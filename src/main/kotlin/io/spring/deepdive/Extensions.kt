/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.spring.deepdive

import java.text.Normalizer
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.time.temporal.TemporalAccessor
import java.util.Locale

private val daysLookup = (1..31).associate { it.toLong() to getOrdinal(it) }

val englishDateFormatter = DateTimeFormatterBuilder()
        .appendPattern("MMMM")
        .appendLiteral(" ")
        .appendText(ChronoField.DAY_OF_MONTH, daysLookup)
        .appendLiteral(" ")
        .appendPattern("yyyy")
        .toFormatter(Locale.ENGLISH)

fun String.slugify() = toLowerCase()
        .stripAccents()
        .replace("\n".toRegex(), " ")
        .replace("[^a-z\\d\\s]".toRegex(), " ")
        .split(" ")
        .joinToString("-")
        .replace("-+".toRegex(), "-")


fun String.stripAccents() = Normalizer
        .normalize(this, Normalizer.Form.NFD)
        .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")

fun TemporalAccessor.formatDate() = englishDateFormatter.format(this)

fun getOrdinal(n: Int) = when {
    (n in 11..13) ->  "${n}th"
    (n % 10 == 1) -> "${n}st"
    (n % 10 == 2) -> "${n}nd"
    (n % 10 == 3) -> "${n}rd"
    else -> "${n}th"
}
