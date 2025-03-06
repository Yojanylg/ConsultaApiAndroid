package com.example.examen
import com.example.examen.model.ProductoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ProductoApiService {

    @GET
    suspend fun getAll(@Url url : String): Response<ProductoResponse>

}