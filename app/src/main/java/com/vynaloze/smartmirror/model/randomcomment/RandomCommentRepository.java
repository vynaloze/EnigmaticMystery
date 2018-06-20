package com.vynaloze.smartmirror.model.randomcomment;

import com.vynaloze.smartmirror.model.AppRoomDatabase;

import java.util.List;

public class RandomCommentRepository {
    private List<RandomComment> defaultComments;
    private RandomCommentDao dao;

    public RandomCommentRepository() {
        this.dao = AppRoomDatabase.getDatabase().randomCommentDao();
        defaultComments = CsvToPojoParser.getFromAsset("comments.csv");
    }

    public List<RandomComment> getAllComments() {
        List<RandomComment> comments = dao.getAll();
        comments.addAll(defaultComments);
        return comments;
    }

    //todo insert & delete here
}
