package me.drakeet.timemachine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * @author drakeet
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    List<Message> mList;


    public MessageAdapter(List<Message> list) {
        mList = list;
    }


    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        Message data = mList.get(position);
    }


    @Override public int getItemCount() {
        return mList.size();
    }


    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.item_message_out, parent, false);
        return new ViewHolder(v);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        Context context;


        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
        }
    }
}

