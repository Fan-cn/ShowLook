package com.hltx.lamic.lamicpay.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.google.gson.internal.UnsafeAllocator;
import com.hltx.lamic.lamicpay.utils.Debug;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 *     author: Fan
 *     time  : 2019-08-01 17:05
 *     desc  : DBHelper
 * </pre>
 */
public class DBHelper extends SQLiteOpenHelper {

    /**
     * 离线订单表
     */
    private final String CT_OLORDER =
            "create table if not exists tOLOrder ("
            + "id integer primary key autoincrement, "
            + "out_trade_no          char(30), "
            + "goods_detail          text,"
            + "total_amount          char(20),"
            + "auth_code             text,"
            + "pay_type              char(10),"
            + "discountable_amount   char(20),"
            + "vip_card_no           char(20),"
            + "terminalName          char(30),"
            + "terminalNo            char(30),"
            + "outcashier            char(20),"
            + "make_invoice          char(10))";



    /**
      * 注释内容
      */
    public static final String TAG = "DBHelper";


    /**
      * 数据库名称
      */
     private static final String DATABASE_NAME_STR = "lamic";


     /**
      * 数据库后缀名
      */
     private static final String DATABASE_NAME_SUFFIX = ".db";


    /**
      * 数据库版本号
      */
    private static final int DB_VERSION = 1;


    public SQLiteDatabase db;


    private static DBHelper mInstance = null;


    public synchronized static DBHelper getInstance(Context context) {
        if (mInstance == null) {
             mInstance = new DBHelper(context);
        }
        return mInstance;
    }


     /**
      * 建普通库
      *
      * @param context
      */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME_STR + DATABASE_NAME_SUFFIX, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CT_OLORDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {

        }
    }



    /**
      * 插入数据
      *
      * @param tableName 要插入的表格的名称
      * @param values    要插入的数据由数据名称和数据值组成的键值对
      */
    public long insert(String tableName, ContentValues values) {
        long result = -1;


        if (TextUtils.isEmpty(tableName) || null == values || values.size() == 0) {
            return -1;
        }
        if (db == null || !db.isOpen()) {
            db = this.getWritableDatabase();
        }


         while (db.isDbLockedByCurrentThread() || db.isDbLockedByOtherThreads()) {
            try {
            Thread.sleep(100);
            } catch (InterruptedException e) {
                Debug.e("[" + TAG + "]" + e.toString());
            }
        }


        db.beginTransaction();
        try {
        result = db.insert(tableName, null, values);
        db.setTransactionSuccessful();
        } catch (Exception e) {
            Debug.e("[" + TAG + "]" + "insert fail,please try again " + e.toString());
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
        }

        return result;
    }


    /**
      * 以集合形式插入数据
      *
      * @param tableName 要插入的表格的名称
      * @param values    要插入的数据由数据名称和数据值组成的键值对集合
      */
    public boolean insert(String tableName, List<ContentValues> values) {


        boolean result = false;


        if (TextUtils.isEmpty(tableName) || null == values || values.size() == 0) {
            return result;
        }


        if (db == null || !db.isOpen()) {
            db = this.getWritableDatabase();
        }


        while (db.isDbLockedByCurrentThread() || db.isDbLockedByOtherThreads()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Debug.e("[" + TAG + "]" + e.toString());
            }
        }
        Debug.i("[" + TAG + "]" + "start excute");


        try {
            db.beginTransaction();
            long ret = -1;
            for (int i = 0; i < values.size(); i++) {
                ret = db.insert(tableName, null, values.get(i));
                if (ret == -1) {
                    Debug.e("[" + TAG + "]" + "insert error! content = [" + values.get(i).toString() + "]");
                }
            }
            result = true;
            Debug.e("[" + TAG + "]" + "insert list ret = " + ret);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Debug.e("[" + TAG + "]" + "insert fail,please try again " + e.toString());
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
        }

        return result;
    }


    /**
      * 以集合形式替换数据
      *
      * @param tableName 要插入的表格的名称
      * @param values    要插入的数据由数据名称和数据值组成的键值对集合
      */
    public boolean replace(String tableName, List<ContentValues> values) {
        boolean result = false;

        if (TextUtils.isEmpty(tableName) || null == values || values.size() == 0) {
            return result;
        }
        if (db == null || !db.isOpen()) {
            db = this.getWritableDatabase();
        }


        while (db.isDbLockedByCurrentThread() || db.isDbLockedByOtherThreads()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Debug.e("[" + TAG + "]" + "replace " + e.toString());
            }
        }
        Debug.i("[" + TAG + "]" + "start excute");


        try {
            db.beginTransaction();
            long ret = -1;
            for (int i = 0; i < values.size(); i++) {
                ret = db.replace(tableName, null, values.get(i));
            }


            result = true;
            Debug.e("[" + TAG + "]" + "replace ret = " + ret);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Debug.e("[" + TAG + "]" + "replace fail,please try again " + e.toString());
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
        }

        return result;
    }


    /**
      * 查询
      *
      * @param sql
      * @param args
      * @return
      */
    public Cursor query(String sql, String[] args) {
        if (TextUtils.isEmpty(sql)) {
            return null;
        }


        if (db == null || !db.isOpen()) {
            db = this.getWritableDatabase();
        }


        while (db.isDbLockedByCurrentThread() || db.isDbLockedByOtherThreads()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Debug.e("[" + TAG + "]" + e.toString());
            }
        }


        Cursor cursor = null;
        try {
            db.beginTransaction();
            cursor = db.rawQuery(sql, args);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Debug.e("[" + TAG + "]" + "query fail,please try again " + e.toString());
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
        }


        return cursor;
    }

    /**
     * 这个方法的主要功能是将数据中查询到的数据放到集合中。
     * 类似于我们查询到对应的数据重新封装到一个对象中，然后把这个对象
     * 放入集合中。这样就能拿到我们的数据集了
     * @param table
     * @param cursor
     * @param <T>
     * @return
     */
    private <T> List<T> convertToList(Class<T> table, Cursor cursor) {
        List<T> result = new ArrayList<>();
        //这里可能大家不了解，这是Gson为我们提供的一个通过JDK内部API 来创建对象实例，这里不做过多讲解
        UnsafeAllocator allocator = UnsafeAllocator.create();
        while (cursor.moveToNext()) {
            try {
                //创建具体的实例
                T t = allocator.newInstance(table);
                boolean flag = true;
                //遍历所有的游标数据
                for (int i = 0; i < cursor.getColumnCount(); i++) {

                    //每次都去查找该类中有没有自带的id，如果没有，就不应该执行下面的语句
                    //因为下面获取属性名时，有一个异常抛出，要是找不到属性就会结束这个for循环
                    //后面的所有数据就拿不到了,只要检测到没有id，就不需要再检测了。
                    if(flag){
                        Field fieldId = getFieldId(table);
                        if(fieldId == null){
                            flag = !flag;
                            continue;
                        }
                    }
                    //通过列名获取对象中对应的属性名
                    Field field = table.getDeclaredField(cursor.getColumnName(i));
                    //获取属性的类型
                    Class<?> type = field.getType();
                    //设置属性的访问权限为最高权限，因为要设置对应的数据
                    field.setAccessible(true);
                    //获取到数据库中的值，由于sqlite是采用若语法，都可以使用getString来获取
                    String value = cursor.getString(i);
                    //通过判断类型，保存到指定类型的属性中，这里判断了我们常用的数据类型。
                    if (type.equals(Byte.class) || type.equals(Byte.TYPE)) {
                        field.set(t, Byte.parseByte(value));
                    } else if (type.equals(Short.class) || type.equals(Short.TYPE)) {
                        field.set(t, Short.parseShort(value));
                    } else if (type.equals(Integer.class) || type.equals(Integer.TYPE)) {
                        field.set(t, Integer.parseInt(value));
                    } else if (type.equals(Long.class) || type.equals(Long.TYPE)) {
                        field.set(t, Long.parseLong(value));
                    } else if (type.equals(Float.class) || type.equals(Float.TYPE)) {
                        field.set(t, Float.parseFloat(value));
                    } else if (type.equals(Double.class) || type.equals(Double.TYPE)) {
                        field.set(t, Double.parseDouble(value));
                    } else if (type.equals(Character.class) || type.equals(Character.TYPE)) {
                        field.set(t, value.charAt(0));
                    } else if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
                        field.set(t, Boolean.parseBoolean(value));
                    } else if (type.equals(String.class)) {
                        field.set(t, value);
                    }
                }
                result.add(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取对象属性中的id字段，如果有就获取，没有就不获取
     * @param table
     * @return
     */
    private Field getFieldId(Class table) {
        Field fieldId = null;
        try {
            fieldId = table.getDeclaredField("id");
            if (fieldId == null) {
                table.getDeclaredField("_id");
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return fieldId;
    }


    /**
      * 对数据库中数据的删除操作
      *
      * @param tableName   删除操作中要操作的数据表的名称
      * @param where       要删除的数据中提供的条件参数的名称
      * @param whereValues 要删除的数据中提供的条件参数的值
      */
    public boolean delete(String tableName, String where, String[] whereValues) {
        if (TextUtils.isEmpty(tableName)) {
            return false;
        }
        if (db == null || !db.isOpen()) {
            db = this.getWritableDatabase();
        }
        boolean result = false;
        try {
            db.beginTransaction();
            int index = db.delete(tableName, where, whereValues);
            if (index > 0) {
                result = true;
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Debug.e("[" + TAG + "]" + "delete fail,please try again");
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
        }
        return result;
    }


    /**
      * 对数据的更新操作
      *
      * @param tableName   是所对应的操作表
      * @param values      需要更新的数据名称和值组成的键值对
      * @param where       要更新的数据集的条件参数
      * @param whereValuse 要更新的数据集的条件参数的值
      */
    public boolean update(String tableName, ContentValues values, String where, String[] whereValuse) {


        if (TextUtils.isEmpty(tableName) || null == values || values.size() == 0) {
            return false;
        }
        if (db == null || !db.isOpen()) {
            db = this.getWritableDatabase();
        }
        boolean result = false;
        try {
            db.beginTransaction();
            int index = db.update(tableName, values, where, whereValuse);
            if (index > 0) {
                result = true;
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Debug.e("[" + TAG + "]" + "update fail,please try again");
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
        }
        return result;
    }


    /**
      * 关闭所有链接中的数据库
      */
    public void closeDatabase() {
        try {
            close();
        } catch (Exception e) {
            Debug.i( "[" + TAG + "]" + "closeDatabase | closeDatabase fail");
        }
    }


    /**
      * 以sql语句形式插入数据
      *
      * @param sql sql语句
      */
    public boolean insertSql(String sql) {


        if (db == null || !db.isOpen()) {
            db = this.getWritableDatabase();
        }


        while (db.isDbLockedByCurrentThread() || db.isDbLockedByOtherThreads()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Debug.e( "[" + TAG + "]" + e.toString());
            }
        }
        Debug.i( "[" + TAG + "]" + "start excute");
        boolean result = false;


        try {
            db.beginTransaction();


            db.execSQL(sql);
            result = true;
            Debug.e( "[" + TAG + "]" + "insertSql result = " + result);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Debug.e("[" + TAG + "]" + "insertSql fail,please try again " + e.toString());
        } finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
        }


        return result;
    }


    /**
      * 判断数据库中指定表的指定字段是否存在
      *
      * @param db
      * @param strTableName 指定表名称
      * @param strFieldName 执行字段名称
      * @return
      */
    private boolean isExistField(SQLiteDatabase db, String strTableName, String strFieldName) {
        StringBuilder builder = new StringBuilder();
        builder.append("name = '").append(strTableName).append("' AND sql LIKE '%").append(strFieldName).append("%'");
        Cursor cursor = null;
        try {
            cursor = db.query("sqlite_master", null, builder.toString(), null, null, null, null);
            return cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();

            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

}
