package com.zhipu.oapi.core.token;

import com.zhipu.oapi.core.cache.LocalCache;
import com.zhipu.oapi.core.token.TokenManager;
import com.zhipu.oapi.core.token.TokenManagerV4;

public class GlobalTokenManager {

    private static volatile com.zhipu.oapi.core.token.TokenManager globalTokenManager = new com.zhipu.oapi.core.token.TokenManager(
            LocalCache.getInstance());

    public static com.zhipu.oapi.core.token.TokenManager getTokenManager() {
        return globalTokenManager;
    }

    public static void setTokenManager(TokenManager tokenManager) {
        globalTokenManager = tokenManager;
    }



    private static volatile com.zhipu.oapi.core.token.TokenManagerV4 globalTokenManagerV4 = new com.zhipu.oapi.core.token.TokenManagerV4(
            LocalCache.getInstance());



    public static com.zhipu.oapi.core.token.TokenManagerV4 getTokenManagerV4() {
        return globalTokenManagerV4;
    }

    public static void setTokenManager(TokenManagerV4 tokenManager) {
        globalTokenManagerV4 = tokenManager;
    }}
