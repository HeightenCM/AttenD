package com.avilanii.attend.core.data

fun constructUrl(url: String): String {
    return when {
        url.contains(BuildConfig.BASE_API_URL) -> url
        url.startsWith("/") -> BuildConfig.BASE_API_URL + url.drop(1)
        else -> BuildConfig.BASE_API_URL + url
    }
}