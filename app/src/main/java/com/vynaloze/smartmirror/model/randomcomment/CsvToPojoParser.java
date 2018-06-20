package com.vynaloze.smartmirror.model.randomcomment;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.vynaloze.smartmirror.util.ApplicationContextProvider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CsvToPojoParser {
    public static List<RandomComment> getFromAsset(String asset) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.getFormat().setLineSeparator("\r\n");
        CsvParser parser = new CsvParser(settings);
        try {
            List<String[]> allRows = parser.parseAll(new BufferedReader(new InputStreamReader(ApplicationContextProvider.getContext().getAssets().open(asset))));
            return parseStringsToPojos(allRows);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static List<RandomComment> parseStringsToPojos(List<String[]> rows) {
        List<RandomComment> list = new ArrayList<>();
        for (String[] row : rows) {
            list.add(new RandomComment(row[0], Double.valueOf(row[1])));
        }
        return list;
    }
}
