package com.example.public_data_api.service;

import com.example.public_data_api.dto.StanReginDto;
import com.example.public_data_api.dto.StanReginRequestDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StanReginService_v2 {

    @Value("${stanRegin.encoding}")
    private String stanReginKey;

    @Value("${stanRegin.url}")
    private String stanReginUrl;

    private final ObjectMapper objectMapper;

    public void getStanRegin(StanReginRequestDto request) {
        try {

            /*
              WebClient
             */
            DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(stanReginUrl);
            factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

            WebClient webClient = WebClient.builder()
                    .uriBuilderFactory(factory)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .baseUrl(stanReginUrl)
                    .build();

            String sBody = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("ServiceKey", stanReginKey)
                            .queryParam("pageNo", request.getPageNo())
                            .queryParam("numOfRows", request.getNumOfRows())
                            .queryParam("type", request.getType())
                            .queryParam("locatadd_nm", request.getLocatadd_nm())
                            .build())
                    .retrieve()
                    .onStatus(
                            status -> status.value() < 200 || status.value() > 300,
                            r -> Mono.empty()
                    )
                    .bodyToMono(String.class)
                    .block();

            // sBody = {"RESULT":{"resultCode":"INFO-3","resultMsg":"데이터없음 에러"}}
            System.out.println("sBody = " + sBody);

            /*
             * objectMapper
             */
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            StanReginDto stanReginDto = objectMapper.readValue(sBody, StanReginDto.class);

            if (!ObjectUtils.isEmpty(stanReginDto.getStanReginCd())) {
                System.out.println("body = " + stanReginDto.getStanReginCd().get(1));

                StanReginDto.StanReginCd stanReginCd = stanReginDto.getStanReginCd().get(1);

                for (StanReginDto.RowData row : stanReginCd.getRow()) {
                    System.out.println("row.getRegion_cd() = " + row.getRegion_cd());
                }
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "StanRegin NOT FOUND");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}

