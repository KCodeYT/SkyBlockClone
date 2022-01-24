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

import lombok.Getter;
import lombok.Setter;
import ms.kevi.skyblock.game.stats.GameStats;

@Getter
public class Attribute {

    private final GameStats gameStats;
    private int lastMaxValue;
    @Setter
    private int forcedMaxValue;
    private int maxValue;
    private int value;

    Attribute(GameStats gameStats) {
        this.gameStats = gameStats;
        this.reset(false);
    }

    public void reset(boolean onlyMax) {
        this.lastMaxValue = this.forcedMaxValue;
        this.forcedMaxValue = 0;
        this.maxValue = 0;
        if(!onlyMax)
            this.value = this.gameStats.getBaseValue();
    }

    public void setMaxValue(int maxValue) {
        this.setMaxValue(maxValue, false);
    }

    public void setMaxValue(int maxValue, boolean force) {
        if(maxValue < 0 && !force)
            maxValue = 0;
        this.maxValue = maxValue;
        if(this.gameStats.isIStatic())
            this.setValue(this.maxValue, true);
        else if(this.forcedMaxValue != -1 && this.value > this.forcedMaxValue && !force)
            this.setValue(this.forcedMaxValue);
    }

    public void setValue(int value) {
        this.setValue(value, false);
    }

    public void setValue(int value, boolean force) {
        if(value < 0 && !force)
            value = 0;
        if(value > this.maxValue && !force)
            value = this.maxValue;
        if(this.forcedMaxValue != -1 && value > this.forcedMaxValue)
            value = this.forcedMaxValue;
        this.value = value;
    }

}
