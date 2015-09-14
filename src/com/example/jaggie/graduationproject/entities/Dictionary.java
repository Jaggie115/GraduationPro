package com.example.jaggie.graduationproject.entities;

import cn.bmob.v3.BmobObject;

/**
 * Created by jaggie on 2015/4/8.
 */
public class Dictionary extends BmobObject {
    private String name;
    private int total_words;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal_words() {
        return total_words;
    }

    public void setTotal_words(int total_words) {
        this.total_words = total_words;
    }
}
