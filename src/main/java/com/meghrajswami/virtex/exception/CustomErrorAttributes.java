package com.meghrajswami.virtex.exception;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    private static final String VERSION_KEY = "version";
    private static final String VERSION = "1.0";

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        // Let Spring handle the error first, we will modify later :)
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

        // insert a new key 'version'
        errorAttributes.put(VERSION_KEY, VERSION);

        return errorAttributes;

    }
}