package iiitd.nishant.todolist;

/**
 * Created by Nishant on 09-11-2016.
 */
public class Tasks {

    public String job;
    public String desc;

    public Tasks() {

    }

    public Tasks(String job, String desc) {
        this.job = job;
        this.desc = desc;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
