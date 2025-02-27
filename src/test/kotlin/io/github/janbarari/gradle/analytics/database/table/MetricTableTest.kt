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
package io.github.janbarari.gradle.analytics.database.table

import org.jetbrains.exposed.sql.autoIncColumnType
import org.jetbrains.exposed.sql.isAutoInc
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MetricTableTest {

    @Test
    fun `check number column`() {
        assertEquals(true, MetricTable.number.autoIncColumnType?.isAutoInc)
        assertEquals("number", MetricTable.number.name)
        assertEquals(false, MetricTable.number.columnType.nullable)
    }

    @Test
    fun `check createdAt column`() {
        assertEquals(false, MetricTable.createdAt.autoIncColumnType?.isAutoInc ?: false)
        assertEquals("created_at", MetricTable.createdAt.name)
        assertEquals(false, MetricTable.createdAt.columnType.nullable)
    }

    @Test
    fun `check branch column`() {
        assertEquals(false, MetricTable.branch.autoIncColumnType?.isAutoInc ?: false)
        assertEquals("branch", MetricTable.branch.name)
        assertEquals(false, MetricTable.branch.columnType.nullable)
    }

    @Test
    fun `check requestedTasks column`() {
        assertEquals(false, MetricTable.requestedTasks.autoIncColumnType?.isAutoInc ?: false)
        assertEquals("requested_tasks", MetricTable.requestedTasks.name)
        assertEquals(false, MetricTable.requestedTasks.columnType.nullable)
    }

    @Test
    fun `check value column`() {
        assertEquals(false, MetricTable.value.autoIncColumnType?.isAutoInc ?: false)
        assertEquals("value", MetricTable.value.name)
        assertEquals(false, MetricTable.value.columnType.nullable)
    }

}