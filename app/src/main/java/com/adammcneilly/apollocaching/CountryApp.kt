package com.adammcneilly.apollocaching

import android.app.Application
import com.adammcneilly.apollocaching.di.networkingModule
import com.adammcneilly.apollocaching.di.repositoriesModule
import com.adammcneilly.apollocaching.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CountryApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CountryApp)
            modules(networkingModule)
            modules(repositoriesModule)
            modules(viewModelModule)
        }
    }
}
