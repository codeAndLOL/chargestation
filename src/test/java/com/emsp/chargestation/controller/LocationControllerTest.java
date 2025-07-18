package com.emsp.chargestation.controller;

import com.emsp.chargestation.dto.LocationCreateDTO;
import com.emsp.chargestation.dto.LocationQueryDTO;
import com.emsp.chargestation.entity.Location;
import com.emsp.chargestation.service.LocationService;
import com.emsp.chargestation.vo.LocationPageVo;
import com.emsp.chargestation.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LocationService locationService;

    private LocationCreateDTO validCreateDto;
    private LocationQueryDTO validQueryDto;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        validCreateDto = new LocationCreateDTO();
        validCreateDto.setName("上海测试站");
        validCreateDto.setAddress("浦东新区张江高科技园区");
        validCreateDto.setLatitude(new BigDecimal("31.230416"));
        validCreateDto.setLongitude(new BigDecimal("121.473701"));

        validQueryDto = new LocationQueryDTO();
        validQueryDto.setPageNum(1);
        validQueryDto.setPageSize(10);
        validQueryDto.setUpdateTimeStart(LocalDateTime.now().minusDays(1));
        validQueryDto.setUpdateTimeEnd(LocalDateTime.now());
    }

    // ========== 创建站点测试 ==========
    @Test
    void createLocation_ShouldReturnSuccess_WhenInputValid() throws Exception {
        Location mockLocation = new Location();
        mockLocation.setId(1L);
        mockLocation.setName(validCreateDto.getName());

        Mockito.when(locationService.createLocation(Mockito.any(LocationCreateDTO.class)))
               .thenReturn(mockLocation);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").exists());
    }


    // ========== 更新站点测试 ==========
    @Test
    void updateLocation_ShouldReturnUpdatedLocation_WhenIdExists() throws Exception {
        Long locationId = 1L;
        Location updatedLocation = new Location();
        updatedLocation.setId(locationId);
        updatedLocation.setName("更新后的名称");

        Mockito.when(locationService.updateLocation(locationId, validCreateDto))
               .thenReturn(updatedLocation);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/locations/" + locationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("更新后的名称"));
    }


    // ========== 分页查询测试 ==========
    @Test
    void queryLocations_ShouldReturnPagedResult_WithValidQuery() throws Exception {
        PageInfo<LocationPageVo> mockPage = new PageInfo<>(Collections.emptyList());
        Mockito.when(locationService.queryLocationsWithEvses(Mockito.any(LocationQueryDTO.class)))
               .thenReturn(mockPage);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/locations/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validQueryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.list").exists());
    }

    @Test
    void queryLocations_ShouldReturnAll_WhenNoTimeRange() throws Exception {
        LocationQueryDTO noTimeRangeQuery = new LocationQueryDTO();
        noTimeRangeQuery.setPageNum(1);
        noTimeRangeQuery.setPageSize(10);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/locations/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noTimeRangeQuery)))
                .andExpect(status().isOk());
    }
}