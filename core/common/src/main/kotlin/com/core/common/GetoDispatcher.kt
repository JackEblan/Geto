package com.core.common

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val getoDispatcher: GetoDispatchers)

enum class GetoDispatchers {
    Default, IO,
}