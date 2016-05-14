package me.drakeet.timemachine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * @author drakeet
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private final int TYPE_OUT = 1;
    private final int TYPE_IN = 2;
    private List<Message> mList;


    public MessageAdapter(List<Message> list) {
        mList = list;
    }


    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resId = (viewType == TYPE_OUT) ? R.layout.item_message_out : R.layout.item_message_in;
        View v = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return new ViewHolder(v);
    }


    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        Message data = mList.get(position);
        holder.content.setText(data.content);
    }


    @Override public int getItemViewType(int position) {
        Message message = mList.get(position);
        if (TimeKey.isCurrentUser(message.fromUserId)) {
            return TYPE_OUT;
        } else {
            return TYPE_IN;
        }
    }


    @Override public int getItemCount() {
        return mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        Context context;
        TextView content;


        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}

