package com.vynaloze.smartmirror.model.randomcomment;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RandomCommentDao {
    @Query("select * from randomcomment")
    List<RandomComment> getAll();

    @Insert
    void insert(RandomComment... randomComments);

    @Delete
    void delete(RandomComment randomComment);
}
