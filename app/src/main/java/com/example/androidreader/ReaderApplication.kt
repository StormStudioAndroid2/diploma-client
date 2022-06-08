package com.example.androidreader

import android.app.Application
import com.example.androidreader.dagger.component.BookListComponent
import com.example.androidreader.dagger.component.MainComponent
import com.example.androidreader.dagger.component.WordInfoComponent
import com.example.androidreader.dagger.module.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class, DispatcherModule::class, SubcomponentsModule::class, UsecaseModule::class, RepositoryModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): ApplicationComponent
    }

    fun mainComponent(): MainComponent.Factory

    fun wordInfoComponent() : WordInfoComponent.Factory

    fun bookListComponent(): BookListComponent.Factory
}

class ReaderApplication : Application() {
    val appComponent = DaggerApplicationComponent.factory().create(this)
}