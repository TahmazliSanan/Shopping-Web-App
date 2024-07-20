package org.pronet.shoppie.utils;

public class RequestMatcherPatterns {
    public static final String[] NON_AUTH_MATCHERS = {
            "/css/**", "/carousel/**", "/category-images/**",
            "/product-images/**", "/profile-images/**",
            "/", "/category/list", "/product/list",
            "/sign-up-view", "/sign-up", "/sign-in",
            "/forgot-password", "/forgot-password-view", "/reset-password"
    };
    public static final String[] USER_AUTH_MATCHERS = {
            "/product/details/{id}", "/cart/**", "/order-cart"
    };
    public static final String[] ADMIN_AUTH_MATCHERS = {
            "/admin/**"
    };
}
