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

import cn.nukkit.item.Item;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.custom.*;
import ms.kevi.skyblock.item.custom.accessory.EyeOfTheGolemItem;
import ms.kevi.skyblock.item.custom.accessory.StickOfWisdomItem;
import ms.kevi.skyblock.item.custom.accessory.piggy.BrokenPiggyBank;
import ms.kevi.skyblock.item.custom.accessory.piggy.CrackedPiggyBankItem;
import ms.kevi.skyblock.item.custom.accessory.piggy.PiggyBankItem;
import ms.kevi.skyblock.item.custom.crystal.CrystalItem;
import ms.kevi.skyblock.item.custom.dragon.superior.SuperiorDragonBoots;
import ms.kevi.skyblock.item.custom.dragon.superior.SuperiorDragonChestplate;
import ms.kevi.skyblock.item.custom.dragon.superior.SuperiorDragonHelmet;
import ms.kevi.skyblock.item.custom.dragon.superior.SuperiorDragonLeggings;
import ms.kevi.skyblock.item.custom.dragon.wise.WiseDragonBoots;
import ms.kevi.skyblock.item.custom.dragon.wise.WiseDragonChestplate;
import ms.kevi.skyblock.item.custom.dragon.wise.WiseDragonHelmet;
import ms.kevi.skyblock.item.custom.dragon.wise.WiseDragonLeggings;
import ms.kevi.skyblock.item.custom.fragment.GiantFragmentDiamondItem;
import ms.kevi.skyblock.item.custom.fragment.SuperiorDragonFragmentItem;
import ms.kevi.skyblock.item.custom.fragment.WiseDragonFragment;
import ms.kevi.skyblock.item.custom.material.EnchantedCobblestoneItem;
import ms.kevi.skyblock.item.custom.material.EnchantedPorkItem;
import ms.kevi.skyblock.item.custom.minion.MinionItem;
import ms.kevi.skyblock.item.custom.minion.upgrade.CompactorItem;
import ms.kevi.skyblock.item.custom.orb.ManaFluxPowerOrbItem;
import ms.kevi.skyblock.item.custom.orb.OverfluxPowerOrbItem;
import ms.kevi.skyblock.item.custom.orb.RadiantPowerOrbItem;
import ms.kevi.skyblock.item.custom.reforge.DragonHornItem;
import ms.kevi.skyblock.item.custom.titan.TitanBootsItem;
import ms.kevi.skyblock.item.custom.titan.TitanChestplateItem;
import ms.kevi.skyblock.item.custom.titan.TitanLeggingsItem;
import ms.kevi.skyblock.item.custom.wither.normal.WitherBoots;
import ms.kevi.skyblock.item.custom.wither.normal.WitherChestplate;
import ms.kevi.skyblock.item.custom.wither.normal.WitherHelmet;
import ms.kevi.skyblock.item.custom.wither.normal.WitherLeggings;
import ms.kevi.skyblock.item.custom.wither.power.PowerWitherBoots;
import ms.kevi.skyblock.item.custom.wither.power.PowerWitherChestplate;
import ms.kevi.skyblock.item.custom.wither.power.PowerWitherHelmet;
import ms.kevi.skyblock.item.custom.wither.power.PowerWitherLeggings;
import ms.kevi.skyblock.item.custom.wither.tank.TankWitherBoots;
import ms.kevi.skyblock.item.custom.wither.tank.TankWitherChestplate;
import ms.kevi.skyblock.item.custom.wither.tank.TankWitherHelmet;
import ms.kevi.skyblock.item.custom.wither.tank.TankWitherLeggings;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("all")
public class GameItemRegistry extends Registry<IGameItem> {

    // DEBUG ITEMS
    public static final IGameItem STICK_OF_WISDOM = new StickOfWisdomItem();
    public static final IGameItem EYE_OF_THE_GOLEM = new EyeOfTheGolemItem();
    public static final IGameItem DEBUG = new DebugItem();
    public static final IGameItem THE_FLASH = new TheFlashItem();
    // VALID INGAME ITEMS
    public static final IGameItem MENU = new MenuItem();
    public static final IGameItem PET = new PetItem();
    public static final IGameItem MINION = new MinionItem();
    public static final IGameItem CRYSTAL = new CrystalItem();
    public static final IGameItem ENCHANTED_COBBLESTONE = new EnchantedCobblestoneItem();
    public static final IGameItem ENCHANTED_PORK = new EnchantedPorkItem();
    public static final IGameItem PIGGY_BANK = new PiggyBankItem();
    public static final IGameItem CRACKED_PIGGY_BANK = new CrackedPiggyBankItem();
    public static final IGameItem BROKEN_PIGGY_BANK = new BrokenPiggyBank();
    public static final IGameItem RECOMBOBULATOR_3000 = new Recombobulator3000Item();
    public static final IGameItem RADIANT_POWER_ORB = new RadiantPowerOrbItem();
    public static final IGameItem MANA_FLUX_POWER_ORB = new ManaFluxPowerOrbItem();
    public static final IGameItem OVERFLUX_POWER_ORB = new OverfluxPowerOrbItem();
    public static final IGameItem ASPECT_OF_THE_DRAGONS = AspectOfTheDragonsItem.INSTANCE;
    public static final IGameItem ASPECT_OF_THE_END = new AspectOfTheEndItem();
    public static final IGameItem BONZO_STAFF = new BonzoStaffItem();
    public static final IGameItem WARDEN_HELMET = new WardenHelmetItem();
    public static final IGameItem BOSS = new BossItem();
    public static final IGameItem TITAN_CHESTPLATE = new TitanChestplateItem();
    public static final IGameItem TITAN_LEGGINGS = new TitanLeggingsItem();
    public static final IGameItem TITAN_BOOTS = new TitanBootsItem();
    public static final IGameItem DRAGON_HORN = new DragonHornItem();
    public static final IGameItem SUPERIOR_DRAGON_FRAGMENT = new SuperiorDragonFragmentItem();
    public static final IGameItem SUPERIOR_DRAGON_HELMET = new SuperiorDragonHelmet();
    public static final IGameItem SUPERIOR_DRAGON_CHESTPLATE = new SuperiorDragonChestplate();
    public static final IGameItem SUPERIOR_DRAGON_LEGGINGS = new SuperiorDragonLeggings();
    public static final IGameItem SUPERIOR_DRAGON_BOOTS = new SuperiorDragonBoots();
    public static final IGameItem WISE_DRAGON_FRAGMENT = new WiseDragonFragment();
    public static final IGameItem WISE_DRAGON_HELMET = new WiseDragonHelmet();
    public static final IGameItem WISE_DRAGON_CHESTPLATE = new WiseDragonChestplate();
    public static final IGameItem WISE_DRAGON_LEGGINGS = new WiseDragonLeggings();
    public static final IGameItem WISE_DRAGON_BOOTS = new WiseDragonBoots();
    public static final IGameItem WITHER_HELMET = new WitherHelmet();
    public static final IGameItem WITHER_CHESTPLATE = new WitherChestplate();
    public static final IGameItem WITHER_LEGGINGS = new WitherLeggings();
    public static final IGameItem WITHER_BOOTS = new WitherBoots();
    public static final IGameItem GIANT_FRAGMENT_DIAMOND = new GiantFragmentDiamondItem();
    public static final IGameItem POWER_WITHER_HELMET = new PowerWitherHelmet();
    public static final IGameItem POWER_WITHER_CHESTPLATE = new PowerWitherChestplate();
    public static final IGameItem POWER_WITHER_LEGGINGS = new PowerWitherLeggings();
    public static final IGameItem POWER_WITHER_BOOTS = new PowerWitherBoots();
    public static final IGameItem TANK_WITHER_HELMET = new TankWitherHelmet();
    public static final IGameItem TANK_WITHER_CHESTPLATE = new TankWitherChestplate();
    public static final IGameItem TANK_WITHER_LEGGINGS = new TankWitherLeggings();
    public static final IGameItem TANK_WITHER_BOOTS = new TankWitherBoots();
    public static final IGameItem COMPACTOR = new CompactorItem();

    private static final List<Class<? extends Registry<IGameItem>>> SUB_REGISTRIES = Collections.singletonList(VanillaItemRegistry.class);

    public IGameItem fromVanilla(Item item) {
        return this.fromVanilla(item.getId(), item.getDamage());
    }

    public IGameItem fromVanilla(int id, int damage) {
        for(IGameItem gameItem : this.values()) {
            if(this.registrarOf(gameItem) == VanillaItemRegistry.class) {
                if(gameItem.getId() == id && gameItem.getDamage() == damage)
                    return gameItem;
            }
        }
        return null;
    }

}
