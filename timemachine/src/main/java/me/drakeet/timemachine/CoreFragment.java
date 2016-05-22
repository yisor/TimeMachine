package me.drakeet.timemachine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.List;

/**
 * @author drakeet
 */
public class CoreFragment extends Fragment implements CoreContract.View, View.OnClickListener, CoreHelper.CoreFragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MessageAdapter mAdapter;
    private ImageView mLeftAction;
    private EditText mInput;
    private ImageView mRightAction;

    private List<Message> mMessages;

    CoreContract.Delegate mDelegate;
    OnRecyclerItemClickListener mItemClickListener;
    GestureDetector mGestureDetector;
    CoreHelper mCoreHelper;


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
        mMessages = mDelegate.provideInitialMessages();
        mAdapter = new MessageAdapter(mMessages);
        mCoreHelper = CoreHelper.attach(this);
    }


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_core, container, false);
        setupRecyclerView(rootView);
        mLeftAction = (ImageView) rootView.findViewById(R.id.left_action);
        mInput = (EditText) rootView.findViewById(R.id.input);
        mRightAction = (ImageView) rootView.findViewById(R.id.right_action);
        mLeftAction.setOnClickListener(this);
        mRightAction.setOnClickListener(this);

        return rootView;
    }


    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void setupRecyclerView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mItemClickListener = new OnRecyclerItemClickListener(getContext()) {
            @Override void onItemClick(View view, int position) {
                mDelegate.onMessageClick(mMessages.get(position));
            }


            @Override void onItemLongClick(View view, int position) {
                mDelegate.onMessageLongClick(mMessages.get(position));
            }
        };
        mRecyclerView.addOnItemTouchListener(mItemClickListener);
        mGestureDetector = new GestureDetector(getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override public boolean onSingleTapUp(MotionEvent e) {
                        if (Keyboards.isKeyboardShowed(mInput)) {
                            Keyboards.hideKeyboard(mInput);
                            return true;
                        }
                        return false;
                    }
                });
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }


    @Override public void onNewIn(Message message) {
        addMessage(message);
    }


    @Override public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.left_action) {
            mDelegate.onLeftActionClick();
        } else {
            Message message = new Message(mInput.getText().toString(), null);
            if (!mDelegate.onRightActionClick()) {
                addMessage(message);
                mInput.setText("");
                mDelegate.onNewOut(message);
                offsetIfInBottom();
            }
            mDelegate.onRightActionClick();
        }
    }


    private void offsetIfInBottom() {
        if (mLayoutManager.findLastVisibleItemPosition() == mMessages.size() - 2) {
            mRecyclerView.smoothScrollToPosition(Integer.MAX_VALUE);
        }
    }


    @Override public void onDestroy() {
        super.onDestroy();
        mRecyclerView.removeOnItemTouchListener(mItemClickListener);
    }


    private void addMessage(Message message) {
        int _size = mMessages.size();
        mMessages.add(message);
        mAdapter.notifyItemInserted(_size);
    }
}
