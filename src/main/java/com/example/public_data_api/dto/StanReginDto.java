package com.example.public_data_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StanReginDto {
    @JsonProperty("StanReginCd")
    private List<StanReginCd> stanReginCd;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StanReginCd {
        private List<head> head;

        private List<RowData> row;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class head{
        @JsonProperty("totalCount")
        long totalCount;

        @JsonProperty("numOfRows")
        String numOfRows;

        @JsonProperty("pageNo")
        String pageNo;

        @JsonProperty("type")
        String type;

        @JsonProperty("RESULT")
        APIResult result;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class APIResult {
        String resultCode;
        String resultMsg;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RowData {
        String region_cd;

        String sido_cd;

        String sgg_cd;

        String umd_cd;

        String ri_cd;

        String locatjumin_cd;

        String locatjijuk_cd;

        String locatadd_nm;

        String locat_order;

        String locat_rm;

        String locathigh_cd;

        String locallow_nm;

        String adpt_de;
    }

}

