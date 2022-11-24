package com.example.public_data_api.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StanReginRequestDto {
    private String pageNo;

    private String numOfRows;

    private String type;

    private String locatadd_nm;

}

