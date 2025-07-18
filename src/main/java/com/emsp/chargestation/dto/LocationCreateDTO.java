package com.emsp.chargestation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Schema(name = "LocationCreateDTO", description = "创建站点请求参数")
public class LocationCreateDTO {
    @Schema(description = "站点名称", required = true, example = "上海充电站")
    @NotBlank(message = "站点名称不能为空")
    private String name;
    
    @Schema(description = "站点地址", required = true, example = "上海市浦东新区张江高科技园区")
    @NotBlank(message = "站点地址不能为空")
    private String address;
    
    @Schema(description = "纬度", required = true, example = "31.230416")
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;
    
    @Schema(description = "经度", required = true, example = "121.473701")
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;
    
    @Schema(description = "营业时间", example = "08:00-22:00")
    private String businessHours;
}