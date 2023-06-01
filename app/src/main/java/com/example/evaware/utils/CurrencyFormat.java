package com.example.evaware.utils;

public class CurrencyFormat {
    public static String getFormattedPrice(int price) {
        return "$" + (int)(price / 100.0) + "," + price % 100;
    }
}
