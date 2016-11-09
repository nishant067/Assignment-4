package iiitd.nishant.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static LinkedHashMap<String, String> my_jobs = new LinkedHashMap<String, String>();

    private static final String TAG = "MainActivity";
    public static List<Tasks> tasksList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TasksAdapter tasksAdapter;
    DBHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHandler(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        tasksAdapter = new TasksAdapter(tasksList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(tasksAdapter);
        recyclerView.addOnItemTouchListener(new OnTap(getApplicationContext(), recyclerView, new OnTapListener() {
            @Override
            public void onTap(View view, int pos) {
                Tasks tasks = tasksList.get(pos);
                //Toast.makeText(getApplicationContext(), tasks.getDesc(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongTap(View view, int pos) {
                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                startActivity(intent);
            }
        }));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Log.d(TAG, "Add a new task");
                add_job();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void add_job() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText title = (EditText) dialogView.findViewById(R.id.task_title);
        final EditText content = (EditText) dialogView.findViewById(R.id.task_content);

        dialogBuilder.setTitle("Add a Task");

        dialogBuilder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String taskTitle = String.valueOf(title.getText());
                String taskContent = String.valueOf(content.getText());
                Log.d(TAG, "Task to add: " + taskTitle + " " + taskContent);
                Tasks tasks = new Tasks(taskTitle, taskContent);
                tasksList.add(tasks);
                my_jobs.put(taskTitle, taskContent);
                db.addScore(tasks);
                tasksAdapter.notifyDataSetChanged();
            }
        });

        dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //pass
            }
        });

        AlertDialog d = dialogBuilder.create();
        d.show();
    }
}
