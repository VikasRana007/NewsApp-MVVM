package me.vikas.newsapp.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider : DispatcherProvider {

    override val main: CoroutineDispatcher
        field = UnconfinedTestDispatcher()

    override val io: CoroutineDispatcher
        get() = main

    override val default: CoroutineDispatcher
        get() = main


}