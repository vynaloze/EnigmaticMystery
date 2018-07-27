package com.vynaloze.smartmirror.model.randomcomment.converters;

import com.vynaloze.smartmirror.model.randomcomment.pojo.PartOfDay;

public class PartOfDayConverter {
    public static PartOfDay toPartOfDay(String string) {
        for (PartOfDay part : PartOfDay.values()) {
            if (string.equals(part.name())) {
                return part;
            }
        }
        return PartOfDay.ALL;
    }
}
