package com.hrandika.android.profile.di

import com.hrandika.android.core.ApolloModule
import com.hrandika.android.profile.ProfileActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApolloModule::class])
interface ProfileComponent {
    fun inject(activity: ProfileActivity)
}