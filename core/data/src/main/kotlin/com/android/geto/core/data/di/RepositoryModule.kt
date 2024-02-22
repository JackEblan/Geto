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

package com.android.geto.core.data.di

import com.android.geto.core.data.repository.DefaultAppSettingsRepository
import com.android.geto.core.data.repository.DefaultClipboardRepository
import com.android.geto.core.data.repository.DefaultPackageRepository
import com.android.geto.core.data.repository.DefaultSecureSettingsRepository
import com.android.geto.core.domain.repository.AppSettingsRepository
import com.android.geto.core.domain.repository.ClipboardRepository
import com.android.geto.core.domain.repository.PackageRepository
import com.android.geto.core.domain.repository.SecureSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun appSettingsRepository(impl: DefaultAppSettingsRepository): AppSettingsRepository

    @Binds
    @Singleton
    fun secureSettingsRepository(impl: DefaultSecureSettingsRepository): SecureSettingsRepository

    @Binds
    @Singleton
    fun packageRepository(impl: DefaultPackageRepository): PackageRepository

    @Binds
    @Singleton
    fun clipboardRepository(impl: DefaultClipboardRepository): ClipboardRepository
}