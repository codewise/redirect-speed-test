package com.codewise.gtmetrix.api;

import com.codewise.gtmetrix.api.response.ApiResponse;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class JsonConverter {

    private static final Gson GSON = new Gson();
    private static final MediaType MEDIA_TYPE = MediaType.parse(GtmetrixApi.CONTENT_TYPE);

    public RequestBody toRequestBody(Object object) {
        String jsonString = GSON.toJson(object);
        return RequestBody.create(jsonString, MEDIA_TYPE);
    }

    public ApiResponse toApiResponse(Response response) throws IOException {
        String responseBody = response.body().string();
        return GSON.fromJson(responseBody, ApiResponse.class);
    }
}
