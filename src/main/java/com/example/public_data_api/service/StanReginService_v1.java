package com.example.public_data_api.service;

import com.example.public_data_api.dto.StanReginDto;
import com.example.public_data_api.dto.StanReginRequestDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StanReginService_v1 {

    @Value("${stanRegin.encoding}")
    private String stanReginKey;

    @Value("${stanRegin.url}")
    private String stanReginUrl;

    public void getStanRegin(StanReginRequestDto request) {

        try {
            //String 사용 시 serviceKey 인식되지 않아 URI Class 사용
            URI uri = new URI(stanReginUrl
                    +"?serviceKey=" + stanReginKey
                    +"&pageNo=" + request.getPageNo()
                    +"&numOfRows=" + request.getNumOfRows()
                    +"&type=" + request.getType()
                    +"&locatadd_nm="+ request.getLocatadd_nm());

            System.out.println("urlStr = " + uri);

            /*
              restTemplate
             */
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            HttpEntity<?> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            // response = {"RESULT":{"resultCode":"INFO-3","resultMsg":"데이터없음 에러"}}
            System.out.println(response);

            /*
              Parser
             */
            JSONParser jsonParser = new JSONParser();

            JSONObject jsonResponse = (JSONObject) jsonParser.parse(response.getBody());

            if (!ObjectUtils.isEmpty(jsonResponse.get("StanReginCd"))) {
                //Body parsing
                JSONArray jsonBody = (JSONArray) jsonResponse.get("StanReginCd");

                JSONObject jsonItems = (JSONObject) jsonBody.get(1);

                System.out.println("jsonItems = " + jsonItems);

                JSONArray jsonItemList = (JSONArray) jsonItems.get("row");

                List<StanReginDto.RowData> result = new ArrayList<>();

                for (Object o : jsonItemList) {
                    JSONObject item = (JSONObject) o;
                    result.add(makeDto(item));
                }

                for (StanReginDto.RowData rowData : result) {
                    System.out.println("rowData.getRegion_cd() = " + rowData.getRegion_cd());
                }
            }
            else {
                System.out.println("getStanRegin Error");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StanReginDto.RowData makeDto(JSONObject item) {
        return StanReginDto.RowData.builder()
                .region_cd(String.valueOf(item.get("region_cd")))
                .sido_cd(String.valueOf(item.get("sido_cd")))
                .sgg_cd(String.valueOf(item.get("sgg_cd")))
                .umd_cd(String.valueOf(item.get("umd_cd")))
                .ri_cd(String.valueOf(item.get("ri_cd")))
                .locatjumin_cd(String.valueOf(item.get("locatjumin_cd")))
                .locatjijuk_cd(String.valueOf(item.get("locatjijuk_cd")))
                .locatadd_nm(String.valueOf(item.get("locatadd_nm")))
                .locat_order(String.valueOf(item.get("locat_order")))
                .locat_rm(String.valueOf(item.get("locat_rm")))
                .locathigh_cd(String.valueOf(item.get("locathigh_cd")))
                .locallow_nm(String.valueOf(item.get("locallow_nm")))
                .adpt_de(String.valueOf( item.get("adpt_de")))
                .build();
    }
}

