package com.android.geto

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project

val Project.libs
    get(): LibrariesForLibs = extensions.getByName("libs") as LibrariesForLibs