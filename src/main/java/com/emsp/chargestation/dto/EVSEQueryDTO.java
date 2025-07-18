package com.emsp.chargestation.dto;

import com.emsp.chargestation.enums.EVSEStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "EVSEQueryDTO", description = "充电设备查询参数")
public class EVSEQueryDTO {
    @Schema(description = "所属站点ID", example = "1")
    private Long locationId;

    @Schema(description = "设备唯一标识符", example = "CN*ABC*EVSE123456")
    private String evseId;

    @Schema(description = "设备状态", example = "AVAILABLE")
    private EVSEStatus status;

    @Schema(description = "最后更新时间开始", example = "2023-01-01T00:00:00")
    private LocalDateTime updateTimeStart;

    @Schema(description = "最后更新时间结束", example = "2023-12-31T23:59:59")
    private LocalDateTime updateTimeEnd;

    @Schema(description = "页码", example = "1")
    private Integer pageNum = 1;

    @Schema(description = "每页数量", example = "10")
    private Integer pageSize = 10;
}