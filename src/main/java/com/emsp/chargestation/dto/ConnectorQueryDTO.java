package com.emsp.chargestation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(name = "ConnectorQueryDTO", description = "充电接口查询参数")
public class ConnectorQueryDTO {
    @Schema(description = "所属充电设备ID", example = "1")
    private Long evseId;

    @Schema(description = "接口标识", example = "1")
    private String connectorId;

    @Schema(description = "充电标准", example = "GB/T")
    private String standard;

    @Schema(description = "最小功率(kW)", example = "30.00")
    private BigDecimal minPower;

    @Schema(description = "最大功率(kW)", example = "100.00")
    private BigDecimal maxPower;

    @Schema(description = "最后更新时间开始", example = "2023-01-01T00:00:00")
    private LocalDateTime updateTimeStart;

    @Schema(description = "最后更新时间结束", example = "2023-12-31T23:59:59")
    private LocalDateTime updateTimeEnd;

    @Schema(description = "页码", example = "1")
    private Integer pageNum = 1;

    @Schema(description = "每页数量", example = "10")
    private Integer pageSize = 10;
}