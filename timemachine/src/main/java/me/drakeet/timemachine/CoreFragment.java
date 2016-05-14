package me.drakeet.timemachine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * @author drakeet
 */
public class CoreFragment extends Fragment implements CoreContract.View {

    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;
    private List<Message> mList;

    CoreContract.Delegate mDelegate;


    public CoreFragment() {
    }


    public static CoreFragment newInstance() {
        CoreFragment fragment = new CoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    private void parseArguments() {
        Bundle bundle = this.getArguments();
    }


    @Override public void setDelegate(CoreContract.Delegate delegate) {
        mDelegate = delegate;
    }


    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = mDelegate.provideInitialMessages();
        mAdapter = new MessageAdapter(mList);
    }


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_core, container, false);
        setupRecyclerView(rootView);
        return rootView;
    }


    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void setupRecyclerView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override public void onNewIn(Message message) {

    }
}
