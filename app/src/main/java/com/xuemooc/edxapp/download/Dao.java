package com.example.chaos.downloadlibrary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaos on 2015/10/23.
 */
public class Dao {
    private static Dao dao = null;
    private Context context;

    private Dao(Context context) {
        this.context = context;
    }

    public static Dao getInstance(Context context){
        if(dao == null){
            dao = new Dao(context);
        }

        return dao;
    }

    public SQLiteDatabase getConnection(){
        SQLiteDatabase sqLiteDatabase = null;

        try {
            sqLiteDatabase = new DBHelper(context).getReadableDatabase();
        } catch (Exception e){
            e.printStackTrace();
        }

        return sqLiteDatabase;
    }

    public synchronized boolean isHasInfo(String url){
        SQLiteDatabase sqLiteDatabase = getConnection();
        int count = -1;
        Cursor cursor = null;

        try {
            String sql = "select count(*)  from download_info where url=?";
            cursor = sqLiteDatabase.rawQuery(sql, new String[]{url});
            if(cursor.moveToFirst()){
                count = cursor.getInt(0);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(sqLiteDatabase != null){
                sqLiteDatabase.close();
            }

            if(cursor != null){
                cursor.close();
            }
        }

        return count == 0;
    }

    public synchronized void saveInfo(List<DownloadInfo> infos){
        SQLiteDatabase sqLiteDatabase = getConnection();

        try {
            for(DownloadInfo info : infos){
                String sql = "insert into download_info(thread_id,start_pos, end_pos,compelete_size,url) values (?,?,?,?,?)";
                Object[] bindArgs = {
                        info.getThreadId(), info.getStartPos(), info.getEndPos(), info.getCompleteSize(), info.getUrl()
                };
                sqLiteDatabase.execSQL(sql, bindArgs);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(sqLiteDatabase != null){
                sqLiteDatabase.close();
            }
        }
    }

    public synchronized List<DownloadInfo> getInfos(String url){
        List<DownloadInfo> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getConnection();
        Cursor cursor = null;

        try {
            String sql = "select thread_id, start_pos, end_pos,compelete_size,url from download_info where url=?";
            cursor = sqLiteDatabase.rawQuery(sql, new String[]{ url });

            while(cursor.moveToNext()){
                DownloadInfo info = new DownloadInfo(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4));
                list.add(info);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(sqLiteDatabase != null){
                sqLiteDatabase.close();
            }

            if(cursor != null){
                cursor.close();
            }
        }

        return list;
    }

    public synchronized void updataInfos(int threadId, int completeSize, String url){
        SQLiteDatabase sqLiteDatabase = getConnection();

        try {
            String sql = "update download_info set compelete_size=? where thread_id=? and url=?";
            Object[] bindArgs = {completeSize, threadId, url};
            sqLiteDatabase.execSQL(sql, bindArgs);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(sqLiteDatabase != null){
                sqLiteDatabase.close();
            }
        }
    }

    public synchronized void delete(String url){
        SQLiteDatabase sqLiteDatabase = getConnection();

        try {
            sqLiteDatabase.delete("download_info", "url=?", new String[] {url});
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(sqLiteDatabase != null){
                sqLiteDatabase.close();
            }
        }
    }
}
