package com.example.androidreader.dagger.module

import com.example.androidreader.dagger.component.MainComponent
import dagger.Module

@Module(subcomponents = [MainComponent::class])
class SubcomponentsModule {}