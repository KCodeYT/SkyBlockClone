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
import cn.nukkit.block.BlockLeaves;
import cn.nukkit.block.BlockSapling;
import cn.nukkit.block.BlockWood;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemColorArmor;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.BlockColor;
import cn.nukkit.utils.DyeColor;
import ms.kevi.skyblock.entity.minion.EntityMinion;
import ms.kevi.skyblock.game.minion.IMinionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OakType implements IMinionType {

    @Override
    public String getDisplayName() {
        return "Oak";
    }

    @Override
    public List<Block> createArea(EntityMinion entityMinion) {
        final Vector3 center = new Vector3(entityMinion.getFloorX(), entityMinion.getFloorY() - 1, entityMinion.getFloorZ());
        final List<Block> area = new ArrayList<>();
        for(int x = -2; x <= 2; x += 2) {
            for(int z = -2; z <= 2; z += 2) {
                if(x == 0 && z == 0)
                    continue;
                area.add(entityMinion.getLevel().getBlock(center.getFloorX() + x, center.getFloorY(), center.getFloorZ() + z));
            }
        }
        return area;
    }

    @Override
    public boolean checkBreakability(Block block) {
        if(!(block.getId() == Block.DIRT || block.getId() == Block.GRASS) || block.getDamage() != 0)
            return false;
        return this.isWood(block.up(1)) && this.isWood(block.up(2)) && this.isWood(block.up(3)) && this.isWood(block.up(4));
    }

    private boolean isWood(Block block) {
        return block.getId() == Block.WOOD && block.getDamage() == BlockWood.OAK;
    }

    @Override
    public boolean checkPlaceability(Block block) {
        if(!(block.getId() == Block.DIRT || block.getId() == Block.GRASS) || block.getDamage() != 0)
            return false;
        final Block up1 = block.up();
        if(up1.getId() == Block.SAPLING && up1.getDamage() == BlockSapling.OAK)
            return true;
        return up1.getId() == Block.AIR && up1.up(1).getId() == Block.AIR && up1.up(2).getId() == Block.AIR && up1.up(3).getId() == Block.AIR;
    }

    @Override
    public void breakBlock(Block block) {
        final Level level = block.getLevel();
        level.setBlock(block.up(1), Block.get(Block.AIR));
        level.setBlock(block.up(2), Block.get(Block.AIR));
        level.setBlock(block.up(3), Block.get(Block.AIR));
        level.setBlock(block.up(4), Block.get(Block.AIR));
    }

    @Override
    public List<Block> getAnimationBlocks(Block block) {
        return Arrays.asList(block.up(1), block.up(2), block.up(3), block.up(4));
    }

    @Override
    public void placeBlock(Block block) {
        final Level level = block.getLevel();
        final Block up1 = block.up(1);
        if(up1.getId() == Block.SAPLING) {
            level.setBlock(block, Block.get(Block.DIRT));
            level.setBlock(up1, Block.get(Block.WOOD, BlockWood.OAK));
            level.setBlock(block.up(2), Block.get(Block.WOOD, BlockWood.OAK));
            level.setBlock(block.up(3), Block.get(Block.WOOD, BlockWood.OAK));
            final Block up4 = block.up(4);
            level.setBlock(up4, Block.get(Block.WOOD, BlockWood.OAK));

            for(int i = 1; i < 6; i++) {
                final Block face = up4.getSide(BlockFace.fromIndex(i));
                if(face.getId() == Block.AIR)
                    level.setBlock(face, Block.get(Block.LEAVES, BlockLeaves.OAK | 4));
            }
            return;
        }

        level.setBlock(up1, Block.get(Block.SAPLING, BlockSapling.OAK));
    }

    @Override
    public Item getItemInHand(Block block) {
        if(block == null || block.up().getId() == Block.WOOD && (block.up().getDamage() & 3) == BlockWood.OAK)
            return Item.get(Item.STONE_AXE, 0, 1);
        if(block.up().getId() == Block.SAPLING && (block.up().getDamage() & 3) == BlockSapling.OAK)
            return Item.get(Item.DYE, DyeColor.WHITE.getDyeData(), 1);
        return Item.get(Block.SAPLING, BlockSapling.OAK, 1);
    }

    @Override
    public Item getChestplateItem() {
        return ((ItemColorArmor) Item.get(Item.LEATHER_TUNIC)).setColor(BlockColor.YELLOW_BLOCK_COLOR);
    }

    @Override
    public Item getLeggingsItem() {
        return ((ItemColorArmor) Item.get(Item.LEATHER_PANTS)).setColor(BlockColor.YELLOW_BLOCK_COLOR);
    }

    @Override
    public Item getBootsItem() {
        return ((ItemColorArmor) Item.get(Item.LEATHER_BOOTS)).setColor(BlockColor.YELLOW_BLOCK_COLOR);
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
        return Item.get(Block.WOOD, BlockWood.OAK, 4);
    }

}
