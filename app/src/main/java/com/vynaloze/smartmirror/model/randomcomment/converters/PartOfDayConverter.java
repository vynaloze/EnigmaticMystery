package com.vynaloze.smartmirror.model.randomcomment.converters;

import android.arch.persistence.room.TypeConverter;

import com.vynaloze.smartmirror.model.randomcomment.pojo.PartOfDay;

public class PartOfDayConverter {
    @TypeConverter
    public static PartOfDay toPartOfDay(String string) {
        for (PartOfDay part : PartOfDay.values()) {
            if (string.equals(part.name())) {
                return part;
            }
        }
        return PartOfDay.ALL;
    }

    @TypeConverter
    public static String toString(PartOfDay partOfDay) {
        return partOfDay.name();
    }
}
