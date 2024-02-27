package com.juan.superheroapp_bbdd.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.security.interfaces.RSAKey

interface ApiService {
    @GET("/api/10229233666327556/search/sp")
    suspend fun getSuperheroes(): Response<SuperHeroDataResponse>

    @GET("/api/10229233666327556/search/sp")
    suspend fun getSuperheroDetail(): Response<SuperHeroDetailResponse>
}