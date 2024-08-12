package org.pronet.shoppie.utils;

public class RequestMatcherPatterns {
    public static final String[] NON_AUTH_MATCHERS = {
            "/css/**", "/carousel/**", "/category-images/**",
            "/product-images/**", "/profile-images/**",
            "/", "/category/list", "/category/list/{id}/view-products",
            "/product/list", "/product/search",
            "/sign-up-view", "/sign-up", "/sign-in"
    };
    public static final String[] USER_AUTH_MATCHERS = {
            "/product/details/{id}", "/cart/**", "/order/**"
    };
    public static final String[] ADMIN_AUTH_MATCHERS = {
            "/admin/**"
    };
    public static final String[] USER_AND_ADMIN_AUTH_MATCHERS = {
            "/delete-account", "/forgot-password", "/forgot-password-view", "/reset-password",
            "/my-profile", "/update-profile", "/change-password", "/settings"
    };
}
