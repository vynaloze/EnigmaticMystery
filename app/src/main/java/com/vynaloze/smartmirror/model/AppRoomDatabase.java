package com.vynaloze.smartmirror.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.vynaloze.smartmirror.model.randomcomment.RandomComment;
import com.vynaloze.smartmirror.model.randomcomment.RandomCommentDao;
import com.vynaloze.smartmirror.util.ApplicationContextProvider;

@Database(entities = {RandomComment.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase {
    public abstract RandomCommentDao randomCommentDao();


    private static AppRoomDatabase INSTANCE;

    public synchronized static AppRoomDatabase getDatabase() {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(ApplicationContextProvider.getContext(),
                    AppRoomDatabase.class, "mirror_database")
                    .build();
        }
        return INSTANCE;
    }
}
