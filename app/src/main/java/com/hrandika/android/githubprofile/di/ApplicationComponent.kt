package com.hrandika.android.githubprofile.di

import com.hrandika.android.githubprofile.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component()
interface ApplicationComponent {
    fun inject(activity: MainActivity)
}