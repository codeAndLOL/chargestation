package com.emsp.chargestation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(name = "EVSECreateDTO", description = "添加充电设备请求参数")
public class EVSECreateDTO {
    @Schema(description = "所属站点ID", required = true, example = "1")
    @NotNull(message = "站点ID不能为空")
    private Long locationId;
    
    @Schema(description = "设备唯一标识符", required = true, example = "CN*ABC*EVSE123456")
    @NotBlank(message = "设备ID不能为空")
    @Pattern(regexp = "^[A-Z]{2}\\*[A-Z0-9]{3}\\*[A-Z0-9]{1,30}$", message = "设备ID格式不正确")
    private String evseId;
}