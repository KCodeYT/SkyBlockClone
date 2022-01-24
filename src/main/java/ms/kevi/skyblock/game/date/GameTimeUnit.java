/*
 * Copyright 2022 KCodeYT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ms.kevi.skyblock.game.date;

class GameTimeUnit {

    static long toSeconds(long millis) {
        return millis / 60L;
    }

    static long toMinutes(long millis) {
        return millis / 830L;
    }

    static long toHours(long millis) {
        return toMinutes(millis) / 60L;
    }

    static long toDays(long millis) {
        return toHours(millis) / 24L;
    }

    static long toMonths(long millis) {
        return toDays(millis) / 31L;
    }

    static long toYears(long millis) {
        return toMonths(millis) / 12L;
    }

}
