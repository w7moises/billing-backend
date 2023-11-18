package com.app.billing.service;

import java.util.Locale;

public interface MessageService {
    String getMessage(String code);
    String getMessage(String code, Object[] args);
    String getMessage(String code, Locale locale);
    String getMessage(String code, Object[] args, Locale locale);
}
