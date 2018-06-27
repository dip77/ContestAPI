package com.constest.ContestAPI.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class demo {
    public static void main(String[] args) throws Exception{

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss:SSS");
        Date parsedTimeStamp = dateFormat.parse("2014-08-22 15:02:51:580");
        Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());


    }
}
