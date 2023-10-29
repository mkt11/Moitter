package jp.ac.gifu_u.z3033113.moitter.service;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jp.ac.gifu_u.z3033113.moitter.databinding.ContainerMessagesBinding;
import jp.ac.gifu_u.z3033113.moitter.databinding.ContainerThreadBinding;

public class Message_Adapter extends RecyclerView.Adapter<Message_Adapter.Sent_MessageViewHolder>{

    private final List<Message> messages;
    private final String threadId;

    public Message_Adapter(List<Message> messages,String threadId){
        this.messages = messages;
        this.threadId = threadId;
    }

    @NonNull
    @Override
    public Message_Adapter.Sent_MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContainerMessagesBinding message_item = ContainerMessagesBinding
                .inflate(LayoutInflater.from(parent.getContext()),
                        parent,false);

        return new Message_Adapter.Sent_MessageViewHolder(message_item);
    }

    @Override
    public void onBindViewHolder(@NonNull Sent_MessageViewHolder holder, int position) {

        holder.setMessage_data(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class Sent_MessageViewHolder extends RecyclerView.ViewHolder{
        private final ContainerMessagesBinding message;

        Sent_MessageViewHolder(ContainerMessagesBinding message){
            super(message.getRoot());
            this.message = message;
        }


        public void setMessage_data(Message messaged){
            message.instanceMessage.setText(messaged.message);
            message.messageNamer.setText(messaged.user_name);
        }
    }

}
