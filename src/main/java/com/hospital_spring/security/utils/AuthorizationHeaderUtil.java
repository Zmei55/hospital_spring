package com.hospital_spring.security.utils;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationHeaderUtil {
    boolean hasAuthorizationToken(HttpServletRequest request); // проверка есть ли в заголовках токен ("Bearer ...")

    String getToken(HttpServletRequest request); // получает токен из строки заголовка ("...")
}
