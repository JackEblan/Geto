/*
 *
 *   Copyright 2023 Einstein Blanco
 *
 *   Licensed under the GNU General Public License v3.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.gnu.org/licenses/gpl-3.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package com.android.geto.core.database.di

import android.content.Context
import androidx.room.Room
import com.android.geto.core.database.AppDatabase
import com.android.geto.core.database.migration.Migration1To2
import com.android.geto.core.database.migration.Migration2To3
import com.android.geto.core.database.migration.Migration3To4
import com.android.geto.core.database.migration.Migration4To5
import com.android.geto.core.database.migration.Migration5To6
import com.android.geto.core.database.migration.Migration6To7
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun appDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME,
    ).addMigrations(
        Migration1To2(),
        Migration2To3(),
        Migration3To4(),
        Migration4To5(),
        Migration5To6(),
        Migration6To7(),
    ).build()
}
