package me.drakeet.transformer;

import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;
import java.util.concurrent.Executor;
import java.util.regex.Pattern;
import me.drakeet.timemachine.BaseService;
import me.drakeet.timemachine.CoreContract;
import me.drakeet.timemachine.Message;
import me.drakeet.timemachine.Now;
import me.drakeet.timemachine.TimeKey;

import static com.google.android.agera.RepositoryConfig.SEND_INTERRUPT;
import static com.google.android.agera.net.HttpFunctions.httpFunction;
import static com.google.android.agera.net.HttpRequests.httpGetRequest;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * @author drakeet
 */
public class ServiceImpl extends BaseService implements Updatable {

    private static final Executor networkExecutor = newSingleThreadExecutor();
    private static final Executor calculationExecutor = newSingleThreadExecutor();

    private Repository<Result<String>> repository;
    public static final String SELF = "Service";

    ServiceImpl(CoreContract.View view) {
        super(view);
    }


    @Override public void start() {
    }


    @Override public void onNewOut(Message message) {
        switch (message.content) {
            case "滚":
                addNewIn(new Message.Builder()
                    .setContent("但是...但是...")
                    .setFromUserId(SELF)
                    .setToUserId(TimeKey.userId)
                    .thenCreateAtNow());
                break;
            case "求王垠的最新文章":
                handleYinWang();
                break;
            default:
                // echo
                Message _message = message.clone();
                _message.fromUserId = SELF;
                _message.toUserId = TimeKey.userId;
                _message.createdAt = new Now();
                addNewIn(_message);
                break;
        }
    }


    private void handleYinWang() {
        repository = Repositories.repositoryWithInitialValue(Result.<String>absent())
            .observe()
            .onUpdatesPerLoop()
            .goTo(networkExecutor)
            .getFrom(() -> "http://www.yinwang.org")
            .transform(url -> httpGetRequest(url).compile())
            .attemptTransform(httpFunction())
            .orEnd(Result::failure)
            .goTo(calculationExecutor)
            .transform(input -> new String(input.getBody()))
            .transform(body -> {
                String re = "title\">\\s+.+?href=\"([^\"]*)\">(.+?)</a>.+</li>";
                Pattern pattern = Pattern.compile(re, Pattern.DOTALL);
                return pattern.matcher(body);
            })
            .thenTransform(matcher -> {
                if (matcher.find()) {
                    return Result.success("好的, 为你找到最新的一篇文章是: \n" +
                        matcher.group(2) + matcher.group(1));
                } else {
                    return Result.absent();
                }
            })
            .onDeactivation(SEND_INTERRUPT)
            .compile();
        repository.addUpdatable(this);
    }


    @Override public void destroy() {
        if (repository != null) {
            repository.removeUpdatable(this);
        }
    }


    @Override public void update() {
        repository.get().ifSucceededSendTo(value -> {
            Message message = new Message.Builder()
                .setContent(value)
                .setFromUserId(SELF)
                .setToUserId(TimeKey.userId)
                .thenCreateAtNow();
            addNewIn(message);
        });
    }
}
