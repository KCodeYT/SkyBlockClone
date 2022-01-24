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

package ms.kevi.skyblock.registry;

import ms.kevi.skyblock.game.modifier.IModifier;
import ms.kevi.skyblock.game.modifier.RenownedModifier;
import ms.kevi.skyblock.item.IGameItem;

import java.util.ArrayList;
import java.util.List;

public class ModifierRegistry extends Registry<IModifier> {

    public static final IModifier RENOWNED = new RenownedModifier();

    public IModifier[] findUsable(IGameItem gameItem) {
        final List<IModifier> modifiers = new ArrayList<>();
        for(IModifier modifier : this.values()) {
            if(modifier.isApplicable(gameItem) && !modifier.isFromStone())
                modifiers.add(modifier);
        }
        return modifiers.toArray(new IModifier[0]);
    }

}
