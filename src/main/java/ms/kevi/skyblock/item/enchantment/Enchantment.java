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

package ms.kevi.skyblock.item.enchantment;

import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ms.kevi.skyblock.game.booster.StatsBooster;

@Getter
@Setter
@AllArgsConstructor
public abstract class Enchantment {

    protected int level;

    public abstract String getName();

    public abstract int getMaxLevel();

    public abstract StatsBooster getStatsBooster();

    public abstract String getDescription();

    public int getNormalId() {
        return -1;
    }

    public void preAttack(Entity attacker, Entity entity) {

    }

    public void postAttack(Entity attacker, Entity entity) {

    }

    public boolean isCompatibleWith(Enchantment enchantment) {
        return this != enchantment;
    }

    public boolean canEnchant(Item item) {
        return true;
    }

    public boolean isSameType(Enchantment enchantment) {
        return this.getClass().equals(enchantment.getClass());
    }

}
