package com.constest.ContestAPI.service.impl;

import com.constest.ContestAPI.dto.ContestQuestionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by _archit_agarwal on 27/06/18.
 */
public class FCMService {

    final private String googleCloudApiKey = "key=AAAAZ_dnfLI:APA91bE-nImowIYkuqgL34ByAJxIw_yBOIOooxqemWHK4pZuEwT8Tmoomv6KyNu3SJYso9SQnJ1RfHhLpv_fgTbRc9Vnw-65jYCYPmv2BkbXwiluuoxrDh9TUTo3ZWSnoAzruVEPbAmGE94UJKigt4v5FauH9X4ojw";
    final private String googleCloudApiUrl = "https://fcm.googleapis.com/fcm/send/";
    private String payload = "";
    ObjectMapper objectMapper = new ObjectMapper();


    public String postQuestionToUsers(String contestId, ContestQuestionDTO contestQuestionDTO) {
        String data;
        try {
             data = objectMapper.writeValueAsString(contestQuestionDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        payload = "{ \"data\":" + data + ",\"to\" : \"/topics/" + contestId + "_user\"}";


        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(googleCloudApiUrl).openConnection();
            connection.setRequestProperty("Authorization", googleCloudApiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(Boolean.TRUE);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(payload.getBytes());
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            //System.out.println(response.toString());
            //R responseObject = objectMapper.readValue(response.toString(),rClass.getClass());

            return response.toString();

        } catch (Exception e) {
            return "error sending push notification : " + e.getMessage();
        }
    }

    public String postStatusToAdmin(String contestId, String status) {


        payload = "{ \"data\":" + status + ",\"to\" : \"/topics/QuizMaster\"}";


        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(googleCloudApiUrl).openConnection();
            connection.setRequestProperty("Authorization", googleCloudApiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(Boolean.TRUE);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(payload.getBytes());
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            //System.out.println(response.toString());
            //R responseObject = objectMapper.readValue(response.toString(),rClass.getClass());

            return response.toString();

        } catch (Exception e) {
            return "error sending push notification : " + e.getMessage();
        }
    }

    private void postNotificationToAdminNUsers(String contestId) {


        payload = "{ \"data\": Dynamic Contest is going to start. Make yourself available in 5 mins,\"to\" : \"/topics/QuizMaster\"}";


        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(googleCloudApiUrl).openConnection();
            connection.setRequestProperty("Authorization", googleCloudApiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(Boolean.TRUE);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(payload.getBytes());
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            //System.out.println(response.toString());
            //R responseObject = objectMapper.readValue(response.toString(),rClass.getClass());

            //return response.toString();

        } catch (Exception e) {
            //return "error sending push notification : " + e.getMessage();
        }

        payload = "{ \"data\": Dynamic Contest is going to start. Make yourself available in 5 mins,\"to\" : \"/topics/" + contestId + "_user\"}";


        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(googleCloudApiUrl).openConnection();
            connection.setRequestProperty("Authorization", googleCloudApiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(Boolean.TRUE);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(payload.getBytes());
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            //System.out.println(response.toString());
            //R responseObject = objectMapper.readValue(response.toString(),rClass.getClass());

            //return response.toString();

        } catch (Exception e) {
            //return "error sending push notification : " + e.getMessage();
        }
    }
}
