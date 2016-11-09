package iiitd.nishant.todolist;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nishant on 09-11-2016.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.MyViewHolder> {

    private List<Tasks> tasksList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc;

        public MyViewHolder(final View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), CardActivity.class);
                    i.putExtra("Pos", getPosition());
//                    Log.v("Position", getPosition() +);
                    view.getContext().startActivity(i);
                }
            });
            title = (TextView) view.findViewById(R.id.title);
            desc = (TextView) view.findViewById(R.id.desc);
        }
    }

    public TasksAdapter(List<Tasks> tasksList) {
        this.tasksList = tasksList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Tasks tasks = tasksList.get(position);
        holder.title.setText(tasks.getJob());
        holder.desc.setText(tasks.getDesc());
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

}
