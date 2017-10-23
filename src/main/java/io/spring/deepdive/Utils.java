/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package io.spring.deepdive;

import java.text.Normalizer;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Utils {

    private static Map<Long, String> daysLookup =
            IntStream.rangeClosed(1, 31).boxed().collect(Collectors.toMap(Integer::longValue, Utils::getOrdinal));

    private static DateTimeFormatter englishDateFormatter = new DateTimeFormatterBuilder()
            .appendPattern("MMMM")
            .appendLiteral(" ")
            .appendText(ChronoField.DAY_OF_MONTH, daysLookup)
            .appendLiteral(" ")
            .appendPattern("yyyy")
            .toFormatter(Locale.ENGLISH);

    public static String slugify(String text) {
        return String.join("-", stripAccents(text.toLowerCase())
                .replaceAll("\n", " ")
                .replaceAll("[^a-z\\d\\s]", " ")
                .split(" "))
                .replaceAll("-+", "-");
    }

    public static String stripAccents(String text) {
        return Normalizer
                .normalize(text, Normalizer.Form.NFD)
                .replace("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static String formatToEnglish(TemporalAccessor temporal) {
        return englishDateFormatter.format(temporal);
    }

    private static String getOrdinal(int n) {
        if (n >= 11 && n <= 13 ) {
            return n + "th";
        }
        if ( n % 10 == 1) {
            return n + "st";
        }
        if ( n % 10 == 2) {
            return n + "nd";
        }
        if ( n % 10 == 3) {
            return n + "rd";
        }
        return n + "th";
    }

}
