package com.kvsoftware.dependencyinjectionhilt.data.rest.service

import com.kvsoftware.dependencyinjectionhilt.data.model.CountryDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryService {
    @GET("countries")
    suspend fun getCountries(@Query("sort") sort: String? = null): List<CountryDataModel>
}