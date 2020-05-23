package com.boards.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.io.IOException;
import java.util.Map;

public class RetroBoardMother {
    private RetroBoardMother() {}

    public static String generateSecureToken() throws FirebaseAuthException, IOException {
        String idToken = null;
        String customToken = FirebaseAuth.getInstance().createCustomToken("foo bar");
        System.out.println("CUSTOM TOKEN: \n" + customToken);
        final String ID_TOOLKIT_URL =
                "https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyCustomToken";
        final JsonFactory jsonFactory = Utils.getDefaultJsonFactory();
        final HttpTransport transport = Utils.getDefaultTransport();
        final String FIREBASE_API_KEY = "AIzaSyAERXDD08sF5pEmX1j3nlxjiermP5mvVrM";

        GenericUrl url = new GenericUrl(ID_TOOLKIT_URL + "?key="
                + FIREBASE_API_KEY);
        Map<String, Object> content = ImmutableMap.of(
                "token", customToken, "returnSecureToken", true);
        com.google.api.client.http.HttpRequest request = transport.createRequestFactory().buildPostRequest(url,
                new JsonHttpContent(jsonFactory, content));
        ((com.google.api.client.http.HttpRequest) request).setParser(new JsonObjectParser(jsonFactory));
        com.google.api.client.http.HttpResponse response = request.execute();
        try {
            GenericJson json = response.parseAs(GenericJson.class);
            idToken = json.get("idToken").toString();
        } finally {
            response.disconnect();
        }



        return idToken;
    }

    public static byte[] asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
