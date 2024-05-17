package com.anass.jplus.API.Anime;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.anasskikanime.models.AnimeModel;

public class DramaDB extends SQLiteOpenHelper {
    public static final String DBNAME = "drama.db";

    public static final String TABLENAMECOMICS = "drama_table";
    public static final String TABLENAMECHECKEPE = "drama_check";
    public static final String TABLENAMECOUNTINUWATCH = "drama_countinu";

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String POSTER = "img";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String STORY = "story";
    public static final String PAGEINFOLINK = "pageinfolink";
    public static final String EPISODELINK = "episodelink";
    public static final String FULLEPISODETIME = "fullepisodetime";
    public static final String CURRENTPOSITION = "currentposition";




    public DramaDB(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry = "create table "+TABLENAMECOMICS+" ( id integer primary key autoincrement," +
                TITLE+" text," +  PAGEINFOLINK+" text," + POSTER +" text," +STATUS +" text,"+TYPE +" text,"
                +STORY +" text)";

        db.execSQL(qry);


        String CHECKEPE = "create table "+TABLENAMECHECKEPE+" ( id integer primary key autoincrement," +
                TITLE+" text," +  EPISODELINK+" text,epename text)";

        db.execSQL(CHECKEPE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String qru = "DROP TABLE IF EXISTS "+TABLENAMECOMICS;
        db.execSQL(qru);

        onCreate(db);
    }

    public boolean AddToDramaList(AnimeModel dramaModel){

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues cv = new ContentValues();
        cv.put(TITLE,dramaModel.getTitle());
        cv.put(POSTER,dramaModel.getImg());
        cv.put(STATUS,dramaModel.getStatus());
        cv.put(TYPE,dramaModel.getType());
        cv.put(STORY,dramaModel.getStory());
        cv.put(PAGEINFOLINK,dramaModel.getPageLink());


        float res = db.insert(TABLENAMECOMICS,null,cv);

        if (res == -1){

            return false;

        }else {

            return true;

        }

    }

    public boolean UpdateDrama(DramaModel dramaModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TITLE,dramaModel.getTitle());
        cv.put(POSTER,dramaModel.getImg());
        cv.put(STATUS,dramaModel.getStatus());
        cv.put(TYPE,dramaModel.getType());
        cv.put(STORY,dramaModel.getStory());
        cv.put(PAGEINFOLINK,dramaModel.getPageLink());

        float res = db.update(TABLENAMECOMICS,cv,PAGEINFOLINK+" =?",new String[] {dramaModel.getPageLink()});

        if (res == -1){

            return false;

        }else {

            return true;

        }

    }

    public Cursor GetMylist(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "select * from "+TABLENAMECOMICS+" order by id desc";
        Cursor cursor = db.rawQuery(qry,null);

        return  cursor;
    }

    public boolean DeletDrama(String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        float res = db.delete(TABLENAMECOMICS,PAGEINFOLINK+" = ?",new String[] {ID});

        return res != -1;

    }

    @SuppressLint("Range")
    public boolean checkIfDramaExists(String id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLENAMECOMICS, new String[] {PAGEINFOLINK}, PAGEINFOLINK + " =?",
                new String[] {id}, null, null, null, null);
        boolean exists = (cursor.getCount() > 0);

        return exists;
    }




    // Check Episode
    public boolean AddCheckEpeDB(EpisodeModel comicsModel){

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues cv = new ContentValues();
        cv.put(TITLE,comicsModel.getServerName());
        cv.put(EPISODELINK,comicsModel.getServerLink());

        float res = db.insert(TABLENAMECHECKEPE,null,cv);

        if (res == -1){

            return false;

        }else {

            return true;

        }

    }

    public Cursor GetCheckEPeDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "select * from "+TABLENAMECHECKEPE+" order by id desc";
        Cursor cursor = db.rawQuery(qry,null);

        return  cursor;
    }

    public boolean UpdateCheckEpeDB(EpisodeModel comicsModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TITLE,comicsModel.getServerName());
        cv.put(EPISODELINK,comicsModel.getServerLink());


        float res = db.update(TABLENAMECHECKEPE,cv,"episodelink =?",new String[] {comicsModel.getServerLink()});

        if (res == -1){

            return false;

        }else {

            return true;

        }

    }

    public boolean DeletCheckEpeDB(String myepisodelink){
        SQLiteDatabase db = this.getWritableDatabase();
        float res = db.delete(TABLENAMECHECKEPE,"episodelink = ?",new String[] {myepisodelink});

        return res != -1;

    }

    public boolean checkIfCheckEpeExists(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLENAMECHECKEPE, new String[] {"episodelink"},"episodelink =?",
                new String[] {id}, null, null, null, null);
        boolean exists = (cursor.getCount() > 0);

        return exists;
    }

}
