package com.example.public_data_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("api/v1/sample")
@RequiredArgsConstructor

public class RestAPISampleController {

    @Value("${stanRegin.encoding}")
    private String stanReginKey;

    @GetMapping(path = "/StanRegin", produces = "application/json; charset=UTF-8")
    public void getStanRegin(@RequestParam(value = "locatadd_nm", required = false, defaultValue = "") String locatadd_nm) throws IOException {

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("apis.data.go.kr")
                .path("/1741000/StanReginCd/getStanReginCdList")
                .queryParam("serviceKey", stanReginKey)
                .queryParam("pageNo", URLEncoder.encode("1", UTF_8))
                .queryParam("numOfRows", URLEncoder.encode("3", UTF_8))
                .queryParam("type", URLEncoder.encode("json", UTF_8))
                .queryParam("locatadd_nm", URLEncoder.encode(locatadd_nm, UTF_8))
                .build(true);

        System.out.println("uriComponents = " + uriComponents);

        URL url = new URL(uriComponents.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        
        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        System.out.println(sb);
    }
}
