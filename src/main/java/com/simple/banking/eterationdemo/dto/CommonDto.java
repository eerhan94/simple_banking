package com.simple.banking.eterationdemo.dto;

import lombok.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonDto {
    @ApiModelProperty(
            value = "Common Dto",
            name = "message",
            dataType = "Map")
    private Map<String, String> message;
}
