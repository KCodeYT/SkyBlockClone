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

package ms.kevi.skyblock.item.custom.potion;

import cn.nukkit.item.Item;
import lombok.RequiredArgsConstructor;
import ms.kevi.skyblock.game.effect.Effect;
import ms.kevi.skyblock.game.effect.IEffect;
import ms.kevi.skyblock.game.effect.PotionType;

@RequiredArgsConstructor
public class BasicPotionItem extends PotionItem {

    private final PotionType type;
    private final IEffect effect;

    @Override
    public Item createItem(Object... args) {
        final int potionLevel = (int) args[0];
        final boolean extended = (boolean) args[1];
        final boolean enhanced = (boolean) args[2];

        return super.createItem(this.type, potionLevel, extended, enhanced, new Effect[]{
                new Effect(this.effect, this.effect.getDurationOf(potionLevel, extended), this.effect.getAmplifierOf(potionLevel, extended))
        });
    }

}
