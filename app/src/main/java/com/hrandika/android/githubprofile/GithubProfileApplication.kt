package com.hrandika.android.githubprofile

import android.app.Application
import com.hrandika.android.core.ApolloModule
import com.hrandika.android.githubprofile.di.ApplicationComponent
import com.hrandika.android.githubprofile.di.ApplicationComponentProvider
import com.hrandika.android.githubprofile.di.DaggerApplicationComponent
import com.hrandika.android.profile.di.DaggerProfileComponent
import com.hrandika.android.profile.di.ProfileComponent
import com.hrandika.android.profile.di.ProfileComponentProvider

class GithubProfileApplication : Application(),
    ApplicationComponentProvider, ProfileComponentProvider {

    private val apolloModule: ApolloModule by lazy {
        ApolloModule(this)
    }

    override fun getApplicationComponent(): ApplicationComponent {
        return DaggerApplicationComponent.create()
    }

    override fun provideProfileComponent(): ProfileComponent {
        return DaggerProfileComponent.builder().apolloModule(apolloModule).build()
    }

}