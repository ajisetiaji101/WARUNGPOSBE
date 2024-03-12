package com.warungposbespring.warungposbe.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class WebAdvice {
    @ExceptionHandler({Exception.class, RuntimeException.class, AppException.class})
    @ResponseBody
    public ResponseEntity<?> handleExceptions(Exception e, HttpServletResponse res, HttpServletRequest req) {
        try {
//            AppAuthentication auth = SecurityUtil.getAuth();
//            String user = auth != null && auth.getDetails() != null ? auth.getDetails().getUsername() : "?";
//            log.error("> api-endpoint: {} {} ; user: {} ;req content-type: {}, res content-type: {} ;\n> message: {}", req.getMethod(), req.getRequestURI(), user, req.getContentType(), res.getContentType(), (e.getClass().getSimpleName() + " | " + e.getMessage()));
            if (e instanceof NullPointerException) {
                writeExceptionStackTrace(e, 17);
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonResponse.error("Something bad happen on app server please try again later, contact support for this error",
                                getStackTraceStringArray(e.getStackTrace())));
            } else if (e instanceof AppException) {
                AppException appException = (AppException) e;
                return ResponseEntity
                        .badRequest().body(appException.generateJsonResponse());
            } else if (e instanceof RuntimeException) {
                writeExceptionStackTrace(e, 17);
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(JsonResponse.get(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
            }
            writeExceptionStackTrace(e, -1);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JsonResponse.error("Something bad happen on app server please try again later, contact support for this error: [" + e.getMessage() + "]",
                            getStackTraceStringArray(e.getStackTrace())));
        } catch (Exception ee) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JsonResponse.error("[WebAdvice-Level] Something bad happen on app server please try again later, contact support for this error: [" + e.getMessage() + "]",
                            getStackTraceStringArray(ee.getStackTrace())));
        }
    }

    private void writeExceptionStackTrace(Exception e, int maxLine) {
        if (e != null) {
            StackTraceElement[] es = e.getStackTrace();
            int i = 1;
            for (StackTraceElement stackTraceElement : es) {
                if (i > maxLine && maxLine > -1) break;
                log.error(stackTraceElement.toString());
                i++;
            }
        }
    }

    private String[] getStackTraceStringArray(StackTraceElement[] traceElements) {
        String[] strings = new String[traceElements.length];
        for (int i = 0; i < traceElements.length; i++) {
            strings[i] = traceElements[i].toString();
        }
        return strings;
    }
}
