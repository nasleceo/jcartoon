package com.anass.jplus.DB.seeepe;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = SeeEpe.class, version = 1)
public abstract class SeeEpeDatabase extends RoomDatabase {

    public abstract SeeEpeDao SeeEpeDao();

    private static SeeEpeDatabase INSTANCE;

    public static SeeEpeDatabase getDbInstance(Context context) {

        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SeeEpeDatabase.class, "SeeEpe")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
