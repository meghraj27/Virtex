package com.meghrajswami.virtex.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    private static final String VERSION_KEY = "version";

    @Value("${application.version}")
    private String VERSION;

//    private static final String APP_NAME_KEY = "app_name";
//
//    @Value("${application.title}")
//    private String APP_NAME;

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        // Let Spring handle the error first, we will modify later :)
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

        errorAttributes.put(VERSION_KEY, VERSION);
//        errorAttributes.put(APP_NAME_KEY, APP_NAME);

        return errorAttributes;

    }
}