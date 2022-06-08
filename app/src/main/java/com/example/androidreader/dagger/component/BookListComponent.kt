package com.example.androidreader.dagger.component

import com.example.androidreader.dagger.ActivityScope
import com.example.androidreader.presentation.screen.BookFragment
import com.example.androidreader.presentation.screen.BookListFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface BookListComponent {
    fun inject(activity: BookListFragment)


    @Subcomponent.Factory
    interface Factory {
        fun create(): BookListComponent

    }
}