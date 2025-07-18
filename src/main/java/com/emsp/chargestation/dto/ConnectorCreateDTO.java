package com.emsp.chargestation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Schema(name = "ConnectorCreateDTO", description = "添加充电接口请求参数")
public class ConnectorCreateDTO {
    @Schema(description = "所属设备ID", required = true, example = "1")
    @NotNull(message = "设备ID不能为空")
    private Long evseId;
    
    @Schema(description = "接口标识", required = true, example = "1")
    @NotBlank(message = "接口标识不能为空")
    private String connectorId;
    
    @Schema(description = "充电标准", required = true, example = "GB/T")
    @NotBlank(message = "充电标准不能为空")
    private String standard;
    
    @Schema(description = "功率(kW)", required = true, example = "60.00")
    @NotNull(message = "功率不能为空")
    private BigDecimal power;
    
    @Schema(description = "电压(V)", required = true, example = "220.00")
    @NotNull(message = "电压不能为空")
    private BigDecimal voltage;
    
    @Schema(description = "电流类型", required = true, example = "AC")
    @NotBlank(message = "电流类型不能为空")
    private String current;
}