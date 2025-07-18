package com.emsp.chargestation.util;

import com.emsp.chargestation.exception.BusinessException;

import java.util.regex.Pattern;

public class EVSEIdValidator {
    private static final Pattern EVSE_ID_PATTERN = Pattern.compile("^[A-Z]{2}\\*[A-Z0-9]{3}\\*[A-Z0-9]{1,30}$");
    
    public static boolean isValid(String evseId) {
        if (evseId == null) {
            return false;
        }
        return EVSE_ID_PATTERN.matcher(evseId).matches();
    }
    
    public static void validate(String evseId) {
        if (!isValid(evseId)) {
            throw new BusinessException("EVSE ID格式不正确，格式应为: <国家代码>*<运营商代码>*<本地设备ID>");
        }
    }
}