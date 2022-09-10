package com.country.grocerystore.api

import com.country.grocerystore.api.model.GroceryData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GroceryService {
    @GET("/resource/9ef84268-d588-465a-a308-a864a43d0070")
    suspend fun getGroceryData(
        @Query("api-key",  encoded = false) apiKey: String,
        @Query("format",  encoded = false) format: String,
        @Query("limit",  encoded = false) limit: String,
    ) : Response<GroceryData>?
}