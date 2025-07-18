package com.emsp.chargestation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "LocationQueryDTO", description = "站点查询参数")
public class LocationQueryDTO {
    @Schema(description = "站点名称", example = "上海")
    private String name;
    
    @Schema(description = "地址关键字", example = "浦东")
    private String address;
    
    @Schema(description = "最后更新时间开始", example = "2023-01-01T00:00:00")
    private LocalDateTime updateTimeStart;
    
    @Schema(description = "最后更新时间结束", example = "2023-12-31T23:59:59")
    private LocalDateTime updateTimeEnd;
    
    @Schema(description = "页码", example = "1")
    private Integer pageNum = 1;
    
    @Schema(description = "每页数量", example = "10")
    private Integer pageSize = 10;
}