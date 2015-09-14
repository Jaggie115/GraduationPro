package com.example.jaggie.graduationproject.entities;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by jaggie on 2015/4/8.
 */
public class Task extends BmobObject implements Serializable {
    private String executor;
    private String type;
    private int completed;
    private int plannum;
    private int total;
    private int todayRemain;
    private boolean finished;

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getPlannum() {
        return plannum;
    }

    public void setPlannum(int plannum) {
        this.plannum = plannum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getTodayRemain() {
        return todayRemain;
    }

    public void setTodayRemain(int todayRemain) {
        this.todayRemain = todayRemain;
    }
}
