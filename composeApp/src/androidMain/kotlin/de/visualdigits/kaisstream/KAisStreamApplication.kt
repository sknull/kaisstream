package de.visualdigits.kaisstream

import android.app.Application
import co.touchlab.kermit.Logger
import de.visualdigits.kaisstream.di.platformModule
import de.visualdigits.kaisstream.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.GlobalContext.startKoin

class KAisStreamApplication: Application() {

    override fun onCreate() {
        Logger.i("Starting koin...")
        startKoin {
            androidContext(this@KAisStreamApplication)
            workManagerFactory()
            modules(sharedModule, platformModule)
        }

        // IMPORTANT do super create AFTER koin initializing to avoid problems with work managers
        Logger.i("Initializing application...")
        super.onCreate()

        Logger.i("Application initialized")
    }
}
