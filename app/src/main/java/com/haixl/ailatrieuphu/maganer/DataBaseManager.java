package com.haixl.ailatrieuphu.maganer;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import com.haixl.ailatrieuphu.model.Question;
import com.haixl.ailatrieuphu.model.highScore;

public class DataBaseManager {
    private String DATABASE_NAME="Question";
    private String DATABASE_PATH= Environment.getDataDirectory().getAbsolutePath()+"/data/com.haixl.altptest1/database";
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public DataBaseManager(Context context) {
        this.context = context;
        copyDataBase();
    }

    private void copyDataBase(){
        try {
            File file=new File(DATABASE_PATH+DATABASE_NAME);
            if (file.exists()){
                return;
            }
            AssetManager assetManager=context.getAssets();
            DataInputStream inputStream=new DataInputStream(assetManager.open(DATABASE_NAME));
            DataOutputStream outputStream=new DataOutputStream(new FileOutputStream(file));
            byte[] b = new byte[1024];
            int length;
            while ((length=inputStream.read(b))>0){
                outputStream.write(b,0,length);
            }
            outputStream.close();
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void openDatabase() {
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }
    private void closeDatabase() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    public Question getQuestion(int level){
        openDatabase();
        String sql;
        switch (level){
            case 1:
                sql="select* from Question1 order by random() limit 1";
                break;
            case 2:
                sql="select* from Question2 order by random() limit 1";
                break;
            case 3:
                sql="select* from Question3 order by random() limit 1";
                break;
            case 4:
                sql="select* from Question4 order by random() limit 1";
                break;
            case 5:
                sql="select* from Question5 order by random() limit 1";
                break;
            case 6:
                sql="select* from Question6 order by random() limit 1";
                break;
            case 7:
                sql="select* from Question7 order by random() limit 1";
                break;
            case 8:
                sql="select* from Question8 order by random() limit 1";
                break;
            case 9:
                sql="select* from Question9 order by random() limit 1";
                break;
            case 10:
                sql="select* from Question10 order by random() limit 1";
                break;
            case 11:
                sql="select* from Question11 order by random() limit 1";
                break;
            case 12:
                sql="select* from Question12 order by random() limit 1";
                break;
            case 13:
                sql="select* from Question13 order by random() limit 1";
                break;
            case 14:
                sql="select* from Question14 order by random() limit 1";
                break;
            case 15:
                sql="select* from Question15 order by random() limit 1";
                break;
            default:
                sql="select* from Question order by random() limit 1";
                break;
        }

        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        if (cursor == null
                || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        String q=cursor.getString(1);
        String a=cursor.getString(2);
        String b=cursor.getString(3);
        String c=cursor.getString(4);
        String d=cursor.getString(5);
        int e=cursor.getInt(6);

        Question questions=new Question(q,a,b,c,d,e);
        closeDatabase();
        return questions;
    }
    public ArrayList<highScore> getHighScore() {
        openDatabase();
        String sql = "select * from HighScore ORDER BY Score DESC limit 10";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor == null
                || cursor.getCount() == 0) {
            return null;
        }
        ArrayList<highScore> highScores = new ArrayList<>();
        cursor.moveToFirst();
        String name;
        int score;
        while (cursor.isAfterLast() == false){
            name = cursor.getString(0);
            score = cursor.getInt(1);

            highScores.add(new highScore(name, score));
            cursor.moveToNext();
        }
        closeDatabase();
        return highScores;
    }
    public void insert(String tableName, ContentValues values) {
        openDatabase();
        sqLiteDatabase.insert(tableName,null,values);
        closeDatabase();
    }
}