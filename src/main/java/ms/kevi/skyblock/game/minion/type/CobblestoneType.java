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

package ms.kevi.skyblock.game.minion.type;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemColorArmor;
import cn.nukkit.utils.BlockColor;
import ms.kevi.skyblock.game.minion.IMinionType;

import java.util.Collections;
import java.util.List;

public class CobblestoneType implements IMinionType {

    @Override
    public String getDisplayName() {
        return "Cobblestone";
    }

    @Override
    public boolean checkBreakability(Block block) {
        return block.getId() == Block.COBBLE;
    }

    @Override
    public boolean checkPlaceability(Block block) {
        return block.getId() == Block.AIR;
    }

    @Override
    public void breakBlock(Block block) {
        block.getLevel().setBlock(block, Block.get(Block.AIR));
    }

    @Override
    public List<Block> getAnimationBlocks(Block block) {
        return Collections.singletonList(block);
    }

    @Override
    public void placeBlock(Block block) {
        block.getLevel().setBlock(block, Block.get(Block.COBBLE));
    }

    @Override
    public Item getItemInHand(Block block) {
        if(block == null || block.getId() == Block.COBBLE)
            return Item.get(Item.WOODEN_PICKAXE, 0, 1);
        return Item.get(Block.COBBLE, 0, 1);
    }

    @Override
    public Item getChestplateItem() {
        return ((ItemColorArmor) Item.get(Item.LEATHER_TUNIC)).setColor(BlockColor.GRAY_BLOCK_COLOR);
    }

    @Override
    public Item getLeggingsItem() {
        return ((ItemColorArmor) Item.get(Item.LEATHER_PANTS)).setColor(BlockColor.GRAY_BLOCK_COLOR);
    }

    @Override
    public Item getBootsItem() {
        return ((ItemColorArmor) Item.get(Item.LEATHER_BOOTS)).setColor(BlockColor.GRAY_BLOCK_COLOR);
    }

    @Override
    public int getMaxTierLevel() {
        return 11;
    }

    @Override
    public float getTimeBetweenActions(int tierLevel) {
        switch(tierLevel) {
            case 1:
            case 2:
                return 15;
            case 3:
            case 4:
                return 13;
            case 5:
            case 6:
                return 11;
            case 7:
            case 8:
                return 10;
            case 9:
            case 10:
                return 9;
            default:
                return 8;
        }
    }

    @Override
    public int getMaxStorage(int tierLevel) {
        switch(tierLevel) {
            case 1:
            case 2:
                return 128;
            case 3:
            case 4:
                return 256;
            case 5:
            case 6:
                return 384;
            case 7:
            case 8:
                return 576;
            case 9:
            case 10:
                return 768;
            default:
                return 960;
        }
    }

    @Override
    public Item getBlockFormat() {
        return null;
    }

    @Override
    public Item getItemFormat() {
        return Item.get(Item.COBBLE);
    }

}
