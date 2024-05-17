package com.anass.jplus.DB.resume_content;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = ResumeContent.class, version = 1)
public abstract class ResumeContentDatabase extends RoomDatabase {

    public abstract ResumeContentDao resumeContentDao();

    private static ResumeContentDatabase INSTANCE;

    public static ResumeContentDatabase getDbInstance(Context context) {

        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ResumeContentDatabase.class, "ResumeContent")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
