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

package ms.kevi.skyblock.game.attribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ms.kevi.skyblock.game.stats.GameStats;

@Getter
@AllArgsConstructor
public class Regeneration {

    private final GameStats gameStats;
    private final double percent;

    void apply(GameAttributes gameAttributes) {
        final Attribute attribute = gameAttributes.get(this.gameStats);
        if(attribute.getValue() < attribute.getMaxValue())
            attribute.setValue(attribute.getValue() + (int) ((double) attribute.getMaxValue() / 100 * this.percent));
    }

    void apply(GameAttributes gameAttributes, double fullPercent) {
        final Attribute attribute = gameAttributes.get(this.gameStats);
        if(attribute.getValue() < attribute.getMaxValue())
            attribute.setValue(attribute.getValue() + (int) ((double) attribute.getMaxValue() / 100 * fullPercent));
    }

}
