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

package ms.kevi.skyblock.item;

import ms.kevi.skyblock.item.registry.BuildableGameItem;

import java.util.LinkedHashMap;
import java.util.Map;

import static ms.kevi.skyblock.game.GameRarity.COMMON;

@SuppressWarnings("SpellCheckingInspection")
public class ItemList {

    private static final Map<String, IGameItem> ITEMS = new LinkedHashMap<>();

    static {
        register("BARRIER", BuildableGameItem.builder().
                displayName("Barrier").
                rarity(COMMON).
                id(-161).damage(0).
                build());
        register("AIR", BuildableGameItem.builder().
                displayName("Air").
                rarity(COMMON).
                id(0).damage(0).
                build());
        register("STONE", BuildableGameItem.builder().
                displayName("Stone").
                rarity(COMMON).
                id(1).damage(0).
                build());
        register("STONE:1", BuildableGameItem.builder().
                displayName("Granite").
                rarity(COMMON).
                id(1).damage(1).
                build());
        register("STONE:2", BuildableGameItem.builder().
                displayName("Polished Granite").
                rarity(COMMON).
                id(1).damage(2).
                build());
        register("STONE:3", BuildableGameItem.builder().
                displayName("Diorite").
                rarity(COMMON).
                id(1).damage(3).
                build());
        register("STONE:4", BuildableGameItem.builder().
                displayName("Polished Diorite").
                rarity(COMMON).
                id(1).damage(4).
                build());
        register("STONE:5", BuildableGameItem.builder().
                displayName("Andesite").
                rarity(COMMON).
                id(1).damage(5).
                build());
        register("STONE:6", BuildableGameItem.builder().
                displayName("Polished Andesite").
                rarity(COMMON).
                id(1).damage(6).
                build());
        register("GRASS", BuildableGameItem.builder().
                displayName("Grass").
                rarity(COMMON).
                id(2).damage(0).
                build());
        register("DIRT", BuildableGameItem.builder().
                displayName("Dirt").
                rarity(COMMON).
                id(3).damage(0).
                build());
        register("DIRT:1", BuildableGameItem.builder().
                displayName("Coarse Dirt").
                rarity(COMMON).
                id(3).damage(1).
                build());
        register("DIRT:2", BuildableGameItem.builder().
                displayName("Podzol").
                rarity(COMMON).
                id(3).damage(2).
                build());
        register("COBBLESTONE", BuildableGameItem.builder().
                displayName("Cobblestone").
                rarity(COMMON).
                id(4).damage(0).
                build());
        register("WOOD", BuildableGameItem.builder().
                displayName("Oak Wood Plank").
                rarity(COMMON).
                id(5).damage(0).
                build());
        register("WOOD:1", BuildableGameItem.builder().
                displayName("Spruce Wood Plank").
                rarity(COMMON).
                id(5).damage(1).
                build());
        register("WOOD:2", BuildableGameItem.builder().
                displayName("Birch Wood Plank").
                rarity(COMMON).
                id(5).damage(2).
                build());
        register("WOOD:3", BuildableGameItem.builder().
                displayName("Jungle Wood Plank").
                rarity(COMMON).
                id(5).damage(3).
                build());
        register("WOOD:4", BuildableGameItem.builder().
                displayName("Acacia Wood Plank").
                rarity(COMMON).
                id(5).damage(4).
                build());
        register("WOOD:5", BuildableGameItem.builder().
                displayName("Dark Oak Wood Plank").
                rarity(COMMON).
                id(5).damage(5).
                build());
        register("SAPLING", BuildableGameItem.builder().
                displayName("Oak Sapling").
                rarity(COMMON).
                id(6).damage(0).
                build());
        register("SAPLING:1", BuildableGameItem.builder().
                displayName("Spruce Sapling").
                rarity(COMMON).
                id(6).damage(1).
                build());
        register("SAPLING:2", BuildableGameItem.builder().
                displayName("Birch Sapling").
                rarity(COMMON).
                id(6).damage(2).
                build());
        register("SAPLING:3", BuildableGameItem.builder().
                displayName("Jungle Sapling").
                rarity(COMMON).
                id(6).damage(3).
                build());
        register("SAPLING:4", BuildableGameItem.builder().
                displayName("Acacia Sapling").
                rarity(COMMON).
                id(6).damage(4).
                build());
        register("SAPLING:5", BuildableGameItem.builder().
                displayName("Dark Oak Sapling").
                rarity(COMMON).
                id(6).damage(5).
                build());
        register("BEDROCK", BuildableGameItem.builder().
                displayName("Bedrock").
                rarity(COMMON).
                id(7).damage(0).
                build());
        register("FLOWING_WATER", BuildableGameItem.builder().
                displayName("Flowing Water Block").
                rarity(COMMON).
                id(8).damage(0).
                build());
        register("WATER", BuildableGameItem.builder().
                displayName("Still Water Block").
                rarity(COMMON).
                id(8).damage(0).
                build());
        register("FLOWING_LAVA", BuildableGameItem.builder().
                displayName("Flowing Lava Block").
                rarity(COMMON).
                id(10).damage(0).
                build());
        register("LAVA", BuildableGameItem.builder().
                displayName("Still Lava Block").
                rarity(COMMON).
                id(10).damage(0).
                build());
        register("SAND", BuildableGameItem.builder().
                displayName("Sand").
                rarity(COMMON).
                id(12).damage(0).
                build());
        register("SAND:1", BuildableGameItem.builder().
                displayName("Red Sand").
                rarity(COMMON).
                id(12).damage(1).
                build());
        register("GRAVEL", BuildableGameItem.builder().
                displayName("Gravel").
                rarity(COMMON).
                id(13).damage(0).
                build());
        register("GOLD_ORE", BuildableGameItem.builder().
                displayName("Gold Ore").
                rarity(COMMON).
                id(14).damage(0).
                build());
        register("IRON_ORE", BuildableGameItem.builder().
                displayName("Iron Ore").
                rarity(COMMON).
                id(15).damage(0).
                build());
        register("COAL_ORE", BuildableGameItem.builder().
                displayName("Coal Ore").
                rarity(COMMON).
                id(16).damage(0).
                build());
        register("LOG", BuildableGameItem.builder().
                displayName("Oak Wood").
                rarity(COMMON).
                id(17).damage(0).
                build());
        register("LOG:1", BuildableGameItem.builder().
                displayName("Spruce Wood").
                rarity(COMMON).
                id(17).damage(1).
                build());
        register("LOG:2", BuildableGameItem.builder().
                displayName("Birch Wood").
                rarity(COMMON).
                id(17).damage(2).
                build());
        register("LOG:3", BuildableGameItem.builder().
                displayName("Jungle Wood").
                rarity(COMMON).
                id(17).damage(3).
                build());
        register("LEAVES", BuildableGameItem.builder().
                displayName("Oak Leaves").
                rarity(COMMON).
                id(18).damage(0).
                build());
        register("LEAVES:1", BuildableGameItem.builder().
                displayName("Spruce Leaves").
                rarity(COMMON).
                id(18).damage(1).
                build());
        register("LEAVES:2", BuildableGameItem.builder().
                displayName("Birch Leaves").
                rarity(COMMON).
                id(18).damage(2).
                build());
        register("LEAVES:3", BuildableGameItem.builder().
                displayName("Jungle Leaves").
                rarity(COMMON).
                id(18).damage(3).
                build());
        register("SPONGE", BuildableGameItem.builder().
                displayName("Sponge").
                rarity(COMMON).
                id(19).damage(0).
                build());
        register("SPONGE:1", BuildableGameItem.builder().
                displayName("Wet Sponge").
                rarity(COMMON).
                id(19).damage(1).
                build());
        register("GLASS", BuildableGameItem.builder().
                displayName("Glass").
                rarity(COMMON).
                id(20).damage(0).
                build());
        register("LAPIS_ORE", BuildableGameItem.builder().
                displayName("Lapis Lazuli Ore").
                rarity(COMMON).
                id(21).damage(0).
                build());
        register("LAPIS_BLOCK", BuildableGameItem.builder().
                displayName("Lapis Lazuli Block").
                rarity(COMMON).
                id(22).damage(0).
                build());
        register("DISPENSER", BuildableGameItem.builder().
                displayName("Dispenser").
                rarity(COMMON).
                id(23).damage(0).
                build());
        register("SANDSTONE", BuildableGameItem.builder().
                displayName("Sandstone").
                rarity(COMMON).
                id(24).damage(0).
                build());
        register("SANDSTONE:1", BuildableGameItem.builder().
                displayName("Chiseled Sandstone").
                rarity(COMMON).
                id(24).damage(1).
                build());
        register("SANDSTONE:2", BuildableGameItem.builder().
                displayName("Smooth Sandstone").
                rarity(COMMON).
                id(24).damage(2).
                build());
        register("NOTEBLOCK", BuildableGameItem.builder().
                displayName("Note Block").
                rarity(COMMON).
                id(25).damage(0).
                build());
        register("GOLDEN_RAIL", BuildableGameItem.builder().
                displayName("Powered Rail").
                rarity(COMMON).
                id(27).damage(0).
                build());
        register("DETECTOR_RAIL", BuildableGameItem.builder().
                displayName("Detector Rail").
                rarity(COMMON).
                id(28).damage(0).
                build());
        register("STICKY_PISTON", BuildableGameItem.builder().
                displayName("Sticky Piston").
                rarity(COMMON).
                id(29).damage(0).
                build());
        register("WEB", BuildableGameItem.builder().
                displayName("Web").
                rarity(COMMON).
                id(30).damage(0).
                build());
        register("LONG_GRASS", BuildableGameItem.builder().
                displayName("Shrub").
                rarity(COMMON).
                id(31).damage(0).
                build());
        register("LONG_GRASS:1", BuildableGameItem.builder().
                displayName("Long Grass").
                rarity(COMMON).
                id(31).damage(1).
                build());
        register("LONG_GRASS:2", BuildableGameItem.builder().
                displayName("Fern").
                rarity(COMMON).
                id(31).damage(2).
                build());
        register("DEAD_BUSH", BuildableGameItem.builder().
                displayName("Dead Bush").
                rarity(COMMON).
                id(32).damage(0).
                build());
        register("PISTON", BuildableGameItem.builder().
                displayName("Piston").
                rarity(COMMON).
                id(33).damage(0).
                build());
        register("PISTON_HEAD", BuildableGameItem.builder().
                displayName("Piston Head").
                rarity(COMMON).
                id(34).damage(0).
                build());
        register("WOOL", BuildableGameItem.builder().
                displayName("White Wool").
                rarity(COMMON).
                id(35).damage(0).
                build());
        register("WOOL:1", BuildableGameItem.builder().
                displayName("Orange Wool").
                rarity(COMMON).
                id(35).damage(1).
                build());
        register("WOOL:2", BuildableGameItem.builder().
                displayName("Magenta Wool").
                rarity(COMMON).
                id(35).damage(2).
                build());
        register("WOOL:3", BuildableGameItem.builder().
                displayName("Light Blue Wool").
                rarity(COMMON).
                id(35).damage(3).
                build());
        register("WOOL:4", BuildableGameItem.builder().
                displayName("Yellow Wool").
                rarity(COMMON).
                id(35).damage(4).
                build());
        register("WOOL:5", BuildableGameItem.builder().
                displayName("Lime Wool").
                rarity(COMMON).
                id(35).damage(5).
                build());
        register("WOOL:6", BuildableGameItem.builder().
                displayName("Pink Wool").
                rarity(COMMON).
                id(35).damage(6).
                build());
        register("WOOL:7", BuildableGameItem.builder().
                displayName("Gray Wool").
                rarity(COMMON).
                id(35).damage(7).
                build());
        register("WOOL:8", BuildableGameItem.builder().
                displayName("Light Gray Wool").
                rarity(COMMON).
                id(35).damage(8).
                build());
        register("WOOL:9", BuildableGameItem.builder().
                displayName("Cyan Wool").
                rarity(COMMON).
                id(35).damage(9).
                build());
        register("WOOL:10", BuildableGameItem.builder().
                displayName("Purple Wool").
                rarity(COMMON).
                id(35).damage(10).
                build());
        register("WOOL:11", BuildableGameItem.builder().
                displayName("Blue Wool").
                rarity(COMMON).
                id(35).damage(11).
                build());
        register("WOOL:12", BuildableGameItem.builder().
                displayName("Brown Wool").
                rarity(COMMON).
                id(35).damage(12).
                build());
        register("WOOL:13", BuildableGameItem.builder().
                displayName("Green Wool").
                rarity(COMMON).
                id(35).damage(13).
                build());
        register("WOOL:14", BuildableGameItem.builder().
                displayName("Red Wool").
                rarity(COMMON).
                id(35).damage(14).
                build());
        register("WOOL:15", BuildableGameItem.builder().
                displayName("Black Wool").
                rarity(COMMON).
                id(35).damage(15).
                build());
        register("YELLOW_FLOWER", BuildableGameItem.builder().
                displayName("Dandelion").
                rarity(COMMON).
                id(37).damage(0).
                build());
        register("RED_ROSE", BuildableGameItem.builder().
                displayName("Poppy").
                rarity(COMMON).
                id(38).damage(0).
                build());
        register("RED_ROSE:1", BuildableGameItem.builder().
                displayName("Blue Orchid").
                rarity(COMMON).
                id(38).damage(1).
                build());
        register("RED_ROSE:2", BuildableGameItem.builder().
                displayName("Allium").
                rarity(COMMON).
                id(38).damage(2).
                build());
        register("RED_ROSE:3", BuildableGameItem.builder().
                displayName("Azure Bluet").
                rarity(COMMON).
                id(38).damage(3).
                build());
        register("RED_ROSE:4", BuildableGameItem.builder().
                displayName("Red Tulip").
                rarity(COMMON).
                id(38).damage(4).
                build());
        register("RED_ROSE:5", BuildableGameItem.builder().
                displayName("Orange Tulip").
                rarity(COMMON).
                id(38).damage(5).
                build());
        register("RED_ROSE:6", BuildableGameItem.builder().
                displayName("White Tulip").
                rarity(COMMON).
                id(38).damage(6).
                build());
        register("RED_ROSE:7", BuildableGameItem.builder().
                displayName("Pink Tulip").
                rarity(COMMON).
                id(38).damage(7).
                build());
        register("RED_ROSE:8", BuildableGameItem.builder().
                displayName("Oxeye Daisy").
                rarity(COMMON).
                id(38).damage(8).
                build());
        register("BROWN_MUSHROOM", BuildableGameItem.builder().
                displayName("Brown Mushroom").
                rarity(COMMON).
                id(39).damage(0).
                build());
        register("RED_MUSHROOM", BuildableGameItem.builder().
                displayName("Red Mushroom").
                rarity(COMMON).
                id(40).damage(0).
                build());
        register("GOLD_BLOCK", BuildableGameItem.builder().
                displayName("Gold Block").
                rarity(COMMON).
                id(41).damage(0).
                build());
        register("IRON_BLOCK", BuildableGameItem.builder().
                displayName("Iron Block").
                rarity(COMMON).
                id(42).damage(0).
                build());
        register("DOUBLE_STONE_SLAB", BuildableGameItem.builder().
                displayName("Double Stone Slab").
                rarity(COMMON).
                id(43).damage(0).
                build());
        register("DOUBLE_STONE_SLAB:1", BuildableGameItem.builder().
                displayName("Double Sandstone Slab").
                rarity(COMMON).
                id(43).damage(1).
                build());
        register("DOUBLE_STONE_SLAB:2", BuildableGameItem.builder().
                displayName("Double Wooden Slab").
                rarity(COMMON).
                id(43).damage(2).
                build());
        register("DOUBLE_STONE_SLAB:3", BuildableGameItem.builder().
                displayName("Double Cobblestone Slab").
                rarity(COMMON).
                id(43).damage(3).
                build());
        register("DOUBLE_STONE_SLAB:4", BuildableGameItem.builder().
                displayName("Double Brick Slab").
                rarity(COMMON).
                id(43).damage(4).
                build());
        register("DOUBLE_STONE_SLAB:5", BuildableGameItem.builder().
                displayName("Double Stone Brick Slab").
                rarity(COMMON).
                id(43).damage(5).
                build());
        register("DOUBLE_STONE_SLAB:6", BuildableGameItem.builder().
                displayName("Double Nether Brick Slab").
                rarity(COMMON).
                id(43).damage(6).
                build());
        register("DOUBLE_STONE_SLAB:7", BuildableGameItem.builder().
                displayName("Double Quartz Slab").
                rarity(COMMON).
                id(43).damage(7).
                build());
        register("STONE_SLAB", BuildableGameItem.builder().
                displayName("Stone Slab").
                rarity(COMMON).
                id(44).damage(0).
                build());
        register("STONE_SLAB:1", BuildableGameItem.builder().
                displayName("Sandstone Slab").
                rarity(COMMON).
                id(44).damage(1).
                build());
        register("STONE_SLAB:2", BuildableGameItem.builder().
                displayName("Wooden Slab").
                rarity(COMMON).
                id(44).damage(2).
                build());
        register("STONE_SLAB:3", BuildableGameItem.builder().
                displayName("Cobblestone Slab").
                rarity(COMMON).
                id(44).damage(3).
                build());
        register("STONE_SLAB:4", BuildableGameItem.builder().
                displayName("Brick Slab").
                rarity(COMMON).
                id(44).damage(4).
                build());
        register("STONE_SLAB:5", BuildableGameItem.builder().
                displayName("Stone Brick Slab").
                rarity(COMMON).
                id(44).damage(5).
                build());
        register("STONE_SLAB:6", BuildableGameItem.builder().
                displayName("Nether Brick Slab").
                rarity(COMMON).
                id(44).damage(6).
                build());
        register("STONE_SLAB:7", BuildableGameItem.builder().
                displayName("Quartz Slab").
                rarity(COMMON).
                id(44).damage(7).
                build());
        register("BRICK", BuildableGameItem.builder().
                displayName("Bricks").
                rarity(COMMON).
                id(45).damage(0).
                build());
        register("TNT", BuildableGameItem.builder().
                displayName("TNT").
                rarity(COMMON).
                id(46).damage(0).
                build());
        register("BOOKSHELF", BuildableGameItem.builder().
                displayName("Bookshelf").
                rarity(COMMON).
                id(47).damage(0).
                build());
        register("MOSSY_COBBLESTONE", BuildableGameItem.builder().
                displayName("Moss Stone").
                rarity(COMMON).
                id(48).damage(0).
                build());
        register("OBSIDIAN", BuildableGameItem.builder().
                displayName("Obsidian").
                rarity(COMMON).
                id(49).damage(0).
                build());
        register("TORCH", BuildableGameItem.builder().
                displayName("Torch").
                rarity(COMMON).
                id(50).damage(0).
                build());
        register("FIRE", BuildableGameItem.builder().
                displayName("Fire").
                rarity(COMMON).
                id(51).damage(0).
                build());
        register("MOB_SPAWNER", BuildableGameItem.builder().
                displayName("Monster Spawner").
                rarity(COMMON).
                id(52).damage(0).
                build());
        register("OAK_STAIRS", BuildableGameItem.builder().
                displayName("Oak Wood Stairs").
                rarity(COMMON).
                id(53).damage(0).
                build());
        register("CHEST", BuildableGameItem.builder().
                displayName("Chest").
                rarity(COMMON).
                id(54).damage(0).
                build());
        register("REDSTONE_WIRE", BuildableGameItem.builder().
                displayName("Redstone Wire").
                rarity(COMMON).
                id(55).damage(0).
                build());
        register("DIAMOND_ORE", BuildableGameItem.builder().
                displayName("Diamond Ore").
                rarity(COMMON).
                id(56).damage(0).
                build());
        register("DIAMOND_BLOCK", BuildableGameItem.builder().
                displayName("Diamond Block").
                rarity(COMMON).
                id(57).damage(0).
                build());
        register("CRAFTING_TABLE", BuildableGameItem.builder().
                displayName("Crafting Table").
                rarity(COMMON).
                id(58).damage(0).
                build());
        register("FARMLAND", BuildableGameItem.builder().
                displayName("Farmland").
                rarity(COMMON).
                id(60).damage(0).
                build());
        register("FURNACE", BuildableGameItem.builder().
                displayName("Furnace").
                rarity(COMMON).
                id(61).damage(0).
                build());
        register("LIT_FURNACE", BuildableGameItem.builder().
                displayName("Burning Furnace").
                rarity(COMMON).
                id(62).damage(0).
                build());
        register("STANDING_SIGN", BuildableGameItem.builder().
                displayName("Standing Sign Block").
                rarity(COMMON).
                id(63).damage(0).
                build());
        register("LADDER", BuildableGameItem.builder().
                displayName("Ladder").
                rarity(COMMON).
                id(65).damage(0).
                build());
        register("RAIL", BuildableGameItem.builder().
                displayName("Rail").
                rarity(COMMON).
                id(66).damage(0).
                build());
        register("STONE_STAIRS", BuildableGameItem.builder().
                displayName("Cobblestone Stairs").
                rarity(COMMON).
                id(67).damage(0).
                build());
        register("WALL_SIGN", BuildableGameItem.builder().
                displayName("Wall-mounted Sign Block").
                rarity(COMMON).
                id(68).damage(0).
                build());
        register("LEVER", BuildableGameItem.builder().
                displayName("Lever").
                rarity(COMMON).
                id(69).damage(0).
                build());
        register("STONE_PRESSURE_PLATE", BuildableGameItem.builder().
                displayName("Stone Pressure Plate").
                rarity(COMMON).
                id(70).damage(0).
                build());
        register("WOODEN_PRESSURE_PLATE", BuildableGameItem.builder().
                displayName("Wooden Pressure Plate").
                rarity(COMMON).
                id(72).damage(0).
                build());
        register("REDSTONE_ORE", BuildableGameItem.builder().
                displayName("Redstone Ore").
                rarity(COMMON).
                id(73).damage(0).
                build());
        register("LIT_REDSTONE_ORE", BuildableGameItem.builder().
                displayName("Glowing Redstone Ore").
                rarity(COMMON).
                id(74).damage(0).
                build());
        register("UNLIT_REDSTONE_TORCH", BuildableGameItem.builder().
                displayName("Redstone Torch").
                rarity(COMMON).
                id(75).damage(0).
                build());
        register("REDSTONE_TORCH", BuildableGameItem.builder().
                displayName("Redstone Torch").
                rarity(COMMON).
                id(76).damage(0).
                build());
        register("STONE_BUTTON", BuildableGameItem.builder().
                displayName("Stone Button").
                rarity(COMMON).
                id(77).damage(0).
                build());
        register("SNOW_LAYER", BuildableGameItem.builder().
                displayName("Snow").
                rarity(COMMON).
                id(78).damage(0).
                build());
        register("SNOW", BuildableGameItem.builder().
                displayName("Snow Block").
                rarity(COMMON).
                id(78).damage(0).
                build());
        register("ICE", BuildableGameItem.builder().
                displayName("Ice").
                rarity(COMMON).
                id(79).damage(0).
                build());
        register("CACTUS", BuildableGameItem.builder().
                displayName("Cactus").
                rarity(COMMON).
                id(81).damage(0).
                build());
        register("REEDS", BuildableGameItem.builder().
                displayName("Sugar Canes").
                rarity(COMMON).
                id(83).damage(0).
                build());
        register("REEDS", BuildableGameItem.builder().
                displayName("Sugar Canes").
                rarity(COMMON).
                id(83).damage(0).
                build());
        register("JUKEBOX", BuildableGameItem.builder().
                displayName("Jukebox").
                rarity(COMMON).
                id(84).damage(0).
                build());
        register("FENCE", BuildableGameItem.builder().
                displayName("Oak Fence").
                rarity(COMMON).
                id(85).damage(0).
                build());
        register("PUMPKIN", BuildableGameItem.builder().
                displayName("Pumpkin").
                rarity(COMMON).
                id(86).damage(0).
                build());
        register("NETHERRACK", BuildableGameItem.builder().
                displayName("Netherrack").
                rarity(COMMON).
                id(87).damage(0).
                build());
        register("SOUL_SAND", BuildableGameItem.builder().
                displayName("Soul Sand").
                rarity(COMMON).
                id(88).damage(0).
                build());
        register("GLOWSTONE", BuildableGameItem.builder().
                displayName("Glowstone").
                rarity(COMMON).
                id(89).damage(0).
                build());
        register("PORTAL", BuildableGameItem.builder().
                displayName("Nether Portal").
                rarity(COMMON).
                id(90).damage(0).
                build());
        register("LIT_PUMPKIN", BuildableGameItem.builder().
                displayName("Jack o'Lantern").
                rarity(COMMON).
                id(91).damage(0).
                build());
        register("UNPOWERED_REPEATER", BuildableGameItem.builder().
                displayName("Redstone Repeater Block").
                rarity(COMMON).
                id(93).damage(0).
                build());
        register("POWERED_REPEATER", BuildableGameItem.builder().
                displayName("Redstone Repeater Block").
                rarity(COMMON).
                id(94).damage(0).
                build());
        register("TRAPDOOR", BuildableGameItem.builder().
                displayName("Wooden Trapdoor").
                rarity(COMMON).
                id(96).damage(0).
                build());
        register("MONSTER_EGG", BuildableGameItem.builder().
                displayName("Stone Monster Egg").
                rarity(COMMON).
                id(97).damage(0).
                build());
        register("MONSTER_EGG:1", BuildableGameItem.builder().
                displayName("Cobblestone Monster Egg").
                rarity(COMMON).
                id(97).damage(1).
                build());
        register("MONSTER_EGG:2", BuildableGameItem.builder().
                displayName("Stone Brick Monster Egg").
                rarity(COMMON).
                id(97).damage(2).
                build());
        register("MONSTER_EGG:3", BuildableGameItem.builder().
                displayName("Mossy Stone Brick Monster Egg").
                rarity(COMMON).
                id(97).damage(3).
                build());
        register("MONSTER_EGG:4", BuildableGameItem.builder().
                displayName("Cracked Stone Brick Monster Egg").
                rarity(COMMON).
                id(97).damage(4).
                build());
        register("MONSTER_EGG:5", BuildableGameItem.builder().
                displayName("Chiseled Stone Brick Monster Egg").
                rarity(COMMON).
                id(97).damage(5).
                build());
        register("SMOOTH_BRICK", BuildableGameItem.builder().
                displayName("Stone Bricks").
                rarity(COMMON).
                id(98).damage(0).
                build());
        register("SMOOTH_BRICK:1", BuildableGameItem.builder().
                displayName("Mossy Stone Bricks").
                rarity(COMMON).
                id(98).damage(1).
                build());
        register("SMOOTH_BRICK:2", BuildableGameItem.builder().
                displayName("Cracked Stone Bricks").
                rarity(COMMON).
                id(98).damage(2).
                build());
        register("SMOOTH_BRICK:3", BuildableGameItem.builder().
                displayName("Chiseled Stone Bricks").
                rarity(COMMON).
                id(98).damage(3).
                build());
        register("BROWN_MUSHROOM_BLOCK", BuildableGameItem.builder().
                displayName("Brown Mushroom Block").
                rarity(COMMON).
                id(99).damage(0).
                build());
        register("RED_MUSHROOM_BLOCK", BuildableGameItem.builder().
                displayName("Red Mushroom Block").
                rarity(COMMON).
                id(100).damage(0).
                build());
        register("IRON_BARS", BuildableGameItem.builder().
                displayName("Iron Bars").
                rarity(COMMON).
                id(101).damage(0).
                build());
        register("GLASS_PANE", BuildableGameItem.builder().
                displayName("Glass Pane").
                rarity(COMMON).
                id(102).damage(0).
                build());
        register("MELON_BLOCK", BuildableGameItem.builder().
                displayName("Melon Block").
                rarity(COMMON).
                id(103).damage(0).
                build());
        register("PUMPKIN_STEM", BuildableGameItem.builder().
                displayName("Pumpkin Stem").
                rarity(COMMON).
                id(104).damage(0).
                build());
        register("MELON_STEM", BuildableGameItem.builder().
                displayName("Melon Stem").
                rarity(COMMON).
                id(105).damage(0).
                build());
        register("VINE", BuildableGameItem.builder().
                displayName("Vines").
                rarity(COMMON).
                id(106).damage(0).
                build());
        register("FENCE_GATE", BuildableGameItem.builder().
                displayName("Oak Fence Gate").
                rarity(COMMON).
                id(107).damage(0).
                build());
        register("BRICK_STAIRS", BuildableGameItem.builder().
                displayName("Brick Stairs").
                rarity(COMMON).
                id(108).damage(0).
                build());
        register("STONE_BRICK_STAIRS", BuildableGameItem.builder().
                displayName("Stone Brick Stairs").
                rarity(COMMON).
                id(109).damage(0).
                build());
        register("MYCEL", BuildableGameItem.builder().
                displayName("Mycelium").
                rarity(COMMON).
                id(110).damage(0).
                build());
        register("WATERLILY", BuildableGameItem.builder().
                displayName("Lily Pad").
                rarity(COMMON).
                id(111).damage(0).
                build());
        register("NETHER_BRICK_FENCE", BuildableGameItem.builder().
                displayName("Nether Brick Fence").
                rarity(COMMON).
                id(113).damage(0).
                build());
        register("NETHER_BRICK_STAIRS", BuildableGameItem.builder().
                displayName("Nether Brick Stairs").
                rarity(COMMON).
                id(114).damage(0).
                build());
        register("NETHER_WART_BLOCK", BuildableGameItem.builder().
                displayName("Nether Wart Block").
                rarity(COMMON).
                id(115).damage(0).
                build());
        register("ENCHANTING_TABLE", BuildableGameItem.builder().
                displayName("Enchantment Table").
                rarity(COMMON).
                id(116).damage(0).
                build());
        register("END_PORTAL", BuildableGameItem.builder().
                displayName("End Portal").
                rarity(COMMON).
                id(119).damage(0).
                build());
        register("END_PORTAL_FRAME", BuildableGameItem.builder().
                displayName("End Portal Frame").
                rarity(COMMON).
                id(120).damage(0).
                build());
        register("END_STONE", BuildableGameItem.builder().
                displayName("End Stone").
                rarity(COMMON).
                id(121).damage(0).
                build());
        register("DRAGON_EGG", BuildableGameItem.builder().
                displayName("Dragon Egg").
                rarity(COMMON).
                id(122).damage(0).
                build());
        register("REDSTONE_LAMP", BuildableGameItem.builder().
                displayName("Redstone Lamp").
                rarity(COMMON).
                id(123).damage(0).
                build());
        register("LIT_REDSTONE_LAMP", BuildableGameItem.builder().
                displayName("Redstone Lamp").
                rarity(COMMON).
                id(124).damage(0).
                build());
        register("DROPPER", BuildableGameItem.builder().
                displayName("Dropper").
                rarity(COMMON).
                id(125).damage(0).
                build());
        register("ACTIVATOR_RAIL", BuildableGameItem.builder().
                displayName("Activator Rail").
                rarity(COMMON).
                id(126).damage(0).
                build());
        register("COCOA", BuildableGameItem.builder().
                displayName("Cocoa").
                rarity(COMMON).
                id(127).damage(0).
                build());
        register("SANDSTONE_STAIRS", BuildableGameItem.builder().
                displayName("Sandstone Stairs").
                rarity(COMMON).
                id(128).damage(0).
                build());
        register("EMERALD_ORE", BuildableGameItem.builder().
                displayName("Emerald Ore").
                rarity(COMMON).
                id(129).damage(0).
                build());
        register("ENDER_CHEST", BuildableGameItem.builder().
                displayName("Ender Chest").
                rarity(COMMON).
                id(130).damage(0).
                build());
        register("TRIPWIRE_HOOK", BuildableGameItem.builder().
                displayName("Tripwire Hook").
                rarity(COMMON).
                id(131).damage(0).
                build());
        register("TRIPWIRE_HOOK", BuildableGameItem.builder().
                displayName("Tripwire").
                rarity(COMMON).
                id(131).damage(0).
                build());
        register("EMERALD_BLOCK", BuildableGameItem.builder().
                displayName("Emerald Block").
                rarity(COMMON).
                id(133).damage(0).
                build());
        register("SPRUCE_STAIRS", BuildableGameItem.builder().
                displayName("Spruce Wood Stairs").
                rarity(COMMON).
                id(134).damage(0).
                build());
        register("BIRCH_STAIRS", BuildableGameItem.builder().
                displayName("Birch Wood Stairs").
                rarity(COMMON).
                id(135).damage(0).
                build());
        register("JUNGLE_STAIRS", BuildableGameItem.builder().
                displayName("Jungle Wood Stairs").
                rarity(COMMON).
                id(136).damage(0).
                build());
        register("COMMAND_BLOCK", BuildableGameItem.builder().
                displayName("Command Block").
                rarity(COMMON).
                id(137).damage(0).
                build());
        register("BEACON", BuildableGameItem.builder().
                displayName("Beacon").
                rarity(COMMON).
                id(138).damage(0).
                build());
        register("COBBLESTONE_WALL", BuildableGameItem.builder().
                displayName("Cobblestone Wall").
                rarity(COMMON).
                id(139).damage(0).
                build());
        register("COBBLESTONE_WALL:1", BuildableGameItem.builder().
                displayName("Mossy Cobblestone Wall").
                rarity(COMMON).
                id(139).damage(1).
                build());
        register("WOODEN_BUTTON", BuildableGameItem.builder().
                displayName("Wooden Button").
                rarity(COMMON).
                id(143).damage(0).
                build());
        register("ANVIL", BuildableGameItem.builder().
                displayName("Anvil").
                rarity(COMMON).
                id(145).damage(0).
                build());
        register("TRAPPED_CHEST", BuildableGameItem.builder().
                displayName("Trapped Chest").
                rarity(COMMON).
                id(146).damage(0).
                build());
        register("LIGHT_WEIGHTED_PRESSURE_PLATE", BuildableGameItem.builder().
                displayName("Weighted Pressure Plate").
                rarity(COMMON).
                id(147).damage(0).
                build());
        register("HEAVY_WEIGHTED_PRESSURE_PLATE", BuildableGameItem.builder().
                displayName("Weighted Pressure Plate").
                rarity(COMMON).
                id(148).damage(0).
                build());
        register("UNPOWERED_COMPARATOR", BuildableGameItem.builder().
                displayName("Redstone Comparator").
                rarity(COMMON).
                id(149).damage(0).
                build());
        register("POWERED_COMPARATOR", BuildableGameItem.builder().
                displayName("Redstone Comparator").
                rarity(COMMON).
                id(150).damage(0).
                build());
        register("DAYLIGHT_DETECTOR", BuildableGameItem.builder().
                displayName("Daylight Sensor").
                rarity(COMMON).
                id(151).damage(0).
                build());
        register("REDSTONE_BLOCK", BuildableGameItem.builder().
                displayName("Redstone Block").
                rarity(COMMON).
                id(152).damage(0).
                build());
        register("QUARTZ_ORE", BuildableGameItem.builder().
                displayName("Nether Quartz Ore").
                rarity(COMMON).
                id(153).damage(0).
                build());
        register("QUARTZ_BLOCK", BuildableGameItem.builder().
                displayName("Quartz Block").
                rarity(COMMON).
                id(155).damage(0).
                build());
        register("QUARTZ_BLOCK:1", BuildableGameItem.builder().
                displayName("Chiseled Quartz Block").
                rarity(COMMON).
                id(155).damage(1).
                build());
        register("QUARTZ_BLOCK:2", BuildableGameItem.builder().
                displayName("Pillar Quartz Block").
                rarity(COMMON).
                id(155).damage(2).
                build());
        register("QUARTZ_STAIRS", BuildableGameItem.builder().
                displayName("Quartz Stairs").
                rarity(COMMON).
                id(156).damage(0).
                build());
        register("DOUBLE_WOODEN_SLAB", BuildableGameItem.builder().
                displayName("Double Oak Wood Slab").
                rarity(COMMON).
                id(157).damage(0).
                build());
        register("DOUBLE_WOODEN_SLAB:1", BuildableGameItem.builder().
                displayName("Double Spruce Wood Slab").
                rarity(COMMON).
                id(157).damage(1).
                build());
        register("DOUBLE_WOODEN_SLAB:2", BuildableGameItem.builder().
                displayName("Double Birch Wood Slab").
                rarity(COMMON).
                id(157).damage(2).
                build());
        register("DOUBLE_WOODEN_SLAB:3", BuildableGameItem.builder().
                displayName("Double Jungle Wood Slab").
                rarity(COMMON).
                id(157).damage(3).
                build());
        register("DOUBLE_WOODEN_SLAB:4", BuildableGameItem.builder().
                displayName("Double Acacia Wood Slab").
                rarity(COMMON).
                id(157).damage(4).
                build());
        register("DOUBLE_WOODEN_SLAB:5", BuildableGameItem.builder().
                displayName("Double Dark Oak Wood Slab").
                rarity(COMMON).
                id(157).damage(5).
                build());
        register("WOODEN_SLAB", BuildableGameItem.builder().
                displayName("Oak Wood Slab").
                rarity(COMMON).
                id(158).damage(0).
                build());
        register("WOODEN_SLAB:1", BuildableGameItem.builder().
                displayName("Spruce Wood Slab").
                rarity(COMMON).
                id(158).damage(1).
                build());
        register("WOODEN_SLAB:2", BuildableGameItem.builder().
                displayName("Birch Wood Slab").
                rarity(COMMON).
                id(158).damage(2).
                build());
        register("WOODEN_SLAB:3", BuildableGameItem.builder().
                displayName("Jungle Wood Slab").
                rarity(COMMON).
                id(158).damage(3).
                build());
        register("WOODEN_SLAB:4", BuildableGameItem.builder().
                displayName("Acacia Wood Slab").
                rarity(COMMON).
                id(158).damage(4).
                build());
        register("WOODEN_SLAB:5", BuildableGameItem.builder().
                displayName("Dark Oak Wood Slab").
                rarity(COMMON).
                id(158).damage(5).
                build());
        register("STAINED_CLAY", BuildableGameItem.builder().
                displayName("White Hardened Clay").
                rarity(COMMON).
                id(159).damage(0).
                build());
        register("STAINED_CLAY:1", BuildableGameItem.builder().
                displayName("Orange Hardened Clay").
                rarity(COMMON).
                id(159).damage(1).
                build());
        register("STAINED_CLAY:2", BuildableGameItem.builder().
                displayName("Magenta Hardened Clay").
                rarity(COMMON).
                id(159).damage(2).
                build());
        register("STAINED_CLAY:3", BuildableGameItem.builder().
                displayName("Light Blue Hardened Clay").
                rarity(COMMON).
                id(159).damage(3).
                build());
        register("STAINED_CLAY:4", BuildableGameItem.builder().
                displayName("Yellow Hardened Clay").
                rarity(COMMON).
                id(159).damage(4).
                build());
        register("STAINED_CLAY:5", BuildableGameItem.builder().
                displayName("Lime Hardened Clay").
                rarity(COMMON).
                id(159).damage(5).
                build());
        register("STAINED_CLAY:6", BuildableGameItem.builder().
                displayName("Pink Hardened Clay").
                rarity(COMMON).
                id(159).damage(6).
                build());
        register("STAINED_CLAY:7", BuildableGameItem.builder().
                displayName("Gray Hardened Clay").
                rarity(COMMON).
                id(159).damage(7).
                build());
        register("STAINED_CLAY:8", BuildableGameItem.builder().
                displayName("Light Gray Hardened Clay").
                rarity(COMMON).
                id(159).damage(8).
                build());
        register("STAINED_CLAY:9", BuildableGameItem.builder().
                displayName("Cyan Hardened Clay").
                rarity(COMMON).
                id(159).damage(9).
                build());
        register("STAINED_CLAY:10", BuildableGameItem.builder().
                displayName("Purple Hardened Clay").
                rarity(COMMON).
                id(159).damage(10).
                build());
        register("STAINED_CLAY:11", BuildableGameItem.builder().
                displayName("Blue Hardened Clay").
                rarity(COMMON).
                id(159).damage(11).
                build());
        register("STAINED_CLAY:12", BuildableGameItem.builder().
                displayName("Brown Hardened Clay").
                rarity(COMMON).
                id(159).damage(12).
                build());
        register("STAINED_CLAY:13", BuildableGameItem.builder().
                displayName("Green Hardened Clay").
                rarity(COMMON).
                id(159).damage(13).
                build());
        register("STAINED_CLAY:14", BuildableGameItem.builder().
                displayName("Red Hardened Clay").
                rarity(COMMON).
                id(159).damage(14).
                build());
        register("STAINED_CLAY:15", BuildableGameItem.builder().
                displayName("Black Hardened Clay").
                rarity(COMMON).
                id(159).damage(15).
                build());
        register("STAINED_GLASS_PANE", BuildableGameItem.builder().
                displayName("White Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(0).
                build());
        register("STAINED_GLASS_PANE:1", BuildableGameItem.builder().
                displayName("Orange Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(1).
                build());
        register("STAINED_GLASS_PANE:2", BuildableGameItem.builder().
                displayName("Magenta Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(2).
                build());
        register("STAINED_GLASS_PANE:3", BuildableGameItem.builder().
                displayName("Light Blue Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(3).
                build());
        register("STAINED_GLASS_PANE:4", BuildableGameItem.builder().
                displayName("Yellow Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(4).
                build());
        register("STAINED_GLASS_PANE:5", BuildableGameItem.builder().
                displayName("Lime Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(5).
                build());
        register("STAINED_GLASS_PANE:6", BuildableGameItem.builder().
                displayName("Pink Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(6).
                build());
        register("STAINED_GLASS_PANE:7", BuildableGameItem.builder().
                displayName("Gray Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(7).
                build());
        register("STAINED_GLASS_PANE:8", BuildableGameItem.builder().
                displayName("Light Gray Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(8).
                build());
        register("STAINED_GLASS_PANE:9", BuildableGameItem.builder().
                displayName("Cyan Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(9).
                build());
        register("STAINED_GLASS_PANE:10", BuildableGameItem.builder().
                displayName("Purple Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(10).
                build());
        register("STAINED_GLASS_PANE:11", BuildableGameItem.builder().
                displayName("Blue Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(11).
                build());
        register("STAINED_GLASS_PANE:12", BuildableGameItem.builder().
                displayName("Brown Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(12).
                build());
        register("STAINED_GLASS_PANE:13", BuildableGameItem.builder().
                displayName("Green Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(13).
                build());
        register("STAINED_GLASS_PANE:14", BuildableGameItem.builder().
                displayName("Red Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(14).
                build());
        register("STAINED_GLASS_PANE:15", BuildableGameItem.builder().
                displayName("Black Stained Glass Pane").
                rarity(COMMON).
                id(160).damage(15).
                build());
        register("LEAVES2", BuildableGameItem.builder().
                displayName("Acacia Leaves").
                rarity(COMMON).
                id(161).damage(0).
                build());
        register("LEAVES2:1", BuildableGameItem.builder().
                displayName("Dark Oak Leaves").
                rarity(COMMON).
                id(161).damage(1).
                build());
        register("LOG2", BuildableGameItem.builder().
                displayName("Acacia Wood").
                rarity(COMMON).
                id(162).damage(0).
                build());
        register("LOG2:1", BuildableGameItem.builder().
                displayName("Dark Oak Wood").
                rarity(COMMON).
                id(162).damage(1).
                build());
        register("ACACIA_STAIRS", BuildableGameItem.builder().
                displayName("Acacia Wood Stairs").
                rarity(COMMON).
                id(163).damage(0).
                build());
        register("DARK_OAK_STAIRS", BuildableGameItem.builder().
                displayName("Dark Oak Wood Stairs").
                rarity(COMMON).
                id(164).damage(0).
                build());
        register("SLIME", BuildableGameItem.builder().
                displayName("Slime Block").
                rarity(COMMON).
                id(165).damage(0).
                build());
        register("IRON_TRAPDOOR", BuildableGameItem.builder().
                displayName("Iron Trapdoor").
                rarity(COMMON).
                id(167).damage(0).
                build());
        register("PRISMARINE", BuildableGameItem.builder().
                displayName("Prismarine").
                rarity(COMMON).
                id(168).damage(0).
                build());
        register("PRISMARINE:1", BuildableGameItem.builder().
                displayName("Prismarine Bricks").
                rarity(COMMON).
                id(168).damage(1).
                build());
        register("PRISMARINE:2", BuildableGameItem.builder().
                displayName("Dark Prismarine").
                rarity(COMMON).
                id(168).damage(2).
                build());
        register("SEA_LANTERN", BuildableGameItem.builder().
                displayName("Sea Lantern").
                rarity(COMMON).
                id(169).damage(0).
                build());
        register("HAY_BLOCK", BuildableGameItem.builder().
                displayName("Hay Bale").
                rarity(COMMON).
                id(170).damage(0).
                build());
        register("CARPET", BuildableGameItem.builder().
                displayName("White Carpet").
                rarity(COMMON).
                id(171).damage(0).
                build());
        register("CARPET:1", BuildableGameItem.builder().
                displayName("Orange Carpet").
                rarity(COMMON).
                id(171).damage(1).
                build());
        register("CARPET:2", BuildableGameItem.builder().
                displayName("Magenta Carpet").
                rarity(COMMON).
                id(171).damage(2).
                build());
        register("CARPET:3", BuildableGameItem.builder().
                displayName("Light Blue Carpet").
                rarity(COMMON).
                id(171).damage(3).
                build());
        register("CARPET:4", BuildableGameItem.builder().
                displayName("Yellow Carpet").
                rarity(COMMON).
                id(171).damage(4).
                build());
        register("CARPET:5", BuildableGameItem.builder().
                displayName("Lime Carpet").
                rarity(COMMON).
                id(171).damage(5).
                build());
        register("CARPET:6", BuildableGameItem.builder().
                displayName("Pink Carpet").
                rarity(COMMON).
                id(171).damage(6).
                build());
        register("CARPET:7", BuildableGameItem.builder().
                displayName("Gray Carpet").
                rarity(COMMON).
                id(171).damage(7).
                build());
        register("CARPET:8", BuildableGameItem.builder().
                displayName("Light Gray Carpet").
                rarity(COMMON).
                id(171).damage(8).
                build());
        register("CARPET:9", BuildableGameItem.builder().
                displayName("Cyan Carpet").
                rarity(COMMON).
                id(171).damage(9).
                build());
        register("CARPET:10", BuildableGameItem.builder().
                displayName("Purple Carpet").
                rarity(COMMON).
                id(171).damage(10).
                build());
        register("CARPET:11", BuildableGameItem.builder().
                displayName("Blue Carpet").
                rarity(COMMON).
                id(171).damage(11).
                build());
        register("CARPET:12", BuildableGameItem.builder().
                displayName("Brown Carpet").
                rarity(COMMON).
                id(171).damage(12).
                build());
        register("CARPET:13", BuildableGameItem.builder().
                displayName("Green Carpet").
                rarity(COMMON).
                id(171).damage(13).
                build());
        register("CARPET:14", BuildableGameItem.builder().
                displayName("Red Carpet").
                rarity(COMMON).
                id(171).damage(14).
                build());
        register("CARPET:15", BuildableGameItem.builder().
                displayName("Black Carpet").
                rarity(COMMON).
                id(171).damage(15).
                build());
        register("HARDENED_CLAY", BuildableGameItem.builder().
                displayName("Hardened Clay").
                rarity(COMMON).
                id(172).damage(0).
                build());
        register("COAL_BLOCK", BuildableGameItem.builder().
                displayName("Block of Coal").
                rarity(COMMON).
                id(173).damage(0).
                build());
        register("PACKED_ICE", BuildableGameItem.builder().
                displayName("Packed Ice").
                rarity(COMMON).
                id(174).damage(0).
                build());
        register("DOUBLE_PLANT", BuildableGameItem.builder().
                displayName("Sunflower").
                rarity(COMMON).
                id(175).damage(0).
                build());
        register("DOUBLE_PLANT:1", BuildableGameItem.builder().
                displayName("Lilac").
                rarity(COMMON).
                id(175).damage(1).
                build());
        register("DOUBLE_PLANT:2", BuildableGameItem.builder().
                displayName("Double Tallgrass").
                rarity(COMMON).
                id(175).damage(2).
                build());
        register("DOUBLE_PLANT:3", BuildableGameItem.builder().
                displayName("Large Fern").
                rarity(COMMON).
                id(175).damage(3).
                build());
        register("DOUBLE_PLANT:4", BuildableGameItem.builder().
                displayName("Rose Bush").
                rarity(COMMON).
                id(175).damage(4).
                build());
        register("DOUBLE_PLANT:5", BuildableGameItem.builder().
                displayName("Peony").
                rarity(COMMON).
                id(175).damage(5).
                build());
        register("STANDING_BANNER", BuildableGameItem.builder().
                displayName("Free-standing Banner").
                rarity(COMMON).
                id(176).damage(0).
                build());
        register("WALL_BANNER", BuildableGameItem.builder().
                displayName("Wall-mounted Banner").
                rarity(COMMON).
                id(177).damage(0).
                build());
        register("DAYLIGHT_DETECTOR_INVERTED", BuildableGameItem.builder().
                displayName("Inverted Daylight Sensor").
                rarity(COMMON).
                id(178).damage(0).
                build());
        register("RED_SANDSTONE", BuildableGameItem.builder().
                displayName("Red Sandstone").
                rarity(COMMON).
                id(179).damage(0).
                build());
        register("RED_SANDSTONE:1", BuildableGameItem.builder().
                displayName("Chiseled Red Sandstone").
                rarity(COMMON).
                id(179).damage(1).
                build());
        register("RED_SANDSTONE:2", BuildableGameItem.builder().
                displayName("Smooth Red Sandstone").
                rarity(COMMON).
                id(179).damage(2).
                build());
        register("RED_SANDSTONE_STAIRS", BuildableGameItem.builder().
                displayName("Red Sandstone Stairs").
                rarity(COMMON).
                id(180).damage(0).
                build());
        register("DOUBLE_STONE_SLAB2", BuildableGameItem.builder().
                displayName("Double Red Sandstone Slab").
                rarity(COMMON).
                id(181).damage(0).
                build());
        register("STONE_SLAB2", BuildableGameItem.builder().
                displayName("Red Sandstone Slab").
                rarity(COMMON).
                id(182).damage(0).
                build());
        register("SPRUCE_FENCE_GATE", BuildableGameItem.builder().
                displayName("Spruce Fence Gate").
                rarity(COMMON).
                id(183).damage(0).
                build());
        register("BIRCH_FENCE_GATE", BuildableGameItem.builder().
                displayName("Birch Fence Gate").
                rarity(COMMON).
                id(184).damage(0).
                build());
        register("JUNGLE_FENCE_GATE", BuildableGameItem.builder().
                displayName("Jungle Fence Gate").
                rarity(COMMON).
                id(185).damage(0).
                build());
        register("DARK_OAK_FENCE_GATE", BuildableGameItem.builder().
                displayName("Dark Oak Fence Gate").
                rarity(COMMON).
                id(186).damage(0).
                build());
        register("ACACIA_FENCE_GATE", BuildableGameItem.builder().
                displayName("Acacia Fence Gate").
                rarity(COMMON).
                id(187).damage(0).
                build());
        register("SPRUCE_FENCE", BuildableGameItem.builder().
                displayName("Spruce Fence").
                rarity(COMMON).
                id(188).damage(0).
                build());
        register("BIRCH_FENCE", BuildableGameItem.builder().
                displayName("Birch Fence").
                rarity(COMMON).
                id(189).damage(0).
                build());
        register("JUNGLE_FENCE", BuildableGameItem.builder().
                displayName("Jungle Fence").
                rarity(COMMON).
                id(190).damage(0).
                build());
        register("DARK_OAK_FENCE", BuildableGameItem.builder().
                displayName("Dark Oak Fence").
                rarity(COMMON).
                id(191).damage(0).
                build());
        register("ACACIA_FENCE", BuildableGameItem.builder().
                displayName("Acacia Fence").
                rarity(COMMON).
                id(192).damage(0).
                build());
        register("GRASS_PATH", BuildableGameItem.builder().
                displayName("Grass Path").
                rarity(COMMON).
                id(198).damage(0).
                build());
        register("CHORUS_FLOWER", BuildableGameItem.builder().
                displayName("Chorus Flower").
                rarity(COMMON).
                id(200).damage(0).
                build());
        register("PURPUR_BLOCK", BuildableGameItem.builder().
                displayName("Purpur Block").
                rarity(COMMON).
                id(201).damage(0).
                build());
        register("PURPUR_PILLAR", BuildableGameItem.builder().
                displayName("Purpur Pillar").
                rarity(COMMON).
                id(202).damage(0).
                build());
        register("PURPUR_STAIRS", BuildableGameItem.builder().
                displayName("Purpur Stairs").
                rarity(COMMON).
                id(203).damage(0).
                build());
        register("PURPUR_DOUBLE_SLAB", BuildableGameItem.builder().
                displayName("Purpur Double Slab").
                rarity(COMMON).
                id(204).damage(0).
                build());
        register("PURPUR_SLAB", BuildableGameItem.builder().
                displayName("Purpur Slab").
                rarity(COMMON).
                id(205).damage(0).
                build());
        register("END_BRICKS", BuildableGameItem.builder().
                displayName("End Stone Bricks").
                rarity(COMMON).
                id(206).damage(0).
                build());
        register("BEETROOTS", BuildableGameItem.builder().
                displayName("Beetroot Block").
                rarity(COMMON).
                id(207).damage(0).
                build());
        register("END_ROD", BuildableGameItem.builder().
                displayName("End Rod").
                rarity(COMMON).
                id(208).damage(0).
                build());
        register("END_GATEWAY", BuildableGameItem.builder().
                displayName("End Gateway").
                rarity(COMMON).
                id(209).damage(0).
                build());
        register("REPEATING_COMMAND_BLOCK", BuildableGameItem.builder().
                displayName("Repeating Command Block").
                rarity(COMMON).
                id(210).damage(0).
                build());
        register("CHAIN_COMMAND_BLOCK", BuildableGameItem.builder().
                displayName("Chain Command Block").
                rarity(COMMON).
                id(211).damage(0).
                build());
        register("FROSTED_ICE", BuildableGameItem.builder().
                displayName("Frosted Ice").
                rarity(COMMON).
                id(212).damage(0).
                build());
        register("MAGMA", BuildableGameItem.builder().
                displayName("Magma Block").
                rarity(COMMON).
                id(213).damage(0).
                build());
        register("RED_NETHER_BRICK", BuildableGameItem.builder().
                displayName("Red Nether Brick").
                rarity(COMMON).
                id(215).damage(0).
                build());
        register("BONE_BLOCK", BuildableGameItem.builder().
                displayName("Bone Block").
                rarity(COMMON).
                id(216).damage(0).
                build());
        register("STRUCTURE_VOID", BuildableGameItem.builder().
                displayName("Structure Void").
                rarity(COMMON).
                id(217).damage(0).
                build());
        register("WHITE_SHULKER_BOX", BuildableGameItem.builder().
                displayName("White Shulker Box").
                rarity(COMMON).
                id(219).damage(0).
                build());
        register("PURPLE_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Purple Glazed Terracotta").
                rarity(COMMON).
                id(219).damage(0).
                build());
        register("ORANGE_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Orange Shulker Box").
                rarity(COMMON).
                id(220).damage(0).
                build());
        register("WHITE_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("White Glazed Terracotta").
                rarity(COMMON).
                id(220).damage(0).
                build());
        register("MAGENTA_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Magenta Shulker Box").
                rarity(COMMON).
                id(221).damage(0).
                build());
        register("ORANGE_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Orange Glazed Terracotta").
                rarity(COMMON).
                id(221).damage(0).
                build());
        register("LIGHT_BLUE_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Light Blue Shulker Box").
                rarity(COMMON).
                id(222).damage(0).
                build());
        register("MAGENTA_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Magenta Glazed Terracotta").
                rarity(COMMON).
                id(222).damage(0).
                build());
        register("YELLOW_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Yellow Shulker Box").
                rarity(COMMON).
                id(223).damage(0).
                build());
        register("LIGHT_BLUE_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Light Blue Glazed Terracotta").
                rarity(COMMON).
                id(223).damage(0).
                build());
        register("LIME_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Lime Shulker Box").
                rarity(COMMON).
                id(224).damage(0).
                build());
        register("YELLOW_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Yellow Glazed Terracotta").
                rarity(COMMON).
                id(224).damage(0).
                build());
        register("PINK_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Pink Shulker Box").
                rarity(COMMON).
                id(225).damage(0).
                build());
        register("LIME_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Lime Glazed Terracotta").
                rarity(COMMON).
                id(225).damage(0).
                build());
        register("GRAY_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Gray Shulker Box").
                rarity(COMMON).
                id(226).damage(0).
                build());
        register("PINK_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Pink Glazed Terracotta").
                rarity(COMMON).
                id(226).damage(0).
                build());
        register("SILVER_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Light Gray Shulker Box").
                rarity(COMMON).
                id(227).damage(0).
                build());
        register("GRAY_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Gray Glazed Terracotta").
                rarity(COMMON).
                id(227).damage(0).
                build());
        register("CYAN_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Cyan Shulker Box").
                rarity(COMMON).
                id(228).damage(0).
                build());
        register("PURPLE_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Purple Shulker Box").
                rarity(COMMON).
                id(229).damage(0).
                build());
        register("CYAN_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Cyan Glazed Terracotta").
                rarity(COMMON).
                id(229).damage(0).
                build());
        register("BLUE_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Blue Shulker Box").
                rarity(COMMON).
                id(230).damage(0).
                build());
        register("BROWN_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Brown Shulker Box").
                rarity(COMMON).
                id(231).damage(0).
                build());
        register("BLUE_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Blue Glazed Terracotta").
                rarity(COMMON).
                id(231).damage(0).
                build());
        register("GREEN_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Green Shulker Box").
                rarity(COMMON).
                id(232).damage(0).
                build());
        register("BROWN_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Brown Glazed Terracotta").
                rarity(COMMON).
                id(232).damage(0).
                build());
        register("RED_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Red Shulker Box").
                rarity(COMMON).
                id(233).damage(0).
                build());
        register("GREEN_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Green Glazed Terracotta").
                rarity(COMMON).
                id(233).damage(0).
                build());
        register("BLACK_SHULKER_BOX", BuildableGameItem.builder().
                displayName("Black Shulker Box").
                rarity(COMMON).
                id(234).damage(0).
                build());
        register("RED_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Red Glazed Terracotta").
                rarity(COMMON).
                id(234).damage(0).
                build());
        register("BLACK_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Black Glazed Terracotta").
                rarity(COMMON).
                id(235).damage(0).
                build());
        register("CONCRETE", BuildableGameItem.builder().
                displayName("White Concrete").
                rarity(COMMON).
                id(236).damage(0).
                build());
        register("CONCRETE:1", BuildableGameItem.builder().
                displayName("Orange Concrete").
                rarity(COMMON).
                id(236).damage(1).
                build());
        register("CONCRETE:2", BuildableGameItem.builder().
                displayName("Magenta Concrete").
                rarity(COMMON).
                id(236).damage(2).
                build());
        register("CONCRETE:3", BuildableGameItem.builder().
                displayName("Light Blue Concrete").
                rarity(COMMON).
                id(236).damage(3).
                build());
        register("CONCRETE:4", BuildableGameItem.builder().
                displayName("Yellow Concrete").
                rarity(COMMON).
                id(236).damage(4).
                build());
        register("CONCRETE:5", BuildableGameItem.builder().
                displayName("Lime Concrete").
                rarity(COMMON).
                id(236).damage(5).
                build());
        register("CONCRETE:6", BuildableGameItem.builder().
                displayName("Pink Concrete").
                rarity(COMMON).
                id(236).damage(6).
                build());
        register("CONCRETE:7", BuildableGameItem.builder().
                displayName("Gray Concrete").
                rarity(COMMON).
                id(236).damage(7).
                build());
        register("CONCRETE:8", BuildableGameItem.builder().
                displayName("Light Gray Concrete").
                rarity(COMMON).
                id(236).damage(8).
                build());
        register("CONCRETE:9", BuildableGameItem.builder().
                displayName("Cyan Concrete").
                rarity(COMMON).
                id(236).damage(9).
                build());
        register("CONCRETE:10", BuildableGameItem.builder().
                displayName("Purple Concrete").
                rarity(COMMON).
                id(236).damage(10).
                build());
        register("CONCRETE:11", BuildableGameItem.builder().
                displayName("Blue Concrete").
                rarity(COMMON).
                id(236).damage(11).
                build());
        register("CONCRETE:12", BuildableGameItem.builder().
                displayName("Brown Concrete").
                rarity(COMMON).
                id(236).damage(12).
                build());
        register("CONCRETE:13", BuildableGameItem.builder().
                displayName("Green Concrete").
                rarity(COMMON).
                id(236).damage(13).
                build());
        register("CONCRETE:14", BuildableGameItem.builder().
                displayName("Red Concrete").
                rarity(COMMON).
                id(236).damage(14).
                build());
        register("CONCRETE:15", BuildableGameItem.builder().
                displayName("Black Concrete").
                rarity(COMMON).
                id(236).damage(15).
                build());
        register("CONCRETE_POWDER", BuildableGameItem.builder().
                displayName("White Concrete Powder").
                rarity(COMMON).
                id(237).damage(0).
                build());
        register("CONCRETE_POWDER:1", BuildableGameItem.builder().
                displayName("Orange Concrete Powder").
                rarity(COMMON).
                id(237).damage(1).
                build());
        register("CONCRETE_POWDER:2", BuildableGameItem.builder().
                displayName("Magenta Concrete Powder").
                rarity(COMMON).
                id(237).damage(2).
                build());
        register("CONCRETE_POWDER:3", BuildableGameItem.builder().
                displayName("Light Blue Concrete Powder").
                rarity(COMMON).
                id(237).damage(3).
                build());
        register("CONCRETE_POWDER:4", BuildableGameItem.builder().
                displayName("Yellow Concrete Powder").
                rarity(COMMON).
                id(237).damage(4).
                build());
        register("CONCRETE_POWDER:5", BuildableGameItem.builder().
                displayName("Lime Concrete Powder").
                rarity(COMMON).
                id(237).damage(5).
                build());
        register("CONCRETE_POWDER:6", BuildableGameItem.builder().
                displayName("Pink Concrete Powder").
                rarity(COMMON).
                id(237).damage(6).
                build());
        register("CONCRETE_POWDER:7", BuildableGameItem.builder().
                displayName("Gray Concrete Powder").
                rarity(COMMON).
                id(237).damage(7).
                build());
        register("CONCRETE_POWDER:8", BuildableGameItem.builder().
                displayName("Light Gray Concrete Powder").
                rarity(COMMON).
                id(237).damage(8).
                build());
        register("CONCRETE_POWDER:9", BuildableGameItem.builder().
                displayName("Cyan Concrete Powder").
                rarity(COMMON).
                id(237).damage(9).
                build());
        register("CONCRETE_POWDER:10", BuildableGameItem.builder().
                displayName("Purple Concrete Powder").
                rarity(COMMON).
                id(237).damage(10).
                build());
        register("CONCRETE_POWDER:11", BuildableGameItem.builder().
                displayName("Blue Concrete Powder").
                rarity(COMMON).
                id(237).damage(11).
                build());
        register("CONCRETE_POWDER:12", BuildableGameItem.builder().
                displayName("Brown Concrete Powder").
                rarity(COMMON).
                id(237).damage(12).
                build());
        register("CONCRETE_POWDER:13", BuildableGameItem.builder().
                displayName("Green Concrete Powder").
                rarity(COMMON).
                id(237).damage(13).
                build());
        register("CONCRETE_POWDER:14", BuildableGameItem.builder().
                displayName("Red Concrete Powder").
                rarity(COMMON).
                id(237).damage(14).
                build());
        register("CONCRETE_POWDER:15", BuildableGameItem.builder().
                displayName("Black Concrete Powder").
                rarity(COMMON).
                id(237).damage(15).
                build());
        register("CHORUS_PLANT", BuildableGameItem.builder().
                displayName("Chorus Plant").
                rarity(COMMON).
                id(240).damage(0).
                build());
        register("STAINED_GLASS", BuildableGameItem.builder().
                displayName("White Stained Glass").
                rarity(COMMON).
                id(241).damage(0).
                build());
        register("STAINED_GLASS:1", BuildableGameItem.builder().
                displayName("Orange Stained Glass").
                rarity(COMMON).
                id(241).damage(1).
                build());
        register("STAINED_GLASS:2", BuildableGameItem.builder().
                displayName("Magenta Stained Glass").
                rarity(COMMON).
                id(241).damage(2).
                build());
        register("STAINED_GLASS:3", BuildableGameItem.builder().
                displayName("Light Blue Stained Glass").
                rarity(COMMON).
                id(241).damage(3).
                build());
        register("STAINED_GLASS:4", BuildableGameItem.builder().
                displayName("Yellow Stained Glass").
                rarity(COMMON).
                id(241).damage(4).
                build());
        register("STAINED_GLASS:5", BuildableGameItem.builder().
                displayName("Lime Stained Glass").
                rarity(COMMON).
                id(241).damage(5).
                build());
        register("STAINED_GLASS:6", BuildableGameItem.builder().
                displayName("Pink Stained Glass").
                rarity(COMMON).
                id(241).damage(6).
                build());
        register("STAINED_GLASS:7", BuildableGameItem.builder().
                displayName("Gray Stained Glass").
                rarity(COMMON).
                id(241).damage(7).
                build());
        register("STAINED_GLASS:8", BuildableGameItem.builder().
                displayName("Light Gray Stained Glass").
                rarity(COMMON).
                id(241).damage(8).
                build());
        register("STAINED_GLASS:9", BuildableGameItem.builder().
                displayName("Cyan Stained Glass").
                rarity(COMMON).
                id(241).damage(9).
                build());
        register("STAINED_GLASS:10", BuildableGameItem.builder().
                displayName("Purple Stained Glass").
                rarity(COMMON).
                id(241).damage(10).
                build());
        register("STAINED_GLASS:11", BuildableGameItem.builder().
                displayName("Blue Stained Glass").
                rarity(COMMON).
                id(241).damage(11).
                build());
        register("STAINED_GLASS:12", BuildableGameItem.builder().
                displayName("Brown Stained Glass").
                rarity(COMMON).
                id(241).damage(12).
                build());
        register("STAINED_GLASS:13", BuildableGameItem.builder().
                displayName("Green Stained Glass").
                rarity(COMMON).
                id(241).damage(13).
                build());
        register("STAINED_GLASS:14", BuildableGameItem.builder().
                displayName("Red Stained Glass").
                rarity(COMMON).
                id(241).damage(14).
                build());
        register("STAINED_GLASS:15", BuildableGameItem.builder().
                displayName("Black Stained Glass").
                rarity(COMMON).
                id(241).damage(15).
                build());
        register("LIGHT_GRAY_GLAZED_TERRACOTTA", BuildableGameItem.builder().
                displayName("Light Gray Glazed Terracotta").
                rarity(COMMON).
                id(243).damage(0).
                build());
        register("OBSERVER", BuildableGameItem.builder().
                displayName("Observer").
                rarity(COMMON).
                id(251).damage(0).
                build());
        register("STRUCTURE_BLOCK", BuildableGameItem.builder().
                displayName("Structure Block").
                rarity(COMMON).
                id(255).damage(0).
                build());
        register("IRON_SHOVEL", BuildableGameItem.builder().
                displayName("Iron Shovel").
                rarity(COMMON).
                id(256).damage(0).
                build());
        register("IRON_PICKAXE", BuildableGameItem.builder().
                displayName("Iron Pickaxe").
                rarity(COMMON).
                id(257).damage(0).
                build());
        register("IRON_AXE", BuildableGameItem.builder().
                displayName("Iron Axe").
                rarity(COMMON).
                id(258).damage(0).
                build());
        register("FLINT_AND_STEEL", BuildableGameItem.builder().
                displayName("Flint and Steel").
                rarity(COMMON).
                id(259).damage(0).
                build());
        register("APPLE", BuildableGameItem.builder().
                displayName("Apple").
                rarity(COMMON).
                id(260).damage(0).
                build());
        register("BOW", BuildableGameItem.builder().
                displayName("Bow").
                rarity(COMMON).
                id(261).damage(0).
                build());
        register("ARROW", BuildableGameItem.builder().
                displayName("Arrow").
                rarity(COMMON).
                id(262).damage(0).
                build());
        register("COAL", BuildableGameItem.builder().
                displayName("Coal").
                rarity(COMMON).
                id(263).damage(0).
                build());
        register("COAL:1", BuildableGameItem.builder().
                displayName("Charcoal").
                rarity(COMMON).
                id(263).damage(1).
                build());
        register("DIAMOND", BuildableGameItem.builder().
                displayName("Diamond").
                rarity(COMMON).
                id(264).damage(0).
                build());
        register("IRON_INGOT", BuildableGameItem.builder().
                displayName("Iron Ingot").
                rarity(COMMON).
                id(265).damage(0).
                build());
        register("GOLD_INGOT", BuildableGameItem.builder().
                displayName("Gold Ingot").
                rarity(COMMON).
                id(266).damage(0).
                build());
        register("IRON_SWORD", BuildableGameItem.builder().
                displayName("Iron Sword").
                rarity(COMMON).
                id(267).damage(0).
                build());
        register("WOODEN_SWORD", BuildableGameItem.builder().
                displayName("Wooden Sword").
                rarity(COMMON).
                id(268).damage(0).
                build());
        register("WOODEN_SHOVEL", BuildableGameItem.builder().
                displayName("Wooden Shovel").
                rarity(COMMON).
                id(269).damage(0).
                build());
        register("WOODEN_PICKAXE", BuildableGameItem.builder().
                displayName("Wooden Pickaxe").
                rarity(COMMON).
                id(270).damage(0).
                build());
        register("WOODEN_AXE", BuildableGameItem.builder().
                displayName("Wooden Axe").
                rarity(COMMON).
                id(271).damage(0).
                build());
        register("STONE_SWORD", BuildableGameItem.builder().
                displayName("Stone Sword").
                rarity(COMMON).
                id(272).damage(0).
                build());
        register("STONE_SHOVEL", BuildableGameItem.builder().
                displayName("Stone Shovel").
                rarity(COMMON).
                id(273).damage(0).
                build());
        register("STONE_PICKAXE", BuildableGameItem.builder().
                displayName("Stone Pickaxe").
                rarity(COMMON).
                id(274).damage(0).
                build());
        register("STONE_AXE", BuildableGameItem.builder().
                displayName("Stone Axe").
                rarity(COMMON).
                id(275).damage(0).
                build());
        register("DIAMOND_SWORD", BuildableGameItem.builder().
                displayName("Diamond Sword").
                rarity(COMMON).
                id(276).damage(0).
                build());
        register("DIAMOND_SHOVEL", BuildableGameItem.builder().
                displayName("Diamond Shovel").
                rarity(COMMON).
                id(277).damage(0).
                build());
        register("DIAMOND_PICKAXE", BuildableGameItem.builder().
                displayName("Diamond Pickaxe").
                rarity(COMMON).
                id(278).damage(0).
                build());
        register("DIAMOND_AXE", BuildableGameItem.builder().
                displayName("Diamond Axe").
                rarity(COMMON).
                id(279).damage(0).
                build());
        register("STICK", BuildableGameItem.builder().
                displayName("Stick").
                rarity(COMMON).
                id(280).damage(0).
                build());
        register("BOWL", BuildableGameItem.builder().
                displayName("Bowl").
                rarity(COMMON).
                id(281).damage(0).
                build());
        register("MUSHROOM_STEW", BuildableGameItem.builder().
                displayName("Mushroom Stew").
                rarity(COMMON).
                id(282).damage(0).
                build());
        register("GOLDEN_SWORD", BuildableGameItem.builder().
                displayName("Golden Sword").
                rarity(COMMON).
                id(283).damage(0).
                build());
        register("GOLDEN_SHOVEL", BuildableGameItem.builder().
                displayName("Golden Shovel").
                rarity(COMMON).
                id(284).damage(0).
                build());
        register("GOLDEN_PICKAXE", BuildableGameItem.builder().
                displayName("Golden Pickaxe").
                rarity(COMMON).
                id(285).damage(0).
                build());
        register("GOLDEN_AXE", BuildableGameItem.builder().
                displayName("Golden Axe").
                rarity(COMMON).
                id(286).damage(0).
                build());
        register("STRING", BuildableGameItem.builder().
                displayName("String").
                rarity(COMMON).
                id(287).damage(0).
                build());
        register("FEATHER", BuildableGameItem.builder().
                displayName("Feather").
                rarity(COMMON).
                id(288).damage(0).
                build());
        register("GUNPOWDER", BuildableGameItem.builder().
                displayName("Gunpowder").
                rarity(COMMON).
                id(289).damage(0).
                build());
        register("WOODEN_HOE", BuildableGameItem.builder().
                displayName("Wooden Hoe").
                rarity(COMMON).
                id(290).damage(0).
                build());
        register("STONE_HOE", BuildableGameItem.builder().
                displayName("Stone Hoe").
                rarity(COMMON).
                id(291).damage(0).
                build());
        register("IRON_HOE", BuildableGameItem.builder().
                displayName("Iron Hoe").
                rarity(COMMON).
                id(292).damage(0).
                build());
        register("DIAMOND_HOE", BuildableGameItem.builder().
                displayName("Diamond Hoe").
                rarity(COMMON).
                id(293).damage(0).
                build());
        register("GOLDEN_HOE", BuildableGameItem.builder().
                displayName("Golden Hoe").
                rarity(COMMON).
                id(294).damage(0).
                build());
        register("SEEDS", BuildableGameItem.builder().
                displayName("Seeds").
                rarity(COMMON).
                id(295).damage(0).
                build());
        register("WHEAT", BuildableGameItem.builder().
                displayName("Wheat").
                rarity(COMMON).
                id(296).damage(0).
                build());
        register("BREAD", BuildableGameItem.builder().
                displayName("Bread").
                rarity(COMMON).
                id(297).damage(0).
                build());
        register("LEATHER_HELMET", BuildableGameItem.builder().
                displayName("Leather Helmet").
                rarity(COMMON).
                id(298).damage(0).
                build());
        register("LEATHER_CHESTPLATE", BuildableGameItem.builder().
                displayName("Leather Tunic").
                rarity(COMMON).
                id(299).damage(0).
                build());
        register("LEATHER_LEGGINGS", BuildableGameItem.builder().
                displayName("Leather Pants").
                rarity(COMMON).
                id(300).damage(0).
                build());
        register("LEATHER_BOOTS", BuildableGameItem.builder().
                displayName("Leather Boots").
                rarity(COMMON).
                id(301).damage(0).
                build());
        register("CHAINMAIL_HELMET", BuildableGameItem.builder().
                displayName("Chainmail Helmet").
                rarity(COMMON).
                id(302).damage(0).
                build());
        register("CHAINMAIL_CHESTPLATE", BuildableGameItem.builder().
                displayName("Chainmail Chestplate").
                rarity(COMMON).
                id(303).damage(0).
                build());
        register("CHAINMAIL_LEGGINGS", BuildableGameItem.builder().
                displayName("Chainmail Leggings").
                rarity(COMMON).
                id(304).damage(0).
                build());
        register("CHAINMAIL_BOOTS", BuildableGameItem.builder().
                displayName("Chainmail Boots").
                rarity(COMMON).
                id(305).damage(0).
                build());
        register("IRON_HELMET", BuildableGameItem.builder().
                displayName("Iron Helmet").
                rarity(COMMON).
                id(306).damage(0).
                build());
        register("IRON_CHESTPLATE", BuildableGameItem.builder().
                displayName("Iron Chestplate").
                rarity(COMMON).
                id(307).damage(0).
                build());
        register("IRON_LEGGINGS", BuildableGameItem.builder().
                displayName("Iron Leggings").
                rarity(COMMON).
                id(308).damage(0).
                build());
        register("IRON_BOOTS", BuildableGameItem.builder().
                displayName("Iron Boots").
                rarity(COMMON).
                id(309).damage(0).
                build());
        register("DIAMOND_HELMET", BuildableGameItem.builder().
                displayName("Diamond Helmet").
                rarity(COMMON).
                id(310).damage(0).
                build());
        register("DIAMOND_CHESTPLATE", BuildableGameItem.builder().
                displayName("Diamond Chestplate").
                rarity(COMMON).
                id(311).damage(0).
                build());
        register("DIAMOND_LEGGINGS", BuildableGameItem.builder().
                displayName("Diamond Leggings").
                rarity(COMMON).
                id(312).damage(0).
                build());
        register("DIAMOND_BOOTS", BuildableGameItem.builder().
                displayName("Diamond Boots").
                rarity(COMMON).
                id(313).damage(0).
                build());
        register("GOLDEN_HELMET", BuildableGameItem.builder().
                displayName("Golden Helmet").
                rarity(COMMON).
                id(314).damage(0).
                build());
        register("GOLDEN_CHESTPLATE", BuildableGameItem.builder().
                displayName("Golden Chestplate").
                rarity(COMMON).
                id(315).damage(0).
                build());
        register("GOLDEN_LEGGINGS", BuildableGameItem.builder().
                displayName("Golden Leggings").
                rarity(COMMON).
                id(316).damage(0).
                build());
        register("GOLDEN_BOOTS", BuildableGameItem.builder().
                displayName("Golden Boots").
                rarity(COMMON).
                id(317).damage(0).
                build());
        register("FLINT", BuildableGameItem.builder().
                displayName("Flint").
                rarity(COMMON).
                id(318).damage(0).
                build());
        register("PORKCHOP", BuildableGameItem.builder().
                displayName("Raw Porkchop").
                rarity(COMMON).
                id(319).damage(0).
                build());
        register("COOKED_PORKCHOP", BuildableGameItem.builder().
                displayName("Cooked Porkchop").
                rarity(COMMON).
                id(320).damage(0).
                build());
        register("PAINTING", BuildableGameItem.builder().
                displayName("Painting").
                rarity(COMMON).
                id(321).damage(0).
                build());
        register("GOLDEN_APPLE", BuildableGameItem.builder().
                displayName("Golden Apple").
                rarity(COMMON).
                id(322).damage(0).
                build());
        register("GOLDEN_APPLE:1", BuildableGameItem.builder().
                displayName("Enchanted Golden Apple").
                rarity(COMMON).
                id(322).damage(1).
                build());
        register("SIGN", BuildableGameItem.builder().
                displayName("Sign").
                rarity(COMMON).
                id(323).damage(0).
                build());
        register("WOODEN_DOOR", BuildableGameItem.builder().
                displayName("Oak Door Block").
                rarity(COMMON).
                id(324).damage(0).
                build());
        register("WOODEN_DOOR", BuildableGameItem.builder().
                displayName("Oak Door").
                rarity(COMMON).
                id(324).damage(0).
                build());
        register("BUCKET", BuildableGameItem.builder().
                displayName("Bucket").
                rarity(COMMON).
                id(325).damage(0).
                build());
        register("WATER_BUCKET", BuildableGameItem.builder().
                displayName("Water Bucket").
                rarity(COMMON).
                id(326).damage(0).
                build());
        register("LAVA_BUCKET", BuildableGameItem.builder().
                displayName("Lava Bucket").
                rarity(COMMON).
                id(327).damage(0).
                build());
        register("MINECART", BuildableGameItem.builder().
                displayName("Minecart").
                rarity(COMMON).
                id(328).damage(0).
                build());
        register("SADDLE", BuildableGameItem.builder().
                displayName("Saddle").
                rarity(COMMON).
                id(329).damage(0).
                build());
        register("IRON_DOOR", BuildableGameItem.builder().
                displayName("Iron Door Block").
                rarity(COMMON).
                id(330).damage(0).
                build());
        register("IRON_DOOR", BuildableGameItem.builder().
                displayName("Iron Door").
                rarity(COMMON).
                id(330).damage(0).
                build());
        register("REDSTONE", BuildableGameItem.builder().
                displayName("Redstone").
                rarity(COMMON).
                id(331).damage(0).
                build());
        register("SNOWBALL", BuildableGameItem.builder().
                displayName("Snowball").
                rarity(COMMON).
                id(332).damage(0).
                build());
        register("BOAT", BuildableGameItem.builder().
                displayName("Oak Boat").
                rarity(COMMON).
                id(333).damage(0).
                build());
        register("LEATHER", BuildableGameItem.builder().
                displayName("Leather").
                rarity(COMMON).
                id(334).damage(0).
                build());
        register("MILK_BUCKET", BuildableGameItem.builder().
                displayName("Milk Bucket").
                rarity(COMMON).
                id(335).damage(0).
                build());
        register("CLAY_BRICK", BuildableGameItem.builder().
                displayName("Brick").
                rarity(COMMON).
                id(336).damage(0).
                build());
        register("CLAY", BuildableGameItem.builder().
                displayName("Clay").
                rarity(COMMON).
                id(337).damage(0).
                build());
        register("CLAY_BALL", BuildableGameItem.builder().
                displayName("Clay").
                rarity(COMMON).
                id(337).damage(0).
                build());
        register("PAPER", BuildableGameItem.builder().
                displayName("Paper").
                rarity(COMMON).
                id(339).damage(0).
                build());
        register("BOOK", BuildableGameItem.builder().
                displayName("Book").
                rarity(COMMON).
                id(340).damage(0).
                build());
        register("SLIME_BALL", BuildableGameItem.builder().
                displayName("Slimeball").
                rarity(COMMON).
                id(341).damage(0).
                build());
        register("CHEST_MINECART", BuildableGameItem.builder().
                displayName("Minecart with Chest").
                rarity(COMMON).
                id(342).damage(0).
                build());
        register("FURNACE_MINECART", BuildableGameItem.builder().
                displayName("Minecart with Furnace").
                rarity(COMMON).
                id(343).damage(0).
                build());
        register("EGG", BuildableGameItem.builder().
                displayName("Egg").
                rarity(COMMON).
                id(344).damage(0).
                build());
        register("COMPASS", BuildableGameItem.builder().
                displayName("Compass").
                rarity(COMMON).
                id(345).damage(0).
                build());
        register("FISHING_ROD", BuildableGameItem.builder().
                displayName("Fishing Rod").
                rarity(COMMON).
                id(346).damage(0).
                build());
        register("CLOCK", BuildableGameItem.builder().
                displayName("Clock").
                rarity(COMMON).
                id(347).damage(0).
                build());
        register("GLOWSTONE_DUST", BuildableGameItem.builder().
                displayName("Glowstone Dust").
                rarity(COMMON).
                id(348).damage(0).
                build());
        register("RAW_FISH", BuildableGameItem.builder().
                displayName("Raw Fish").
                rarity(COMMON).
                id(349).damage(0).
                build());
        register("RAW_FISH:1", BuildableGameItem.builder().
                displayName("Raw Salmon").
                rarity(COMMON).
                id(349).damage(1).
                build());
        register("RAW_FISH:2", BuildableGameItem.builder().
                displayName("Clownfish").
                rarity(COMMON).
                id(349).damage(2).
                build());
        register("RAW_FISH:3", BuildableGameItem.builder().
                displayName("Pufferfish").
                rarity(COMMON).
                id(349).damage(3).
                build());
        register("COOKED_FISH", BuildableGameItem.builder().
                displayName("Cooked Fish").
                rarity(COMMON).
                id(350).damage(0).
                build());
        register("COOKED_FISH:1", BuildableGameItem.builder().
                displayName("Cooked Salmon").
                rarity(COMMON).
                id(350).damage(1).
                build());
        register("INK_SACK", BuildableGameItem.builder().
                displayName("Ink Sack").
                rarity(COMMON).
                id(351).damage(0).
                build());
        register("INK_SACK:1", BuildableGameItem.builder().
                displayName("Rose Red").
                rarity(COMMON).
                id(351).damage(1).
                build());
        register("INK_SACK:2", BuildableGameItem.builder().
                displayName("Cactus Green").
                rarity(COMMON).
                id(351).damage(2).
                build());
        register("INK_SACK:3", BuildableGameItem.builder().
                displayName("Coco Beans").
                rarity(COMMON).
                id(351).damage(3).
                build());
        register("INK_SACK:4", BuildableGameItem.builder().
                displayName("Lapis Lazuli").
                rarity(COMMON).
                id(351).damage(4).
                build());
        register("INK_SACK:5", BuildableGameItem.builder().
                displayName("Purple Dye").
                rarity(COMMON).
                id(351).damage(5).
                build());
        register("INK_SACK:6", BuildableGameItem.builder().
                displayName("Cyan Dye").
                rarity(COMMON).
                id(351).damage(6).
                build());
        register("INK_SACK:7", BuildableGameItem.builder().
                displayName("Light Gray Dye").
                rarity(COMMON).
                id(351).damage(7).
                build());
        register("INK_SACK:8", BuildableGameItem.builder().
                displayName("Gray Dye").
                rarity(COMMON).
                id(351).damage(8).
                build());
        register("INK_SACK:9", BuildableGameItem.builder().
                displayName("Pink Dye").
                rarity(COMMON).
                id(351).damage(9).
                build());
        register("INK_SACK:10", BuildableGameItem.builder().
                displayName("Lime Dye").
                rarity(COMMON).
                id(351).damage(10).
                build());
        register("INK_SACK:11", BuildableGameItem.builder().
                displayName("Dandelion Yellow").
                rarity(COMMON).
                id(351).damage(11).
                build());
        register("INK_SACK:12", BuildableGameItem.builder().
                displayName("Light Blue Dye").
                rarity(COMMON).
                id(351).damage(12).
                build());
        register("INK_SACK:13", BuildableGameItem.builder().
                displayName("Magenta Dye").
                rarity(COMMON).
                id(351).damage(13).
                build());
        register("INK_SACK:14", BuildableGameItem.builder().
                displayName("Orange Dye").
                rarity(COMMON).
                id(351).damage(14).
                build());
        register("INK_SACK:15", BuildableGameItem.builder().
                displayName("Bone Meal").
                rarity(COMMON).
                id(351).damage(15).
                build());
        register("BONE", BuildableGameItem.builder().
                displayName("Bone").
                rarity(COMMON).
                id(352).damage(0).
                build());
        register("SUGAR", BuildableGameItem.builder().
                displayName("Sugar").
                rarity(COMMON).
                id(353).damage(0).
                build());
        register("CAKE", BuildableGameItem.builder().
                displayName("Cake").
                rarity(COMMON).
                id(354).damage(0).
                build());
        register("BED", BuildableGameItem.builder().
                displayName("Bed").
                rarity(COMMON).
                id(355).damage(0).
                build());
        register("BED", BuildableGameItem.builder().
                displayName("Bed").
                rarity(COMMON).
                id(355).damage(0).
                build());
        register("REPEATER", BuildableGameItem.builder().
                displayName("Redstone Repeater").
                rarity(COMMON).
                id(356).damage(0).
                build());
        register("COOKIE", BuildableGameItem.builder().
                displayName("Cookie").
                rarity(COMMON).
                id(357).damage(0).
                build());
        register("FILLED_MAP", BuildableGameItem.builder().
                displayName("Map").
                rarity(COMMON).
                id(358).damage(0).
                build());
        register("MAP", BuildableGameItem.builder().
                displayName("Empty Map").
                rarity(COMMON).
                id(358).damage(0).
                build());
        register("SHEARS", BuildableGameItem.builder().
                displayName("Shears").
                rarity(COMMON).
                id(359).damage(0).
                build());
        register("MELON", BuildableGameItem.builder().
                displayName("Melon").
                rarity(COMMON).
                id(360).damage(0).
                build());
        register("PUMPKIN_SEEDS", BuildableGameItem.builder().
                displayName("Pumpkin Seeds").
                rarity(COMMON).
                id(361).damage(0).
                build());
        register("MELON_SEEDS", BuildableGameItem.builder().
                displayName("Melon Seeds").
                rarity(COMMON).
                id(362).damage(0).
                build());
        register("BEEF", BuildableGameItem.builder().
                displayName("Raw Beef").
                rarity(COMMON).
                id(363).damage(0).
                build());
        register("COOKED_BEEF", BuildableGameItem.builder().
                displayName("Steak").
                rarity(COMMON).
                id(364).damage(0).
                build());
        register("CHICKEN", BuildableGameItem.builder().
                displayName("Raw Chicken").
                rarity(COMMON).
                id(365).damage(0).
                build());
        register("COOKED_CHICKEN", BuildableGameItem.builder().
                displayName("Cooked Chicken").
                rarity(COMMON).
                id(366).damage(0).
                build());
        register("ROTTEN_FLESH", BuildableGameItem.builder().
                displayName("Rotten Flesh").
                rarity(COMMON).
                id(367).damage(0).
                build());
        register("ENDER_PEARL", BuildableGameItem.builder().
                displayName("Ender Pearl").
                rarity(COMMON).
                id(368).damage(0).
                build());
        register("BLAZE_ROD", BuildableGameItem.builder().
                displayName("Blaze Rod").
                rarity(COMMON).
                id(369).damage(0).
                build());
        register("GHAST_TEAR", BuildableGameItem.builder().
                displayName("Ghast Tear").
                rarity(COMMON).
                id(370).damage(0).
                build());
        register("GOLD_NUGGET", BuildableGameItem.builder().
                displayName("Gold Nugget").
                rarity(COMMON).
                id(371).damage(0).
                build());
        register("NETHER_WART", BuildableGameItem.builder().
                displayName("Nether Wart").
                rarity(COMMON).
                id(372).damage(0).
                build());
        register("NETHER_WART", BuildableGameItem.builder().
                displayName("Nether Wart").
                rarity(COMMON).
                id(372).damage(0).
                build());
        register("POTION", BuildableGameItem.builder().
                displayName("Potion").
                rarity(COMMON).
                id(373).damage(0).
                build());
        register("GLASS_BOTTLE", BuildableGameItem.builder().
                displayName("Glass Bottle").
                rarity(COMMON).
                id(374).damage(0).
                build());
        register("SPIDER_EYE", BuildableGameItem.builder().
                displayName("Spider Eye").
                rarity(COMMON).
                id(375).damage(0).
                build());
        register("FERMENTED_SPIDER_EYE", BuildableGameItem.builder().
                displayName("Fermented Spider Eye").
                rarity(COMMON).
                id(376).damage(0).
                build());
        register("BLAZE_POWDER", BuildableGameItem.builder().
                displayName("Blaze Powder").
                rarity(COMMON).
                id(377).damage(0).
                build());
        register("MAGMA_CREAM", BuildableGameItem.builder().
                displayName("Magma Cream").
                rarity(COMMON).
                id(378).damage(0).
                build());
        register("BREWING_STAND", BuildableGameItem.builder().
                displayName("Brewing Stand").
                rarity(COMMON).
                id(379).damage(0).
                build());
        register("BREWING_STAND", BuildableGameItem.builder().
                displayName("Brewing Stand").
                rarity(COMMON).
                id(379).damage(0).
                build());
        register("CAULDRON", BuildableGameItem.builder().
                displayName("Cauldron").
                rarity(COMMON).
                id(380).damage(0).
                build());
        register("ENDER_EYE", BuildableGameItem.builder().
                displayName("Eye of Ender").
                rarity(COMMON).
                id(381).damage(0).
                build());
        register("SPECKLED_MELON", BuildableGameItem.builder().
                displayName("Glistering Melon").
                rarity(COMMON).
                id(382).damage(0).
                build());
        register("CREATIVE_SPAWN_EGG", BuildableGameItem.builder().
                displayName("Spawn Unknown").
                rarity(COMMON).
                id(383).damage(0).
                build());
        register("EXPERIENCE_BOTTLE", BuildableGameItem.builder().
                displayName("Experience Bottle").
                rarity(COMMON).
                id(384).damage(0).
                build());
        register("FIRE_CHARGE", BuildableGameItem.builder().
                displayName("Fire Charge").
                rarity(COMMON).
                id(385).damage(0).
                build());
        register("WRITABLE_BOOK", BuildableGameItem.builder().
                displayName("Book and Quill").
                rarity(COMMON).
                id(386).damage(0).
                build());
        register("WRITTEN_BOOK", BuildableGameItem.builder().
                displayName("Written Book").
                rarity(COMMON).
                id(387).damage(0).
                build());
        register("EMERALD", BuildableGameItem.builder().
                displayName("Emerald").
                rarity(COMMON).
                id(388).damage(0).
                build());
        register("ITEM_FRAME", BuildableGameItem.builder().
                displayName("Item Frame").
                rarity(COMMON).
                id(389).damage(0).
                build());
        register("FLOWER_POT", BuildableGameItem.builder().
                displayName("Flower Pot").
                rarity(COMMON).
                id(390).damage(0).
                build());
        register("CARROT_ITEM", BuildableGameItem.builder().
                displayName("Carrot").
                rarity(COMMON).
                id(391).damage(0).
                build());
        register("POTATO_ITEM", BuildableGameItem.builder().
                displayName("Potato").
                rarity(COMMON).
                id(392).damage(0).
                build());
        register("BAKED_POTATO", BuildableGameItem.builder().
                displayName("Baked Potato").
                rarity(COMMON).
                id(393).damage(0).
                build());
        register("POISONOUS_POTATO", BuildableGameItem.builder().
                displayName("Poisonous Potato").
                rarity(COMMON).
                id(394).damage(0).
                build());
        register("GOLDEN_CARROT", BuildableGameItem.builder().
                displayName("Golden Carrot").
                rarity(COMMON).
                id(396).damage(0).
                build());
        register("SKULL_ITEM", BuildableGameItem.builder().
                displayName("Skeleton Skull").
                rarity(COMMON).
                id(397).damage(0).
                build());
        register("SKULL_ITEM:1", BuildableGameItem.builder().
                displayName("Wither Skeleton Skull").
                rarity(COMMON).
                id(397).damage(1).
                build());
        register("SKULL_ITEM:2", BuildableGameItem.builder().
                displayName("Zombie Skull").
                rarity(COMMON).
                id(397).damage(2).
                build());
        register("SKULL_ITEM:3", BuildableGameItem.builder().
                displayName("Human Skull").
                rarity(COMMON).
                id(397).damage(3).
                build());
        register("SKULL_ITEM:4", BuildableGameItem.builder().
                displayName("Creeper Skull").
                rarity(COMMON).
                id(397).damage(4).
                build());
        register("SKULL_ITEM:5", BuildableGameItem.builder().
                displayName("Dragon Skull").
                rarity(COMMON).
                id(397).damage(5).
                build());
        register("CARROT_STICK", BuildableGameItem.builder().
                displayName("Carrot on a Stick").
                rarity(COMMON).
                id(398).damage(0).
                build());
        register("NETHER_STAR", BuildableGameItem.builder().
                displayName("Nether Star").
                rarity(COMMON).
                id(399).damage(0).
                build());
        register("PUMPKIN_PIE", BuildableGameItem.builder().
                displayName("Pumpkin Pie").
                rarity(COMMON).
                id(400).damage(0).
                build());
        register("FIREWORKS", BuildableGameItem.builder().
                displayName("Firework Rocket").
                rarity(COMMON).
                id(401).damage(0).
                build());
        register("FIREWORK_CHARGE", BuildableGameItem.builder().
                displayName("Firework Star").
                rarity(COMMON).
                id(402).damage(0).
                build());
        register("ENCHANTED_BOOK", BuildableGameItem.builder().
                displayName("Enchanted Book").
                rarity(COMMON).
                id(403).damage(0).
                build());
        register("COMPARATOR", BuildableGameItem.builder().
                displayName("Redstone Comparator").
                rarity(COMMON).
                id(404).damage(0).
                build());
        register("NETHER_BRICK", BuildableGameItem.builder().
                displayName("Nether Brick").
                rarity(COMMON).
                id(405).damage(0).
                build());
        register("NETHERBRICK", BuildableGameItem.builder().
                displayName("Nether Brick").
                rarity(COMMON).
                id(405).damage(0).
                build());
        register("QUARTZ", BuildableGameItem.builder().
                displayName("Nether Quartz").
                rarity(COMMON).
                id(406).damage(0).
                build());
        register("TNT_MINECART", BuildableGameItem.builder().
                displayName("Minecart with TNT").
                rarity(COMMON).
                id(407).damage(0).
                build());
        register("HOPPER_MINECART", BuildableGameItem.builder().
                displayName("Minecart with Hopper").
                rarity(COMMON).
                id(408).damage(0).
                build());
        register("PRISMARINE_SHARD", BuildableGameItem.builder().
                displayName("Prismarine Shard").
                rarity(COMMON).
                id(409).damage(0).
                build());
        register("HOPPER", BuildableGameItem.builder().
                displayName("Hopper").
                rarity(COMMON).
                id(410).damage(0).
                build());
        register("RABBIT", BuildableGameItem.builder().
                displayName("Raw Rabbit").
                rarity(COMMON).
                id(411).damage(0).
                build());
        register("COOKED_RABBIT", BuildableGameItem.builder().
                displayName("Cooked Rabbit").
                rarity(COMMON).
                id(412).damage(0).
                build());
        register("RABBIT_STEW", BuildableGameItem.builder().
                displayName("Rabbit Stew").
                rarity(COMMON).
                id(413).damage(0).
                build());
        register("RABBIT_FOOT", BuildableGameItem.builder().
                displayName("Rabbit's Foot").
                rarity(COMMON).
                id(414).damage(0).
                build());
        register("RABBIT_HIDE", BuildableGameItem.builder().
                displayName("Rabbit Hide").
                rarity(COMMON).
                id(415).damage(0).
                build());
        register("IRON_HORSE_ARMOR", BuildableGameItem.builder().
                displayName("Iron Horse Armor").
                rarity(COMMON).
                id(417).damage(0).
                build());
        register("GOLDEN_HORSE_ARMOR", BuildableGameItem.builder().
                displayName("Golden Horse Armor").
                rarity(COMMON).
                id(418).damage(0).
                build());
        register("DIAMOND_HORSE_ARMOR", BuildableGameItem.builder().
                displayName("Diamond Horse Armor").
                rarity(COMMON).
                id(419).damage(0).
                build());
        register("LEAD", BuildableGameItem.builder().
                displayName("Lead").
                rarity(COMMON).
                id(420).damage(0).
                build());
        register("NAME_TAG", BuildableGameItem.builder().
                displayName("Name Tag").
                rarity(COMMON).
                id(421).damage(0).
                build());
        register("PRISMARINE_CRYSTALS", BuildableGameItem.builder().
                displayName("Prismarine Crystals").
                rarity(COMMON).
                id(422).damage(0).
                build());
        register("MUTTON", BuildableGameItem.builder().
                displayName("Raw Mutton").
                rarity(COMMON).
                id(423).damage(0).
                build());
        register("COOKED_MUTTON", BuildableGameItem.builder().
                displayName("Cooked Mutton").
                rarity(COMMON).
                id(424).damage(0).
                build());
        register("ARMOR_STAND", BuildableGameItem.builder().
                displayName("Armor Stand").
                rarity(COMMON).
                id(425).damage(0).
                build());
        register("END_CRYSTAL", BuildableGameItem.builder().
                displayName("End Crystal").
                rarity(COMMON).
                id(426).damage(0).
                build());
        register("SPRUCE_DOOR", BuildableGameItem.builder().
                displayName("Spruce Door").
                rarity(COMMON).
                id(427).damage(0).
                build());
        register("BIRCH_DOOR", BuildableGameItem.builder().
                displayName("Birch Door").
                rarity(COMMON).
                id(428).damage(0).
                build());
        register("JUNGLE_DOOR", BuildableGameItem.builder().
                displayName("Jungle Door").
                rarity(COMMON).
                id(429).damage(0).
                build());
        register("ACACIA_DOOR", BuildableGameItem.builder().
                displayName("Acacia Door").
                rarity(COMMON).
                id(430).damage(0).
                build());
        register("DARK_OAK_DOOR", BuildableGameItem.builder().
                displayName("Dark Oak Door").
                rarity(COMMON).
                id(431).damage(0).
                build());
        register("CHORUS_FRUIT", BuildableGameItem.builder().
                displayName("Chorus Fruit").
                rarity(COMMON).
                id(432).damage(0).
                build());
        register("POPPED_CHORUS_FRUIT", BuildableGameItem.builder().
                displayName("Popped Chorus Fruit").
                rarity(COMMON).
                id(433).damage(0).
                build());
        register("DRAGON_BREATH", BuildableGameItem.builder().
                displayName("Dragon's Breath").
                rarity(COMMON).
                id(437).damage(0).
                build());
        register("SPLASH_POTION", BuildableGameItem.builder().
                displayName("Splash Potion").
                rarity(COMMON).
                id(438).damage(0).
                build());
        register("COMMAND_BLOCK_MINECART", BuildableGameItem.builder().
                displayName("Minecart with Command Block").
                rarity(COMMON).
                id(443).damage(0).
                build());
        register("ELYTRA", BuildableGameItem.builder().
                displayName("Elytra").
                rarity(COMMON).
                id(444).damage(0).
                build());
        register("SPRUCE_BOAT", BuildableGameItem.builder().
                displayName("Spruce Boat").
                rarity(COMMON).
                id(444).damage(0).
                build());
        register("BIRCH_BOAT", BuildableGameItem.builder().
                displayName("Birch Boat").
                rarity(COMMON).
                id(445).damage(0).
                build());
        register("SHULKER_SHELL", BuildableGameItem.builder().
                displayName("Shulker Shell").
                rarity(COMMON).
                id(445).damage(0).
                build());
        register("BANNER", BuildableGameItem.builder().
                displayName("Banner").
                rarity(COMMON).
                id(446).damage(0).
                build());
        register("JUNGLE_BOAT", BuildableGameItem.builder().
                displayName("Jungle Boat").
                rarity(COMMON).
                id(446).damage(0).
                build());
        register("ACACIA_BOAT", BuildableGameItem.builder().
                displayName("Acacia Boat").
                rarity(COMMON).
                id(447).damage(0).
                build());
        register("DARK_OAK_BOAT", BuildableGameItem.builder().
                displayName("Dark Oak Boat").
                rarity(COMMON).
                id(448).damage(0).
                build());
        register("TOTEM_OF_UNDYING", BuildableGameItem.builder().
                displayName("Totem of Undying").
                rarity(COMMON).
                id(449).damage(0).
                build());
        register("IRON_NUGGET", BuildableGameItem.builder().
                displayName("Iron Nugget").
                rarity(COMMON).
                id(452).damage(0).
                build());
        register("KNOWLEDGE_BOOK", BuildableGameItem.builder().
                displayName("Knowledge Book").
                rarity(COMMON).
                id(453).damage(0).
                build());
        register("BEETROOT", BuildableGameItem.builder().
                displayName("Beetroot").
                rarity(COMMON).
                id(457).damage(0).
                build());
        register("BEETROOT_SEEDS", BuildableGameItem.builder().
                displayName("Beetroot Seeds").
                rarity(COMMON).
                id(458).damage(0).
                build());
        register("BEETROOT_SOUP", BuildableGameItem.builder().
                displayName("Beetroot Soup").
                rarity(COMMON).
                id(459).damage(0).
                build());
        register("RECORD_13", BuildableGameItem.builder().
                displayName("13 Disc").
                rarity(COMMON).
                id(500).damage(0).
                build());
        register("RECORD_CAT", BuildableGameItem.builder().
                displayName("Cat Disc").
                rarity(COMMON).
                id(501).damage(0).
                build());
        register("RECORD_BLOCKS", BuildableGameItem.builder().
                displayName("Blocks Disc").
                rarity(COMMON).
                id(502).damage(0).
                build());
        register("RECORD_CHIRP", BuildableGameItem.builder().
                displayName("Chirp Disc").
                rarity(COMMON).
                id(503).damage(0).
                build());
        register("RECORD_FAR", BuildableGameItem.builder().
                displayName("Far Disc").
                rarity(COMMON).
                id(504).damage(0).
                build());
        register("RECORD_MALL", BuildableGameItem.builder().
                displayName("Mall Disc").
                rarity(COMMON).
                id(505).damage(0).
                build());
        register("RECORD_MELLOHI", BuildableGameItem.builder().
                displayName("Mellohi Disc").
                rarity(COMMON).
                id(506).damage(0).
                build());
        register("RECORD_STAL", BuildableGameItem.builder().
                displayName("Stal Disc").
                rarity(COMMON).
                id(507).damage(0).
                build());
        register("RECORD_STRAD", BuildableGameItem.builder().
                displayName("Strad Disc").
                rarity(COMMON).
                id(508).damage(0).
                build());
        register("RECORD_WARD", BuildableGameItem.builder().
                displayName("Ward Disc").
                rarity(COMMON).
                id(509).damage(0).
                build());
        register("RECORD_11", BuildableGameItem.builder().
                displayName("11 Disc").
                rarity(COMMON).
                id(510).damage(0).
                build());
        register("RECORD_WAIT", BuildableGameItem.builder().
                displayName("Wait Disc").
                rarity(COMMON).
                id(511).damage(0).
                build());
        register("SHIELD", BuildableGameItem.builder().
                displayName("Shield").
                rarity(COMMON).
                id(513).damage(0).
                build());
    }

    private static void register(String id, IGameItem item) {
        ITEMS.put(id, item);
    }

}
