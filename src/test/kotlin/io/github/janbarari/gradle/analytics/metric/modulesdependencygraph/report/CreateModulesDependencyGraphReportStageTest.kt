/**
 * MIT License
 * Copyright (c) 2024 Mehdi Janbarari (@janbarari)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.janbarari.gradle.analytics.metric.modulesdependencygraph.report

import io.github.janbarari.gradle.TowerMockImpl
import io.github.janbarari.gradle.analytics.domain.model.ModuleDependency
import io.github.janbarari.gradle.analytics.domain.model.metric.BuildMetric
import io.github.janbarari.gradle.analytics.domain.model.metric.ModulesDependencyGraphMetric
import io.github.janbarari.gradle.analytics.domain.model.report.Report
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class CreateModulesDependencyGraphReportStageTest {

    @Test
    fun `check process() generates report when metric is not available`() = runBlocking {
        val metrics = mutableListOf<BuildMetric>()
        val stage = CreateModulesDependencyGraphReportStage(TowerMockImpl(), metrics)
        var report = Report("main", "assemble")
        report = stage.process(report)

        assertEquals(0, report.modulesDependencyGraphReport!!.dependencies.size)
    }

    @Test
    fun `check process() generates report when metric is available`() = runBlocking {
        val metrics = mutableListOf<BuildMetric>()
        metrics.add(
            BuildMetric(
                branch = "main",
                requestedTasks = listOf("assemble"),
                createdAt = 1668836798265,
                gitHeadCommitHash = UUID.randomUUID().toString(),
                modulesDependencyGraphMetric = ModulesDependencyGraphMetric(
                    dependencies = listOf(
                        ModuleDependency(
                            path = ":freedom",
                            configuration = "implementation",
                            dependency = ":life"
                        ),
                        ModuleDependency(
                            path = ":life",
                            configuration = "implementation",
                            dependency = ":woman"
                        ),
                        ModuleDependency(
                            path = ":woman",
                            configuration = "implementation",
                            dependency = ""
                        )
                    )
                ),
                modules = setOf(":woman", ":life", ":freedom")
            )
        )

        metrics.add(
            BuildMetric(
                branch = "main",
                requestedTasks = listOf("assemble"),
                createdAt = 1668936974389,
                gitHeadCommitHash = UUID.randomUUID().toString(),
                modulesDependencyGraphMetric = ModulesDependencyGraphMetric(
                    dependencies = listOf(
                        ModuleDependency(
                            path = ":freedom",
                            configuration = "implementation",
                            dependency = ":life"
                        ),
                        ModuleDependency(
                            path = ":life",
                            configuration = "implementation",
                            dependency = ":woman"
                        ),
                        ModuleDependency(
                            path = ":woman",
                            configuration = "implementation",
                            dependency = ""
                        )
                    )
                ),
                modules = setOf(":woman", ":life", ":freedom")
            )
        )


        val stage = CreateModulesDependencyGraphReportStage(TowerMockImpl(), metrics)
        var report = Report("main", "assemble")
        report = stage.process(report)

        assertEquals(3, report.modulesDependencyGraphReport!!.dependencies.size)

    }

}
