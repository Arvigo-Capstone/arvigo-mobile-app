package id.arvigo.arvigobasecore

import android.app.Application
import id.arvigo.arvigobasecore.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@MyApplication)
            modules(networkModule, viewModelModules, useCaseModule, dataPreferencesModule)
        }
    }
}