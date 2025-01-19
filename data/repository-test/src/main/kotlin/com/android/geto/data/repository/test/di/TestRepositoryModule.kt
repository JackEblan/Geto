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
package com.android.geto.data.repository.test.di

import com.android.geto.data.repository.di.RepositoryModule
import com.android.geto.data.repository.test.FakeAppSettingsRepository
import com.android.geto.data.repository.test.FakeSecureSettingsRepository
import com.android.geto.data.repository.test.FakeShortcutRepository
import com.android.geto.data.repository.test.FakeUserDataRepository
import com.android.geto.data.repository.test.StubGetoApplicationInfoRepository
import com.android.geto.domain.repository.AppSettingsRepository
import com.android.geto.domain.repository.GetoApplicationInfosRepository
import com.android.geto.domain.repository.SecureSettingsRepository
import com.android.geto.domain.repository.ShortcutRepository
import com.android.geto.domain.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class],
)
internal interface TestRepositoryModule {

    @Binds
    @Singleton
    fun appSettingsRepository(impl: FakeAppSettingsRepository): AppSettingsRepository

    @Binds
    @Singleton
    fun secureSettingsRepository(impl: FakeSecureSettingsRepository): SecureSettingsRepository

    @Binds
    @Singleton
    fun shortcutRepository(impl: FakeShortcutRepository): ShortcutRepository

    @Binds
    @Singleton
    fun userDataRepository(impl: FakeUserDataRepository): UserDataRepository

    @Binds
    @Singleton
    fun getoApplicationInfosRepository(impl: StubGetoApplicationInfoRepository): GetoApplicationInfosRepository
}
