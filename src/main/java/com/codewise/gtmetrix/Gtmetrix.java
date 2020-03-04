package com.codewise.gtmetrix;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.codewise.gtmetrix.entities.GtmetrixHar;
import okhttp3.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class Gtmetrix {

    private OkHttpClient httpClient;
    private String auth;

    public Gtmetrix(String email, String apiKey) {
        this.httpClient = new OkHttpClient().newBuilder()
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .build();

        this.auth = "Basic " +
                new String(Base64.getEncoder().encode((email + ":" + apiKey).getBytes(StandardCharsets.ISO_8859_1)));
    }

    public String scheduleTest(int location, int browser, String url) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String requestProperties = String
                .format("location=%s&x-metrix-video=0&x-metrix-adblock=0&browser=%s&url=%s", location, browser, url);
        RequestBody body = RequestBody.create(mediaType, requestProperties);
        Request request = new Request.Builder()
                .url("https://gtmetrix.com/api/0.1/test")
                .method("POST", body)
                .addHeader("authorization", this.auth)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        Response response = this.httpClient.newCall(request).execute();
        JsonParser parser = new JsonParser();
        String rbs = response.body().string();
        System.out.println(rbs);
        JsonObject element = (JsonObject) parser.parse(rbs);
        return element.get("test_id").toString().replaceAll("\"", "");
    }

    public boolean isTestCompleted(String testId) throws IOException {
        Request request = new Request.Builder()
                .url(String.format("https://gtmetrix.com/api/0.1/test/%s", testId))
                .method("GET", null)
                .addHeader("authorization", this.auth)
                .build();
        Response response = httpClient.newCall(request).execute();
        JsonParser parser = new JsonParser();
        JsonObject element = (JsonObject) parser.parse(response.body().string());
        if (element.get("state").toString().contains("error")) {
            throw new IllegalStateException("redirect threw an error");
        };
        return element.get("state").toString().contains("completed");
    }

    public long getTimeUntilLanderFromHar(String testId, String landerDomain) throws IOException {
        Request request = new Request.Builder()
                .url(String.format("https://gtmetrix.com/api/0.1/test/%s/har?=", testId))
                .method("GET", null)
                .addHeader("Authorization", this.auth)
                .build();
        Response response = this.httpClient.newCall(request).execute();
        Gson gson = new Gson();
        GtmetrixHar har = gson.fromJson(response.body().string(), GtmetrixHar.class);

        LocalDateTime localDateTimeStart = LocalDateTime.parse(har
                .getLog()
                .getEntries()
                .get(0)
                .getStartedDateTime()
                .replaceAll("Z", ""));

        LocalDateTime localDateTimeStop = LocalDateTime.parse(har
                .getLog()
                .getEntries()
                .stream()
                .filter(entry -> entry.getRequest().getUrl().toLowerCase().contains(landerDomain.toLowerCase()))
                .findFirst()
                .get()
                .getStartedDateTime()
                .replaceAll("Z", ""));

        long millisecondsDiff = ChronoUnit.MILLIS.between(localDateTimeStop, localDateTimeStart);

        return Math.abs(millisecondsDiff);
    }
}
