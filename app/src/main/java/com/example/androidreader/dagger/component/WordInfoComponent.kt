package com.example.androidreader.dagger.component

import com.example.androidreader.dagger.ActivityScope
import com.example.androidreader.presentation.screen.WordInfoFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface WordInfoComponent {
    fun inject(activity: WordInfoFragment)


    @Subcomponent.Factory
    interface Factory {
        fun create(): WordInfoComponent

    }
}