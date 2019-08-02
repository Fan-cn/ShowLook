package com.hltx.lamic.lamicpay.db;

import android.content.Context;

/**
 * <pre>
 *     author: Fan
 *     time  : 2019-08-01 18:10
 *     desc  : LamicPaySdk
 * </pre>
 */
public class DBManager {
    private static  DBOpenHelper mHelper;

    private DBManager(){

    }

    /**
     * 一般来说这里的initHelper放到application中去初始化
     * 当然也可以在项目运行阶段初始化
     */
    public static void initHelper(Context context){
        if(mHelper == null){
            mHelper = new DBOpenHelper(context);
        }
    }

    public static DBOpenHelper getHelper(){
        if(mHelper == null){
            throw new RuntimeException("DBOpenHelper is null,No init it");
        }
        return mHelper;
    }
}
