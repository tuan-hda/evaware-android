package com.example.evaware.utils;

import android.text.format.DateUtils;

import com.google.firebase.Timestamp;

import java.util.Date;

public class ConvertTimestamp {
    public static String convertToRelativeTimeSpanString(Timestamp timestamp) {
        long seconds = timestamp.getSeconds();
        long nanoseconds = timestamp.getNanoseconds();

        Date date = new Date(seconds * 1000L + nanoseconds / 1000000L);
        long timeInMillis = date.getTime();

        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(timeInMillis, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);

        return timeAgo.toString();
    }
}
