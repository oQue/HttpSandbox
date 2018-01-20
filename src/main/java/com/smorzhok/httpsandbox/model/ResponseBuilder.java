package com.smorzhok.httpsandbox.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

/**
 * Builder for {@link Response}
 */
public class ResponseBuilder {

    private Map<String, String> cookies = new TreeMap<>();

    private String data;

    private Map<String, String> headers = new TreeMap<>();

    private String method;

    private String origin;

    private ResponseBuilder() {}

    public static ResponseBuilder instance() {
        return new ResponseBuilder();
    }

    public ResponseBuilder cookies(Cookie[] cookies) {
        if (cookies != null) {
            this.cookies.putAll(
                    Arrays.stream(cookies).collect(Collectors.toMap(Cookie::getName, Cookie::getValue))
            );
        }
        return this;
    }

    public ResponseBuilder method(String method) {
        this.method = method;
        return this;
    }

    public ResponseBuilder headers(Map<String, String> headers) {
        if (headers != null) {
            this.headers.putAll(headers);
        }
        return this;
    }

    public ResponseBuilder data(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        this.data = sb.toString();
        return this;
    }

    public ResponseBuilder origin(String header, String remoteAddr) {
        if (header != null && !header.isEmpty()) {
            this.origin = header;
        } else {
            this.origin = remoteAddr;
        }
        return this;
    }

    public Response build() {
        return new Response(cookies, data, headers, method, origin);
    }
}
