package com.emsp.chargestation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("location")
@Schema(name = "Location", description = "充电站点实体")
public class Location {
    @TableId(type = IdType.AUTO)
    @Schema(description = "站点ID", example = "1")
    private Long id;
    
    @Schema(description = "站点名称", required = true, example = "上海充电站")
    private String name;
    
    @Schema(description = "站点地址", required = true, example = "上海市浦东新区张江高科技园区")
    private String address;
    
    @Schema(description = "纬度", required = true, example = "31.230416")
    private BigDecimal latitude;
    
    @Schema(description = "经度", required = true, example = "121.473701")
    private BigDecimal longitude;
    
    @Schema(description = "营业时间", example = "08:00-22:00")
    private String businessHours;
    
    @Schema(description = "状态", example = "1")
    private Integer status;
    
    @Schema(description = "创建时间", example = "2023-01-01 00:00:00")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间", example = "2023-01-01 00:00:00")
    private LocalDateTime updateTime;
}