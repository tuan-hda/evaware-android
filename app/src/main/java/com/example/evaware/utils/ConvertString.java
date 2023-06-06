package com.example.evaware.utils;

public class ConvertString {
    public static String convertToTitleCase(String input) {
        StringBuilder converted = new StringBuilder();

        String[] words = input.split("_");

        for (String word : words) {

            String firstChar = word.substring(0, 1).toUpperCase();
            String restOfWord = word.substring(1);

            converted.append(firstChar).append(restOfWord).append(" ");
        }

        return converted.toString().trim();
    }
}
