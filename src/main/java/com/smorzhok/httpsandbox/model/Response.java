package com.smorzhok.httpsandbox.model;

import java.util.Map;

/**
 * Response object for api
 */
public class Response {

    private final Map<String, String> cookies;

    private final String data;

    private final Map<String, String> headers;

    private final String method;

    private final String origin;

    Response(Map<String, String> cookies, String data, Map<String, String> headers, String method, String origin) {
        this.cookies = cookies;
        this.data = data;
        this.headers = headers;
        this.method = method;
        this.origin = origin;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public String getData() {
        return data;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMethod() {
        return method;
    }

    public String getOrigin() {
        return origin;
    }
}
