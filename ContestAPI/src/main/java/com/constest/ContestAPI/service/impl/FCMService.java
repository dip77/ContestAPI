package  com.constest.ContestAPI.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.constest.ContestAPI.dto.ContestQuestionDTO;
import org.springframework.http.MediaType;

/**
 * Created by _archit_agarwal on 27/06/18.
 */
public class FCMService {

    final private String googleCloudApiKey = "key=AAAAZ_dnfLI:APA91bE-nImowIYkuqgL34ByAJxIw_yBOIOooxqemWHK4pZuEwT8Tmoomv6KyNu3SJYso9SQnJ1RfHhLpv_fgTbRc9Vnw-65jYCYPmv2BkbXwiluuoxrDh9TUTo3ZWSnoAzruVEPbAmGE94UJKigt4v5FauH9X4ojw";
    final private String googleCloudApiUrl = "https://fcm.googleapis.com/fcm/send";
    private String payload = "";
    //ObjectMapper objectMapper = new ObjectMapper();

/*
    public static void main(String[] args) {
        //FCMServices fcmServices = new FCMServices();





        System.out.println("Hello World!!");
    }
    */

    public String postQuestionToUsers(ContestQuestionDTO contestQuestionDTO) {
        String data= "";
        /*
        try {
            data = objectMapper.writeValueAsString(contestQuestionDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        */

        //payload = "{ \"data\":" + data + ",\"to\" : \"/topics/" + contestId + "_user\"}";
        payload = "{\n"
                + "    \"data\" : {\n"
                + "      \"detail\" : \"" + "User msg received" + "\",\n"
                + "      \"questionId\" : \"" + contestQuestionDTO.getQuestionId() + "\",\n"
                + "      \"contestQuestionId\" : \"" + contestQuestionDTO.getContestQuestionId() + "\",\n"
                + "      \"categoryId\" : \"" + contestQuestionDTO.getQuestionDTO().getCategoryId() + "\",\n"
                + "      \"difficulty\" : \"" + contestQuestionDTO.getQuestionDTO().getDifficulty() + "\",\n"
                + "      \"questionType\" : \"" + contestQuestionDTO.getQuestionDTO().getQuestionType() + "\",\n"
                + "      \"questionText\" : \"" + contestQuestionDTO.getQuestionDTO().getQuestionText() + "\",\n"
                + "      \"questionUrl\" : \"" + contestQuestionDTO.getQuestionDTO().getQuestionUrl() + "\",\n"
                + "      \"optionOne\" : \"" + contestQuestionDTO.getQuestionDTO().getOptionOne() + "\",\n"
                + "      \"optionTwo\" : \"" + contestQuestionDTO.getQuestionDTO().getOptionTwo() + "\",\n"
                + "      \"optionThree\" : \"" + contestQuestionDTO.getQuestionDTO().getOptionThree() + "\",\n"
                + "      \"optionFour\" : \"" + contestQuestionDTO.getQuestionDTO().getOptionFour() + "\",\n"
                + "      \"answerType\" : \"" + contestQuestionDTO.getQuestionDTO().getAnswerType() + "\",\n"
                + "      \"status\" : \"" + "next" + "\",\n"
                //+ "      \"points\" : \"" + contestQuestionDTO.getPoints().toString() + "\",\n"
                //+ "      \"\" : \"" +  + "\",\n"
                + "      \"points\" : \"" + contestQuestionDTO.getPoints().toString() + "\"\n"
                + "    },\n"
                + "    \"to\" : \"/topics/user\"\n"
                + "  }\n";

        /*payload = "{\n"
                + "    \"to\" : "
                + "\"c7tzq59OXnk"
                +
                ":APA91bH9iDeWgMQnBnSGPGvrYFnTA6DywYoE4yx_lloorTEBbnWSJ5obbrXyvm2HXp2e63pMrYwlOR0CIdn06J80BN7XwTGw_gC5qEeUyoZ3jO-zWLeqbzzT73pOHvpJWtbVVyOBj4sy\",\n"
                + "    \"notification\" : {\n" + "      \"body\" : \"great match! 3-3\",\n"
                + "      \"title\" : \"Portugal vs. Spain\",\n" + "      \"icon\" : \"myicon\"\n"
                + "    }\n" + "  }\n";
*/

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(googleCloudApiUrl).openConnection();
            connection.setRequestProperty("Authorization", googleCloudApiKey);
            connection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            connection.setRequestMethod("POST");
            connection.setDoOutput(Boolean.TRUE);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(payload.getBytes());
            InputStream inputStream = connection.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
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

    public String postStatusToAdmin(String contestId,String status) {


        payload = "{\n"
                + "    \"data\" : {\n"
                + "      \"detail\" : \"" + "quizMaster msg received" + "\",\n"
                + "      \"contestId\" : \"" + contestId + "\",\n"
                + "      \"status\" : \"" + status + "\"\n"
                //+ "      \"points\" : \"" + contestQuestionDTO.getPoints().toString() + "\",\n"
                //+ "      \"\" : \"" +  + "\",\n"
                + "    },\n"
                + "    \"to\" : \"/topics/quizMaster\"\n"
                + "  }\n";


        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(googleCloudApiUrl).openConnection();
            connection.setRequestProperty("Authorization", googleCloudApiKey);
            connection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            connection.setRequestMethod("POST");
            connection.setDoOutput(Boolean.TRUE);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(payload.getBytes());
            InputStream inputStream = connection.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
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

    public String postStatusToUsers(String contestId, String status) {


        payload = "{\n"
                + "    \"data\" : {\n"
                + "      \"detail\" : \"" + "user msg received" + "\",\n"
                + "      \"contestId\" : \"" + contestId + "\",\n"
                + "      \"status\" : \"" + status + "\"\n"
                //+ "      \"points\" : \"" + contestQuestionDTO.getPoints().toString() + "\",\n"
                //+ "      \"\" : \"" +  + "\",\n"
                + "    },\n"
                + "    \"to\" : \"/topics/user\"\n"
                + "  }\n";


        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(googleCloudApiUrl).openConnection();
            connection.setRequestProperty("Authorization", googleCloudApiKey);
            connection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            connection.setRequestMethod("POST");
            connection.setDoOutput(Boolean.TRUE);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(payload.getBytes());
            InputStream inputStream = connection.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
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
}