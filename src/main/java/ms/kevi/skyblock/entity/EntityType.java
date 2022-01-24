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

package ms.kevi.skyblock.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SuppressWarnings("ALL")
public enum EntityType {

    CHICKEN("minecraft:chicken", 10),
    COW("minecraft:cow", 11),
    PIG("minecraft:pig", 12),
    SHEEP("minecraft:sheep", 13),
    WOLF("minecraft:wolf", 14),
    VILLAGER("minecraft:villager", 15),
    MOOSHROOM("minecraft:mooshroom", 16),
    SQUID("minecraft:squid", 17),
    RABBIT("minecraft:rabbit", 18),
    BAT("minecraft:bat", 19),
    IRON_GOLEM("minecraft:iron_golem", 20),
    SNOW_GOLEM("minecraft:snow_golem", 21),
    OCELOT("minecraft:ocelot", 22),
    HORSE("minecraft:horse", 23),
    DONKEY("minecraft:donkey", 24),
    MULE("minecraft:mule", 25),
    SKELETON_HORSE("minecraft:skeleton_horse", 26),
    ZOMBIE_HORSE("minecraft:zombie_horse", 27),
    POLAR_BEAR("minecraft:polar_bear", 28),
    LLAMA("minecraft:llama", 29),
    PARROT("minecraft:parrot", 30),
    DOLPHIN("minecraft:dolphin", 31),
    ZOMBIE("minecraft:zombie", 32),
    CREEPER("minecraft:creeper", 33),
    SKELETON("minecraft:skeleton", 34),
    SPIDER("minecraft:spider", 35),
    ZOMBIE_PIGMAN("minecraft:zombie_pigman", 36),
    SLIME("minecraft:slime", 37),
    ENDERMAN("minecraft:enderman", 38),
    SILVERFISH("minecraft:silverfish", 39),
    CAVE_SPIDER("minecraft:cave_spider", 40),
    GHAST("minecraft:ghast", 41),
    MAGMA_CUBE("minecraft:magma_cube", 42),
    BLAZE("minecraft:blaze", 43),
    ZOMBIE_VILLAGER("minecraft:zombie_villager", 44),
    WITCH("minecraft:witch", 45),
    STRAY("minecraft:stray", 46),
    HUSK("minecraft:husk", 47),
    WITHER_SKELETON("minecraft:wither_skeleton", 48),
    GUARDIAN("minecraft:guardian", 49),
    ELDER_GUARDIAN("minecraft:elder_guardian", 50),
    NPC("minecraft:npc", 51),
    WITHER("minecraft:wither", 52),
    ENDER_DRAGON("minecraft:ender_dragon", 53),
    SHULKER("minecraft:shulker", 54),
    ENDERMITE("minecraft:endermite", 55),
    AGENT("minecraft:agent", 56),
    VINDICATOR("minecraft:vindicator", 57),
    PHANTOM("minecraft:phantom", 58),
    RAVAGER("minecraft:ravager", 59),
    ARMOR_STAND("minecraft:armor_stand", 61),
    TRIPOD_CAMERA("minecraft:tripod_camera", 62),
    ITEM("minecraft:item", 64),
    TNT("minecraft:tnt", 65),
    FALLING_BLOCK("minecraft:falling_block", 66),
    XP_BOTTLE("minecraft:xp_bottle", 68),
    XP_ORB("minecraft:xp_orb", 69),
    EYE_OF_ENDER_SIGNAL("minecraft:eye_of_ender_signal", 70),
    ENDER_CRYSTAL("minecraft:ender_crystal", 71),
    FIREWORKS_ROCKET("minecraft:fireworks_rocket", 72),
    THROWN_TRIDENT("minecraft:thrown_trident", 73),
    TURTLE("minecraft:turtle", 74),
    CAT("minecraft:cat", 75),
    SHULKER_BULLET("minecraft:shulker_bullet", 76),
    FISHING_HOOK("minecraft:fishing_hook", 77),
    DRAGON_FIREBALL("minecraft:dragon_fireball", 79),
    ARROW("minecraft:arrow", 80),
    SNOWBALL("minecraft:snowball", 81),
    EGG("minecraft:egg", 82),
    PAINTING("minecraft:painting", 83),
    MINECART("minecraft:minecart", 84),
    FIREBALL("minecraft:fireball", 85),
    SPLASH_POTION("minecraft:splash_potion", 86),
    ENDER_PEARL("minecraft:ender_pearl", 87),
    LEASH_KNOT("minecraft:leash_knot", 88),
    WITHER_SKULL("minecraft:wither_skull", 89),
    BOAT("minecraft:boat", 90),
    WITHER_SKULL_DANGEROUS("minecraft:wither_skull_dangerous", 91),
    LIGHTNING_BOLT("minecraft:lightning_bolt", 93),
    SMALL_FIREBALL("minecraft:small_fireball", 94),
    AREA_EFFECT_CLOUD("minecraft:area_effect_cloud", 95),
    HOPPER_MINECART("minecraft:hopper_minecart", 96),
    TNT_MINECART("minecraft:tnt_minecart", 97),
    CHEST_MINECART("minecraft:chest_minecart", 98),
    COMMAND_BLOCK_MINECART("minecraft:command_block_minecart", 100),
    LINGERING_POTION("minecraft:lingering_potion", 101),
    LLAMA_SPIT("minecraft:llama_spit", 102),
    EVOCATION_FANG("minecraft:evocation_fang", 103),
    EVOCATION_ILLAGER("minecraft:evocation_illager", 104),
    VEX("minecraft:vex", 105),
    ICE_BOMB("minecraft:ice_bomb", 106),
    BALLOON("minecraft:balloon", 107),
    PUFFERFISH("minecraft:pufferfish", 108),
    SALMON("minecraft:salmon", 109),
    DROWNED("minecraft:drowned", 110),
    TROPICALFISH("minecraft:tropicalfish", 111),
    COD("minecraft:cod", 112),
    PANDA("minecraft:panda", 113),
    PILLAGER("minecraft:pillager", 114),
    VILLAGER_V2("minecraft:villager_v2", 115),
    ZOMBIE_VILLAGER_V2("minecraft:zombie_villager_v2", 116),
    WANDERING_TRADER("minecraft:wandering_trader", 118),
    ELDER_GUARDIAN_GHOST("minecraft:elder_guardian_ghost", 120),
    FOX("minecraft:fox", 121),
    BEE("minecraft:bee", 122),
    PIGLIN("minecraft:piglin", 123),
    HOGLIN("minecraft:hoglin", 124),
    STRIDER("minecraft:strider", 125),
    ZOGLIN("minecraft:zoglin", 126),
    PLAYER("minecraft:player", 257);

    private final String identifier;
    private final int networkId;

}
