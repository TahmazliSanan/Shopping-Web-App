package org.pronet.shoppie.utils;

public class RequestMatcherPatterns {
    public static final String[] NON_AUTH_MATCHERS = {
            "/css/**", "/carousel/**", "/category-images/**",
            "/product-images/**", "/profile-images/**",
            "/", "/category/list", "/category/list/{id}/view-products",
            "/product/list", "/product/details/{id}",
            "/sign-up-view", "/sign-up", "/sign-in",
            "/forgot-password", "/forgot-password-view", "/reset-password"
    };
    public static final String[] USER_AUTH_MATCHERS = {
            "/cart/**", "/order/**"
    };
    public static final String[] ADMIN_AUTH_MATCHERS = {
            "/admin/**"
    };
    public static final String[] USER_AND_ADMIN_AUTH_MATCHERS = {
            "/delete-account", "/delete-profile-photo", "/my-profile",
            "/update-profile", "/change-password", "/settings"
    };
}
