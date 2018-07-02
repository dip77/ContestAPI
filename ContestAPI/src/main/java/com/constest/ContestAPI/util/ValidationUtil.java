package com.constest.ContestAPI.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationUtil {
    public static boolean compare(String date1) {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "MMM dd, yyyy hh:mm:ss aa");
            Date parsedTimeStamp = dateFormat.parse(date1);
            Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
            return timestamp.after(new Timestamp(System.currentTimeMillis()));

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }


    }
}

