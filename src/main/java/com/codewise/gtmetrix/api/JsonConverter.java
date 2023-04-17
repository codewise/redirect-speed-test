package com.codewise.gtmetrix.api;

import com.codewise.gtmetrix.api.response.ApiResponse;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class JsonConverter {

    private static final Logger log = LogManager.getLogger(JsonConverter.class);
    private static final Gson GSON = new Gson();
    private static final MediaType MEDIA_TYPE = MediaType.parse(GtmetrixApi.CONTENT_TYPE);

    public RequestBody toRequestBody(Object object) {
        String jsonString = GSON.toJson(object);
        return RequestBody.create(jsonString, MEDIA_TYPE);
    }

    public ApiResponse toApiResponse(Response response) throws IOException {
        String responseBody = response.body().string();
        log.debug("Raw response body: {}", responseBody.trim());
        return GSON.fromJson(responseBody, ApiResponse.class);
    }
}
