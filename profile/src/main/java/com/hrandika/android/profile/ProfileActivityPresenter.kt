package com.hrandika.android.profile

import com.hrandika.android.core.GithubUserQuery
import com.hrandika.android.core.UserRepository
import javax.inject.Inject

class ProfileActivityPresenter @Inject constructor(private var userRepository: UserRepository) {

    suspend fun getUserProfile(userName: String?): GithubUserQuery.User? {
        return this.userRepository.getUser(userName!!)
    }

}