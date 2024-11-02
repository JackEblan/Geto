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
package com.android.geto.benchmarks

import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.TraceSectionMetric

/**
 * Custom Metrics to measure baseline profile effectiveness.
 */
class BaselineProfileMetrics {
    companion object {
        /**
         * A [TraceSectionMetric] that tracks the time spent in JIT compilation.
         *
         * This number should go down when a baseline profile is applied properly.
         */
        @OptIn(ExperimentalMetricApi::class)
        val jitCompilationMetric = TraceSectionMetric("JIT Compiling %", label = "JIT compilation")

        /**
         * A [TraceSectionMetric] that tracks the time spent in class initialization.
         *
         * This number should go down when a baseline profile is applied properly.
         */
        @OptIn(ExperimentalMetricApi::class)
        val classInitMetric = TraceSectionMetric("L%/%;", label = "ClassInit")

        /**
         * Metrics relevant to startup and baseline profile effectiveness measurement.
         */
        @OptIn(ExperimentalMetricApi::class)
        val allMetrics = listOf(StartupTimingMetric(), jitCompilationMetric, classInitMetric)
    }
}
