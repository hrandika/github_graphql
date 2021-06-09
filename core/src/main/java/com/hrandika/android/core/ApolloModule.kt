package com.hrandika.android.core

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// Place your github token with client having email permissions
const val token = ""

@Module
class ApolloModule(var context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun interceptor(context: Context): AuthorizationInterceptor {
        return AuthorizationInterceptor(context)
    }

    @Singleton
    @Provides
    fun apolloClient(authorizationInterceptor: AuthorizationInterceptor): ApolloClient {
        val build = OkHttpClient.Builder().addInterceptor(authorizationInterceptor).build()
        val lruNormalizedCacheFactory = LruNormalizedCacheFactory(
            EvictionPolicy.builder().expireAfterWrite(1, TimeUnit.DAYS).build()
        )
        return ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .normalizedCache(lruNormalizedCacheFactory)
            .okHttpClient(build)
            .build()
    }

}

class AuthorizationInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}