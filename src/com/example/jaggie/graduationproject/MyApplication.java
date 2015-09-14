/*
 * COPYRIGHT. HSBC HOLDINGS PLC 2015. ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of HSBC Holdings plc.
 */
package com.example.jaggie.graduationproject;

import android.app.Application;
import cn.bmob.v3.Bmob;


/**
 * <p>
 * <b> TODO : Insert description of the class's responsibility/role. </b>
 * </p>
 */
public class MyApplication extends Application {

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Application#onCreate()
     */
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Bmob.initialize(this, Config.APPLICATION_KEY);
    }

}
