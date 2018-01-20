package com.smorzhok.httpsandbox;

import com.smorzhok.httpsandbox.model.Response;
import com.smorzhok.httpsandbox.model.ResponseBuilder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SandboxController {

    private static final String X_FORWARDED_FOR = "X-Forwarded-For";

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public Response hello(HttpServletRequest request) throws IOException {
        return ResponseBuilder.instance()
                .cookies(request.getCookies())
                .data(request.getInputStream())
                .headers(extractHeaders(request))
                .method(request.getMethod())
                .origin(request.getHeader(X_FORWARDED_FOR), request.getRemoteAddr())
                .build();
    }

    private Map<String, String> extractHeaders(HttpServletRequest request) {
        Map<String, String> headers = new TreeMap<>();
        Enumeration<String> headersEnum = request.getHeaderNames();
        while (headersEnum.hasMoreElements()) {
            String header = headersEnum.nextElement();
            headers.put(header, request.getHeader(header));
        }
        return headers;
    }

}
