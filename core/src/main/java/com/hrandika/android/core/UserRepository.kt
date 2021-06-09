package com.hrandika.android.core

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import kotlinx.coroutines.*
import javax.inject.Inject

class UserRepository @Inject constructor(var apolloClient: ApolloClient) {

    suspend fun getUser(login: String): GithubUserQuery.User? = withContext(Dispatchers.IO) {
        val await = apolloClient.query(GithubUserQuery(login = login)).await()
        await.data?.user
    }

}