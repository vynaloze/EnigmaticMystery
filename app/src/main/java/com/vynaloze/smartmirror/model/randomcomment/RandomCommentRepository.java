package com.vynaloze.smartmirror.model.randomcomment;

import com.annimon.stream.Stream;
import com.vynaloze.smartmirror.model.randomcomment.converters.CsvToPojoParser;
import com.vynaloze.smartmirror.model.randomcomment.pojo.PartOfDay;
import com.vynaloze.smartmirror.model.randomcomment.pojo.RandomComment;
import com.vynaloze.smartmirror.model.randomcomment.pojo.WeatherType;
import com.vynaloze.smartmirror.model.weather.pojo.Weather;

import java.util.List;
import java.util.Set;

public class RandomCommentRepository {
    private List<RandomComment> defaultComments;

    public RandomCommentRepository() {
        defaultComments = CsvToPojoParser.getFromAsset("comments.csv");
    }

    public List<RandomComment> getAllComments(Weather weather) {
        final PartOfDay currentPartOfDay = PartOfDay.getCurrentPartOfDay();
        final Set<WeatherType> weatherTypes = WeatherType.getTypesOf(weather);
        return Stream.of(defaultComments)
                .filter(comment -> comment.getPartOfDay() == PartOfDay.ALL
                        || comment.getPartOfDay() == currentPartOfDay)
                .filter(comment -> comment.getWeatherType() == WeatherType.ALL
                        || weatherTypes.contains(comment.getWeatherType()))
                .toList();
    }
}
