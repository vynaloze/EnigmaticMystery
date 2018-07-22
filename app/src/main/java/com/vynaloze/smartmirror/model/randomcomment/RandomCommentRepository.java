package com.vynaloze.smartmirror.model.randomcomment;

import com.annimon.stream.Stream;
import com.vynaloze.smartmirror.model.randomcomment.converters.CsvToPojoParser;
import com.vynaloze.smartmirror.model.randomcomment.pojo.PartOfDay;
import com.vynaloze.smartmirror.model.randomcomment.pojo.RandomComment;

import java.util.List;

public class RandomCommentRepository {
    private List<RandomComment> defaultComments;
//    private RandomCommentDao dao;

    public RandomCommentRepository() {
//        this.dao = AppRoomDatabase.getDatabase().randomCommentDao();
        defaultComments = CsvToPojoParser.getFromAsset("comments.csv");
    }

    public List<RandomComment> getAllComments() {
//        List<RandomComment> comments = dao.getAll();
//        comments.addAll(defaultComments);
//        return Stream.of(comments)
        final PartOfDay currentPartOfDay = PartOfDay.getCurrentPartOfDay();
        return Stream.of(defaultComments)
                .filter(comment -> comment.getPartOfDay() == PartOfDay.ALL
                        || comment.getPartOfDay() == currentPartOfDay)
                .toList();
    }

    //todo insert & delete here
}
