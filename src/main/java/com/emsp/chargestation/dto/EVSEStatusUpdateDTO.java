package com.emsp.chargestation.dto;

import com.emsp.chargestation.enums.EVSEStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(name = "EVSEStatusUpdateDTO", description = "变更充电设备状态请求参数")
public class EVSEStatusUpdateDTO {
    @Schema(description = "设备ID", required = true, example = "1")
    @NotNull(message = "设备ID不能为空")
    private Long id;
    
    @Schema(description = "目标状态", required = true, example = "BLOCKED")
    @NotNull(message = "状态不能为空")
    private EVSEStatus targetStatus;
}