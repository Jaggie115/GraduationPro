package com.example.jaggie.graduationproject.entities;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;


/**
 * 储存授权用户的信息类
 *
 * @author Administrator
 */

public class User extends BmobUser {

    private BmobFile avatar;
    public BmobFile getAvatar() {
        return avatar;
    }

}
