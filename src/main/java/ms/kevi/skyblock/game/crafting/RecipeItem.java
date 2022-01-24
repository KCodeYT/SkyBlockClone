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

package ms.kevi.skyblock.game.crafting;

import cn.nukkit.item.Item;
import lombok.Getter;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.registry.VanillaItemRegistry;

@Getter
public class RecipeItem {

    public static final RecipeItem EMPTY = new RecipeItem(Item::isNull, Item.get(Item.AIR, 0, 0));

    public static RecipeItem of(Item item) {
        if(item.isNull()) return EMPTY;
        return new RecipeItem(item);
    }

    public static RecipeItem of(IGameItem gameItem, int count) {
        if(gameItem.getId() == Item.AIR || gameItem.equals(VanillaItemRegistry.AIR))
            return EMPTY;
        return new RecipeItem(gameItem, count);
    }

    private final PreviewFactory previewFactory;
    private final Item item;
    private final Check check;
    private final int count;

    private RecipeItem(Check check, Item item) {
        this.previewFactory = () -> item;
        this.item = item;
        this.check = check;
        this.count = -1;
    }

    private RecipeItem(Item item) {
        this.previewFactory = () -> item;
        this.item = item;
        this.check = null;
        this.count = -1;
    }

    private RecipeItem(IGameItem gameItem, int count) {
        this.previewFactory = () -> {
            final Item item = gameItem.createItem();
            item.setCount(count);
            return item;
        };
        this.item = null;
        this.check = item -> gameItem.checkName(ItemHelper.getAttributeId(item));
        this.count = count;
    }

    public boolean isNull() {
        return this.check != null && this.check.equals(EMPTY.check) || this.item != null && this.item.isNull();
    }

    public boolean isInvalid(Item item) {
        return this.check == null || !this.check.check(item);
    }

    public Item getPreview() {
        return this.previewFactory.get();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RecipeItem && (this.item != null ?
                ((RecipeItem) obj).check != null && ((RecipeItem) obj).check.check(this.item) && this.item.getCount() >= ((RecipeItem) obj).count :
                this.check != null && this.check.check(((RecipeItem) obj).item) && ((RecipeItem) obj).item.getCount() >= this.count);
    }

    public interface Check {

        boolean check(Item item);

    }

    private interface PreviewFactory {

        Item get();

    }

}
