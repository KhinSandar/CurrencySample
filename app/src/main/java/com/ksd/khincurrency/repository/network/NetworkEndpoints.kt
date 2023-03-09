package com.ksd.khincurrency.repository.network

const val BASE_URL: String = "https://openexchangerates.org/api/"
const val PATH_CURRENCY: String = "latest.json"
const val PATH_CURRENCY_INFO: String = "$PATH_CURRENCY/{app_id}"
const val PATH_USER_REPOSITORIES: String = "$PATH_CURRENCY/{userId}/repos"