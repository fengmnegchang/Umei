/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-11下午2:55:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.db;

import android.content.Context;

import com.open.umei.R;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-11下午2:55:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class UmeiDBHelper extends OpenDatabaseHelper {

    private static UmeiDBHelper mUmeiDBHelper;

    private UmeiDBHelper(Context context){
        super(context);
    }

    public static UmeiDBHelper getInstance(Context context){
        if (mUmeiDBHelper==null){
            synchronized (OpenDatabaseHelper.class){
                if (mUmeiDBHelper==null){
                	mUmeiDBHelper = new UmeiDBHelper(context);
                    if (mUmeiDBHelper.getDB()==null||!mUmeiDBHelper.getDB().isOpen()){
                    	mUmeiDBHelper.open();
                    }
                }
            }
        }
        return mUmeiDBHelper;
    }

//    @Override
//    protected int getMDbVersion(Context context) {
//        return 1;
//    }
//
//    @Override
//    protected String getDbName(Context context) {
//        return "test.db";
//    }
//
//    @Override
//    protected String[] getDbCreateSql(Context context) {
//        String[] a = new String[1];
//        a[0] = "CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,gender TEXT,age INTEGER)";
//        return a;
//    }
//
//    @Override
//    protected String[] getDbUpdateSql(Context context) {
//        return new String[0];
//    }
    
    @Override
    protected int getMDbVersion(Context context) {
    	int version = 1;
    	try {
    		version = Integer.valueOf(context.getResources().getStringArray(R.array.DATABASE_INFO)[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return version;
    }

    @Override
    protected String getDbName(Context context) {
        return context.getResources().getStringArray(R.array.DATABASE_INFO)[0];
    }

    @Override
    protected String[] getDbCreateSql(Context context) {
        return context.getResources().getStringArray(R.array.CREATE_TABLE_SQL);
    }

    @Override
    protected String[] getDbUpdateSql(Context context) {
        return context.getResources().getStringArray(R.array.UPDATE_TABLE_SQL);
    }
}
