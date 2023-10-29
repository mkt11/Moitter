package jp.ac.gifu_u.z3033113.moitter.service;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jp.ac.gifu_u.z3033113.moitter.databinding.ContainerThreadBinding;
public class Thread_Adapter extends RecyclerView.Adapter<Thread_Adapter.ThreadViewHolder> {
    private final Thread_Listener thread_listener;
    private final List<Thread> threadList;

    public Thread_Adapter(Thread_Listener thread_listener,List<Thread> threadList) {
        this.threadList = threadList;
        this.thread_listener = thread_listener;
    }

    @NonNull
    @Override
    public ThreadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContainerThreadBinding thread_item = ContainerThreadBinding
                .inflate(LayoutInflater.from(parent.getContext()),
                parent,false);

        return new ThreadViewHolder(thread_item);
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadViewHolder holder, int position) {
        holder.itemView.setOnClickListener(view ->
        thread_listener.Thread_click(holder.setThreadData(threadList.get(position))));
        holder.setThreadData(threadList.get(position));
    }

    public interface Thread_Listener{
        void Thread_click(Thread thread);
    }

    @Override
    public int getItemCount() {
        return threadList.size();
    }

    class ThreadViewHolder extends RecyclerView.ViewHolder{
        ContainerThreadBinding thread_item;

        public ThreadViewHolder(ContainerThreadBinding thread_item) {
            super(thread_item.getRoot());
            this.thread_item = thread_item;
        }

        Thread setThreadData(Thread thread){

            thread_item.threadTitle.setText(thread.thread_name);
            thread_item.countter.setText(thread.counter);
            return thread;
        }

    }
}

