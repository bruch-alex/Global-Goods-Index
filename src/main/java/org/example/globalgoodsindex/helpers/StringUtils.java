package org.example.globalgoodsindex.helpers;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String generateKey(String descriptiveName) {
        return descriptiveName.toLowerCase().replaceAll("[^a-z0-9 ]", "").replaceAll("\\s+", "_");
    }

    public static List<String> extractQuotedValues(String input) {
        List<String> result = new ArrayList<>();
        Matcher matcher = Pattern.compile("\"([^\"]*)\"").matcher(input);
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        return result;
    }
}

