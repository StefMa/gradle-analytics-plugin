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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.janbarari.gradle.analytics.metric.modulestimeline.report

import io.github.janbarari.gradle.TowerMockImpl
import io.github.janbarari.gradle.analytics.domain.model.metric.ModuleTimeline
import io.github.janbarari.gradle.analytics.domain.model.report.ModulesTimelineReport
import io.github.janbarari.gradle.analytics.domain.model.report.Report
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RenderModulesTimelineReportStageTest {

    @Test
    fun `check render when report is null`() = runBlocking {
        val report = Report("main", "assemble")

        val renderTemplate = "%modules-execution-timeline-metric%"
        val stage = RenderModulesTimelineReportStage(TowerMockImpl(), report)
        val result = stage.process(renderTemplate)

        val expectedAnswer = "<p>Modules Execution Timeline is not available!</p><div class=\"space\"></div>"
        assertEquals(expectedAnswer, result)
    }

    @Test
    fun `check render when report is available`() = runBlocking {
        val report = Report("main", "assemble")
        report.modulesTimelineReport = ModulesTimelineReport(
            start = 0,
            end = 100,
            modules = listOf(
                ModuleTimeline(
                    path = ":woman",
                    timelines = listOf(
                        ModuleTimeline.Timeline(
                            path = ":task1",
                            start = 0,
                            end = 50,
                            isCached = true
                        ),
                        ModuleTimeline.Timeline(
                            path = ":task2",
                            start = 50,
                            end = 100,
                            isCached = false
                        )
                    )
                ),
                ModuleTimeline(
                    path = ":life",
                    timelines = listOf(
                        ModuleTimeline.Timeline(
                            path = ":task1",
                            start = 0,
                            end = 50,
                            isCached = true
                        ),
                        ModuleTimeline.Timeline(
                            path = ":task2",
                            start = 50,
                            end = 100,
                            isCached = false
                        )
                    )
                ),
                ModuleTimeline(
                    path = ":freedom",
                    timelines = listOf(
                        ModuleTimeline.Timeline(
                            path = ":task1",
                            start = 0,
                            end = 50,
                            isCached = true
                        ),
                        ModuleTimeline.Timeline(
                            path = ":task2",
                            start = 50,
                            end = 100,
                            isCached = false
                        )
                    )
                )
            ),
            createdAt = 1668836798265
        )

        val renderTemplate = "%modules-execution-timeline-metric%"
        val stage = RenderModulesTimelineReportStage(TowerMockImpl(), report)
        val result = stage.process(renderTemplate)

        assertTrue {
            result.contains("var data = [\n" +
                    "{\n" +
                    "label: \":woman\",\n" +
                    "times: [\n" +
                    "{ \"color\": \"#999999\", \"starting_time\": 0, \"ending_time\": 50 },\n" +
                    "{ \"color\": \"#3b76af\", \"starting_time\": 50, \"ending_time\": 100 },\n" +
                    "]\n" +
                    "},\n" +
                    "{\n" +
                    "label: \":life\",\n" +
                    "times: [\n" +
                    "{ \"color\": \"#999999\", \"starting_time\": 0, \"ending_time\": 50 },\n" +
                    "{ \"color\": \"#b3c6e5\", \"starting_time\": 50, \"ending_time\": 100 },\n" +
                    "]\n" +
                    "},\n" +
                    "{\n" +
                    "label: \":freedom\",\n" +
                    "times: [\n" +
                    "{ \"color\": \"#999999\", \"starting_time\": 0, \"ending_time\": 50 },\n" +
                    "{ \"color\": \"#ef8536\", \"starting_time\": 50, \"ending_time\": 100 },\n" +
                    "]\n" +
                    "},\n" +
                    "\n" +
                    "];")
        }

        assertTrue {
            result.contains("var chart = d3.timeline()\n" +
                    "          .beginning(0)\n" +
                    "          .ending(100)")
        }
    }

}
