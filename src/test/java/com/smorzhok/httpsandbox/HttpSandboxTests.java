package com.smorzhok.httpsandbox;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import javax.servlet.http.Cookie;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class HttpSandboxTests {

    private static final MediaType JSON_CONTENT = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private static final String CUSTOM_JSON = "{\"custom\":\"value\"}";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetMethod() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.method", is(HttpMethod.GET.name())));
    }

    @Test
    public void testPostMethod() throws Exception {
        mockMvc.perform(post("/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.method", is(HttpMethod.POST.name())));
    }

    @Test
    public void testPutMethod() throws Exception {
        mockMvc.perform(put("/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.method", is(HttpMethod.PUT.name())));
    }

    @Test
    public void testDeleteMethod() throws Exception {
        mockMvc.perform(delete("/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.method", is(HttpMethod.DELETE.name())));
    }

    @Test
    public void testHeaders() throws Exception {
        mockMvc.perform(get("/").header("custom", "value"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.headers.custom", is("value")));
    }

    @Test
    public void testCookies() throws Exception {
        mockMvc.perform(get("/").cookie(new Cookie("custom", "value")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.cookies.custom", is("value")));
    }

    @Test
    public void testData() throws Exception {
        mockMvc.perform(post("/").contentType(JSON_CONTENT).content(CUSTOM_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data", containsString(CUSTOM_JSON)));
    }

}
