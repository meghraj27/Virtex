package com.meghrajswami.virtex.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.meghrajswami.virtex.domain.User;
import com.meghrajswami.virtex.domain.form.ChangePasswordForm;
import com.meghrajswami.virtex.exception.ConfigurationException;
import com.meghrajswami.virtex.security.CustomUserDetails;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class Helper {

    /**
     * Represents the classes that there is no need to log.
     */
    @SuppressWarnings("rawtypes")
    private static final List<Class> NOLOGS = Arrays.asList(HttpServletRequest.class, HttpServletResponse.class,
            ModelAndView.class, ChangePasswordForm.class, MultipartFile[].class, ResponseEntity.class);

    /**
     * The object mapper.
     */
    public static final ObjectMapper MAPPER = new Jackson2ObjectMapperBuilder().serializationInclusion(JsonInclude.Include.NON_NULL)
            .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS).build().setFilterProvider(
                    new SimpleFilterProvider().addFilter("userBriefFilter", SimpleBeanPropertyFilter.serializeAll()));

    /**
     * Checks if the configuration is null or not.
     *
     * @param object the object
     * @param name   the fullName of object to display
     * @throws ConfigurationException if the configuration is null
     */
    public static void checkConfigNotNull(Object object, String name) {
        if (object == null) {
            throw new ConfigurationException(String.format("%s should be provided", name));
        }
    }

    /**
     * Get user from context authentication object.
     *
     * @return User current authenticated user
     */
    public static User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getUser();
    }

    /**
     * Logs message with <code>DEBUG</code> level.
     *
     * @param logger  the logger.
     * @param message the log message.
     */
    public static void logDebugMessage(Logger logger, String message) {
        if (logger.isDebugEnabled()) {
            logger.trace(message);
        }
    }

    /**
     * Logs for entrance into public methods at <code>DEBUG</code> level.
     *
     * @param logger     the logger.
     * @param signature  the signature.
     * @param paramNames the names of parameters to log (not Null).
     * @param params     the values of parameters to log (not Null).
     */
    public static void logEntrance(Logger logger, String signature, String[] paramNames, Object[] params) {
        if (logger.isDebugEnabled()) {
            String msg = String.format("Entering method %s.", signature) + toString(paramNames, params);
            logger.trace(msg);
        }
    }

    /**
     * Logs for exit from public methods at <code>DEBUG</code> level.
     *
     * @param logger    the logger.
     * @param signature the signature of the method to be logged.
     * @param value     the return value to log.
     */
    public static void logExit(Logger logger, String signature, Object value) {
        if (logger.isDebugEnabled()) {
            String msg = String.format("Exiting method %s.", signature);
            if (value != null) {
                msg += "Output parameter :" + toString(value);
            }
            logger.trace(msg);
        }
    }

    /**
     * Logs the given exception and message at <code>ERROR</code> level.
     *
     * @param <T>       the exception type.
     * @param logger    the logger.
     * @param signature the signature of the method to log.
     * @param ex        the exception to log.
     */
    public static <T extends Throwable> void logException(Logger logger, String signature, T ex) {
        logger.error(String.format("Error in method %s. Details:", signature) + ": " + ex.getMessage(), ex);
    }

    /**
     * Converts the parameters to string.
     *
     * @param paramNames the names of parameters.
     * @param params     the values of parameters.
     * @return the string
     */
    private static String toString(String[] paramNames, Object[] params) {
        StringBuilder sb = new StringBuilder("Input parameters:");
        sb.append("{");
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(paramNames[i]).append(":").append(toString(params[i]));
            }
        }
        sb.append("}.");
        return sb.toString();
    }

    /**
     * Converts the object to string.
     *
     * @param obj the object
     * @return the string representation of the object.
     */
    public static String toString(Object obj) {
        String result;
        try {
            if (NOLOGS.stream().anyMatch(s -> s.isInstance(obj))) {
                result = obj.getClass().getSimpleName();
            } else {
                result = MAPPER.writeValueAsString(obj);
            }
        } catch (JsonProcessingException e) {
            // Helper.logException(LogAspect.LOGGER, "com.meghrajswami.virtex.utils" +
            // ".Helper#toString", e);
            result = "The object can not be serialized by Jackson JSON mapper, error: " + e.toString();
        }
        return result;
    }

    /**
     * It checks whether a given string is null or empty.
     *
     * @param str the string to be checked
     * @return true if a given string is null or empty
     * @throws IllegalArgumentException throws if string is null or empty
     */
    public static boolean isNullOrEmpty(String str) throws IllegalArgumentException {
        return str == null || str.trim().isEmpty();
    }
}
