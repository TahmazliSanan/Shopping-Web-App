package org.pronet.shoppie.utils;

public class AppConstants {
    public static final long UNLOCK_DURATION_TIME = 3600000;
    public static final int ATTEMPT_COUNT = 3;
    public static final String[] NON_ACCOUNT_AUTH_PATTERNS = {
            "/css/**", "/carousel/**", "/category-images/**",
            "/product-images/**", "/profile-images/**",
            "/", "/category/list", "/product/list", "/sign-up", "/sign-in"
    };
    public static final String[] USER_ACCOUNT_AUTH_PATTERNS = {
            "/product/details/{id}"
    };
    public static final String[] ADMIN_ACCOUNT_AUTH_PATTERNS = {
            "/admin/**"
    };
}
