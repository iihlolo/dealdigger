package com.dealdigger.dto;

import lombok.*;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpMethod;

import java.util.Map;
 
@Getter @Builder
public class CrawlRequest {

    private HttpMethod method;
    private String url;

    private Map<String, String> headers;
    private Map<String, String> queryParams;

    private MultiValueMap<String, String> formData;
    private Object jsonBody;
}