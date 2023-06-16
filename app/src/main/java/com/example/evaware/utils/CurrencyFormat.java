package com.example.evaware.utils;

import java.text.DecimalFormat;

public class CurrencyFormat {
    public static String getFormattedPrice(Double price) {
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        String formattedPrice = decimalFormat.format(price);
        return "$" + formattedPrice;
    }
}
