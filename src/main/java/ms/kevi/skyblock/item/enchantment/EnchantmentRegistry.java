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

import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationTargetException;

@AllArgsConstructor
public enum EnchantmentRegistry {

    BANE_OF_ARTHROPODS(BaneOfArthropodsEnchantment.class),
    CLEAVE(CleaveEnchantment.class),
    CRITICAL(CriticalEnchantment.class),
    CUBISM(CubismEnchantment.class),
    ENDER_SLAYER(EnderSlayerEnchantment.class),
    EXECUTE(ExecuteEnchantment.class),
    EXPERIENCE(ExperienceEnchantment.class),
    FIRE_ASPECT(FireAspectEnchantment.class),
    FIRST_STRIKE(FirstStrikeEnchantment.class),
    GIANT_KILLER(GiantKillerEnchantment.class),
    IMPALING(ImpalingEnchantment.class),
    KNOCKBACK(KnockbackEnchantment.class),
    LETHALITY(LethalityEnchantment.class),
    LIFE_STEAL(LifeStealEnchantment.class),
    LOOTING(LootingEnchantment.class),
    LUCK(LuckEnchantment.class),
    SCAVENGER(ScavengerEnchantment.class),
    SHARPNESS(SharpnessEnchantment.class),
    SMITE(SmiteEnchantment.class),
    TELEKINESIS(TelekinesisEnchantment.class),
    THUNDERLORD(ThunderlordEnchantment.class),
    VAMPIRISM(VampirismEnchantment.class),
    VENOMOUS(VenomousEnchantment.class),
    AIMING(AimingEnchantment.class),
    DRAGON_HUNTER(DragonHunterEnchantment.class),
    FLAME(FlameEnchantment.class),
    INFINITE_QUIVER(InfiniteQuiverEnchantment.class),
    PIERCING(PiercingEnchantment.class),
    POWER(PowerEnchantment.class),
    PUNCH(PunchEnchantment.class),
    SNIPE(SnipeEnchantment.class),
    AQUA_AFFINITY(AquaAffinityEnchantment.class),
    BLAST_PROTECTION(BlastProtectionEnchantment.class),
    DEPTH_STRIDER(DepthStriderEnchantment.class),
    FEATHER_FALLING(FeatherFallingEnchantment.class),
    FIRE_PROTECTION(FireProtectionEnchantment.class),
    FROST_WALKER(FrostWalkerEnchantment.class),
    GROWTH(GrowthEnchantment.class),
    PROJECTILE_PROTECTION(ProjectileProtectionEnchantment.class),
    PROTECTION(ProtectionEnchantment.class),
    RESPIRATION(RespirationEnchantment.class),
    THORNS(ThornsEnchantment.class),
    SUGAR_RUSH(SugarRushEnchantment.class),
    EFFICIENCY(EfficiencyEnchantment.class),
    FORTUNE(FortuneEnchantment.class),
    HARVESTING(HarvestingEnchantment.class),
    RAINBOW(RainbowEnchantment.class),
    SILK_TOUCH(SilkTouchEnchantment.class),
    SMELTING_TOUCH(SmeltingTouchEnchantment.class),
    ANGLER(AnglerEnchantment.class),
    BLESSING(BlessingEnchantment.class),
    CASTER(CasterEnchantment.class),
    FRAIL(FrailEnchantment.class),
    LUCK_OF_THE_SEA(LuckOfTheSeaEnchantment.class),
    LURE(LureEnchantment.class),
    MAGNET(MagnetEnchantment.class),
    SPIKED_HOOK(SpikedHookEnchantment.class);

    private final Class<? extends Enchantment> enchantment;

    public Enchantment newInstance(int level) {
        try {
            return this.enchantment.getDeclaredConstructor(int.class).newInstance(level);
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }

}
