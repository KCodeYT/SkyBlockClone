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

package ms.kevi.skyblock.item.custom.accessory;

import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemType;
import ms.kevi.skyblock.item.registry.AbstractGameItem;

public abstract class AbstractAccessoryItem extends AbstractGameItem {

    @Override
    public ItemType getItemType() {
        return ItemType.ACCESSORY;
    }

    @Override
    public int getStackSize() {
        return 1;
    }

    @Override
    public boolean isModifiable() {
        return true;
    }

    public abstract IGameItem[] getFamily();

}
