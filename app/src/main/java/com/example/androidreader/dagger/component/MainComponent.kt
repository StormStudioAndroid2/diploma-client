package com.example.androidreader.dagger.component

import com.example.androidreader.MainActivity
import com.example.androidreader.dagger.ActivityScope
import com.example.androidreader.presentation.screen.BookFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface MainComponent {
    fun inject(activity: BookFragment)


    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent

    }
}