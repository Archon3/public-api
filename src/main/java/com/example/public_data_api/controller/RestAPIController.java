package com.example.public_data_api.controller;

import com.example.public_data_api.dto.StanReginRequestDto;
import com.example.public_data_api.service.StanReginService_v1;
import com.example.public_data_api.service.StanReginService_v2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

import static java.nio.charset.StandardCharsets.*;

@RestController
@RequestMapping("api/v1/openapi")
@RequiredArgsConstructor
public class RestAPIController {

    private final StanReginService_v1 stanReginService_v1;
    private final StanReginService_v2 stanReginService_v2;

    @GetMapping(path = "/StanRegin_Blocking", produces = "application/json; charset=UTF-8")
    public void getStanReginBlocking(@RequestParam(value = "locatadd_nm", required = false, defaultValue = "") String locatadd_nm) {
        // UTF-8 인코딩 처리하지 않으면 SERVICE_KEY_IS_NOT_REGISTERED_ERROR 발생
        stanReginService_v1.getStanRegin(
                StanReginRequestDto.builder()
                        .pageNo(URLEncoder.encode("1", UTF_8))
                        .numOfRows(URLEncoder.encode("3", UTF_8))
                        .type(URLEncoder.encode("json", UTF_8))
                        .locatadd_nm(URLEncoder.encode(locatadd_nm, UTF_8))
                        .build()
        );
    }

    @GetMapping(path = "/StanRegin_NonBlocking", produces = "application/json; charset=UTF-8")
    public void getStanReginNonBlocking(@RequestParam(value = "locatadd_nm", required = false, defaultValue = "") String locatadd_nm) {
        // UTF-8 인코딩 처리하지 않으면 SERVICE_KEY_IS_NOT_REGISTERED_ERROR 발생
        stanReginService_v2.getStanRegin(
                StanReginRequestDto.builder()
                        .pageNo(URLEncoder.encode("1", UTF_8))
                        .numOfRows(URLEncoder.encode("3", UTF_8))
                        .type(URLEncoder.encode("json", UTF_8))
                        .locatadd_nm(URLEncoder.encode(locatadd_nm, UTF_8))
                        .build()
        );

    }

}
