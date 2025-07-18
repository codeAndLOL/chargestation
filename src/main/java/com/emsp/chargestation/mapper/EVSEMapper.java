package com.emsp.chargestation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.emsp.chargestation.entity.EVSE;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EVSEMapper extends BaseMapper<EVSE> {
    @Select("<script>" +
            "SELECT * FROM evse WHERE location_id IN " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>" +
            "   #{id}" +
            "</foreach>" +
            "ORDER BY update_time DESC" +
            "</script>")
    List<EVSE> selectByLocationIds(@Param("ids") List<Long> locationIds);
}