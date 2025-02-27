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
package io.github.janbarari.gradle.analytics.database

/**
 * Since tables uses auto incremental number to generate unique id, the auto-incremental
 * won't reset after table cleared, This class helps to reset auto-incremental numbers
 * of a table. also it will do it for both MySql and Sqlite database.
 */
object ResetAutoIncremental {
    lateinit var dbType: Class<out DatabaseConnection>

    fun getQuery(tableName: String): String? {
        if (this::dbType.isInitialized) {
            if (dbType == MySqlDatabaseConnection::class.java) {
                return "ALTER TABLE $tableName AUTO_INCREMENT = 0;"
            }
            if (dbType == SqliteDatabaseConnection::class.java) {
                return "UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='$tableName';"
            }
            if (dbType == PostgresDatabaseConnection::class.java) {
                return "ALTER SEQUENCE $tableName RESTART WITH 0;"
            }
        }
        return null
    }
}
