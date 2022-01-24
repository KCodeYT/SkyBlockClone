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

package ms.kevi.skyblock.game.player;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.player.PlayerEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerToggleSneakEvent;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import lombok.Getter;
import ms.kevi.skyblock.game.attribute.Attribute;
import ms.kevi.skyblock.game.attribute.GameAttributes;
import ms.kevi.skyblock.game.bank.Bank;
import ms.kevi.skyblock.game.booster.BoosterHolder;
import ms.kevi.skyblock.game.booster.BoosterSlot;
import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.modifier.IModifier;
import ms.kevi.skyblock.game.pet.IPetAbility;
import ms.kevi.skyblock.game.pet.PetData;
import ms.kevi.skyblock.game.pet.Pets;
import ms.kevi.skyblock.game.player.bag.AccessoryBag;
import ms.kevi.skyblock.game.player.event.IntelligenceConsumeEvent;
import ms.kevi.skyblock.game.player.event.PlayerEventManager;
import ms.kevi.skyblock.game.player.event.UpdateGameAttributesEvent;
import ms.kevi.skyblock.game.stats.GameStats;
import ms.kevi.skyblock.game.stats.StatsHolder;
import ms.kevi.skyblock.item.IGameItem;
import ms.kevi.skyblock.item.ItemHelper;
import ms.kevi.skyblock.item.ability.AbilityType;
import ms.kevi.skyblock.item.ability.IAbility;
import ms.kevi.skyblock.item.enchantment.Enchantment;
import ms.kevi.skyblock.item.registry.set.ArmorSetItem;
import ms.kevi.skyblock.item.registry.set.PieceItem;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.util.Utils;
import ms.kevi.skyblock.util.api.ScoreboardAPI;

import java.util.*;

@Getter
public class SkyBlockPlayer {

    private final Player player;
    private final GameAttributes gameAttributes;
    private final BoosterHolder boosterHolder;
    private final Bank bank;
    private final Pets pets;
    private final AccessoryBag accessoryBag;

    private final PlayerEventManager eventManager;

    private final Map<IAbility, Long> coolDowns;
    private final Map<AbilitySlot, IAbility> activeAbilities;

    public SkyBlockPlayer(Player player) {
        this.player = player;
        this.gameAttributes = new GameAttributes();
        this.boosterHolder = new BoosterHolder();
        this.bank = new Bank();
        this.pets = new Pets();
        this.accessoryBag = new AccessoryBag();

        this.eventManager = new PlayerEventManager(this);

        this.coolDowns = new HashMap<>();
        this.activeAbilities = new HashMap<>();
    }

    public void tick() {
        final int currentTick = this.player.getServer().getTick();
        if(currentTick % 3 == 0)
            ScoreboardAPI.update(this.player);

        this.updateAttributes();
        if(currentTick % 3 == 0) {
            final String health = "§c" + this.gameAttributes.get(GameStats.HEALTH).getValue() + "/" + this.gameAttributes.get(GameStats.HEALTH).getMaxValue() + " HP";
            final String defense = "§a" + this.gameAttributes.get(GameStats.DEFENSE).getValue() + " Defense";
            final String intelligence = "§b" + this.gameAttributes.get(GameStats.INTELLIGENCE).getValue() + "/" + this.gameAttributes.get(GameStats.INTELLIGENCE).getMaxValue() + " Mana";
            this.player.sendActionBar(health + (this.gameAttributes.get(GameStats.DEFENSE).getValue() > 0 ? "    " + defense : "") + "    " + intelligence);
        }

        final float defaultSpeed = Player.DEFAULT_SPEED;
        final float movementSpeed = (defaultSpeed * ((float) this.gameAttributes.get(GameStats.SPEED).getValue() / 100)) + (this.player.isSprinting() ? 0.03F : 0);
        if(this.player.getMovementSpeed() != movementSpeed)
            this.player.setMovementSpeed(movementSpeed);
        this.player.sendTip("Movement: " + movementSpeed + " | Speed: " + this.gameAttributes.get(GameStats.SPEED).getValue() + "/" + this.gameAttributes.get(GameStats.SPEED).getMaxValue() + "/" + this.gameAttributes.get(GameStats.SPEED).getForcedMaxValue());

        if(currentTick % 20 == 0)
            this.gameAttributes.applyRegenerations(new ArrayList<>(this.boosterHolder.currentBooster().getRegenerations()));
    }

    private void updateAttributes() {
        final Map<GameStats, StatsHolder> holderMap = new HashMap<>();
        for(GameStats gameStats : GameStats.values()) {
            if(gameStats.isIStatic())
                continue;
            final Attribute attribute = this.gameAttributes.get(gameStats);
            holderMap.put(gameStats, new StatsHolder(attribute.getValue(), attribute.getValue() >= attribute.getMaxValue()));
        }

        this.gameAttributes.reset(true);
        this.boosterHolder.clear();
        this.boosterHolder.addBooster(BoosterSlot.DEFAULT, StatsBooster.defaultOf(this));
        for(GameStats stats : GameStats.values())
            this.gameAttributes.get(stats).setForcedMaxValue(stats.getMaxValue());
        this.updateAbilities();
        this.eventManager.run(new UpdateGameAttributesEvent(this));

        final StatsBooster currentBooster = StatsBooster.combine(this.boosterHolder.getBoosterSnapshot().values());
        for(GameStats gameStats : currentBooster.getKeySet()) {
            final Attribute attribute = this.gameAttributes.get(gameStats);
            attribute.setMaxValue(attribute.getMaxValue() + currentBooster.get(gameStats), true);
            if(!gameStats.isIStatic() && holderMap.get(gameStats).isSame()) {
                final int diff = attribute.getMaxValue() - attribute.getValue();
                if(diff > 0)
                    attribute.setValue(attribute.getValue() + diff, true);
            }
        }

        final Map<GameStats, Integer> maxValues = new HashMap<>();
        for(GameStats gameStats : GameStats.values())
            maxValues.put(gameStats, this.gameAttributes.get(gameStats).getMaxValue());

        for(GameStats gameStats : currentBooster.getPercentKeySet()) {
            final Attribute attribute = this.gameAttributes.get(gameStats);
            final int maxValue = maxValues.get(gameStats);
            final int percentValue = (int) (maxValue * (currentBooster.getPercent(gameStats) / 100));
            attribute.setMaxValue(maxValue + percentValue, true);
            if(!gameStats.isIStatic() && holderMap.get(gameStats).isSame()) {
                final int diff = attribute.getMaxValue() - attribute.getValue();
                if(diff > 0)
                    attribute.setValue(attribute.getValue() + diff, true);
            }
        }

        for(GameStats gameStats : GameStats.values()) {
            final Attribute attribute = this.gameAttributes.get(gameStats);
            if(!gameStats.isIStatic() && holderMap.containsKey(gameStats) && attribute.getValue() != attribute.getMaxValue())
                attribute.setValue(holderMap.get(gameStats).getValue(), true);
            if(attribute.getValue() > attribute.getMaxValue())
                attribute.setValue(attribute.getMaxValue(), true);
        }
    }

    private void updateAbilities() {
        final PlayerInventory inventory = this.player.getInventory();

        final PetData activePetData = this.pets.getActive().getPetData();
        if(activePetData != null) {
            final List<IPetAbility> petAbilities = activePetData.getType().getPetAbilities(activePetData);
            for(IPetAbility petAbility : petAbilities)
                this.boosterHolder.addBooster(BoosterSlot.PET, petAbility.getStatsBooster(activePetData));
            this.boosterHolder.addBooster(BoosterSlot.PET, activePetData.getType().getStatsBooster(activePetData));
        }

        final List<Item> accessories = AccessoryBag.filterHighest(inventory.getContents().values());
        accessories.addAll(this.accessoryBag.filterHighest());
        accessories.stream().map(item -> Registries.ITEMS.valueOf(ItemHelper.getAttributeId(item))).
                filter(gameItem -> gameItem.getStatsBooster() != null).
                forEach(item -> this.boosterHolder.addBooster(BoosterSlot.ACCESSORIES, item.getStatsBooster()));

        for(AbilitySlot slot : AbilitySlot.values()) {
            final Item item = slot.getSelector().apply(inventory);
            final IGameItem gameItem = ItemHelper.getGameItem(item);
            final IAbility current;
            final List<StatsBooster> boosters;

            switch(slot) {
                case FULL_SET:
                    current = this.getCurrentSetBonus(new IGameItem[]{
                            ItemHelper.getGameItem(inventory.getHelmet()),
                            ItemHelper.getGameItem(inventory.getChestplate()),
                            ItemHelper.getGameItem(inventory.getLeggings()),
                            ItemHelper.getGameItem(inventory.getBoots())
                    });
                    if(current != null && current.getStatsBooster() != null)
                        boosters = Collections.singletonList(current.getStatsBooster());
                    else
                        boosters = Collections.emptyList();
                    break;
                case ITEM:
                case HELMET:
                case CHESTPLATE:
                case LEGGINGS:
                case BOOTS:
                    if(slot == AbilitySlot.ITEM)
                        current = gameItem != null ? gameItem.getAbility() : null;
                    else
                        current = gameItem instanceof PieceItem ? ((PieceItem) gameItem).getBonus() : gameItem != null ? gameItem.getAbility() : null;

                    final IModifier modifier;
                    boosters = new ArrayList<>();

                    if(gameItem != null && gameItem.getStatsBooster() != null)
                        boosters.add(gameItem.getStatsBooster());

                    if(current != null && current.getStatsBooster() != null)
                        boosters.add(current.getStatsBooster());

                    if((modifier = ItemHelper.getModifier(item)) != null) {
                        boosters.add(modifier.getStatsBooster(ItemHelper.getRarity(item)));
                        if(modifier.getModifierBonus() != null)
                            boosters.add(modifier.getModifierBonus().getStatsBooster());
                    }

                    final List<Enchantment> enchantments;
                    if((enchantments = ItemHelper.getEnchantments(item)) != null) {
                        for(Enchantment enchantment : enchantments) {
                            final StatsBooster statsBooster = enchantment.getStatsBooster();
                            if(statsBooster != null)
                                boosters.add(statsBooster);
                        }
                    }
                    break;
                case HELMET_SPECIAL:
                case CHESTPLATE_SPECIAL:
                case LEGGINGS_SPECIAL:
                case BOOTS_SPECIAL:
                    current = gameItem instanceof PieceItem ? ((PieceItem) gameItem).getExtraBonus() : null;
                    if(current != null && current.getStatsBooster() != null)
                        boosters = Collections.singletonList(current.getStatsBooster());
                    else
                        boosters = Collections.emptyList();
                    break;
                default:
                    current = null;
                    boosters = Collections.emptyList();
                    break;
            }

            final BoosterSlot boosterSlot = slot.getBoosterSlot();
            final boolean canBeUsed;
            if((gameItem != null && (boosterSlot == BoosterSlot.HAND) == (gameItem.getItemType() != null && !gameItem.getItemType().isWearable())) || boosterSlot == BoosterSlot.FULL_SET_BONUS) {
                for(StatsBooster booster : boosters)
                    this.boosterHolder.addBooster(boosterSlot, booster);
                canBeUsed = true;
            } else
                canBeUsed = false;

            final IAbility previous = this.activeAbilities.get(slot);
            if(current != previous) {
                if(previous != null)
                    previous.onDeactivate(this.player);
                if(current != null && canBeUsed) {
                    current.onActivate(this.player);
                }
            }

            if(current != null && canBeUsed)
                current.onTick(this.player, this.player.getServer().getTick());

            this.activeAbilities.put(slot, canBeUsed ? current : null);
        }
    }

    private IAbility getCurrentSetBonus(IGameItem[] currentSet) {
        for(IGameItem item : currentSet) {
            if(item instanceof ArmorSetItem) {
                final ArmorSetItem armorSetItem = (ArmorSetItem) item;
                if(Utils.equalsOrNull(armorSetItem.getHelmet(), currentSet[0]) && Utils.equalsOrNull(armorSetItem.getChestplate(), currentSet[1]) &&
                        Utils.equalsOrNull(armorSetItem.getLeggings(), currentSet[2]) && Utils.equalsOrNull(armorSetItem.getBoots(), currentSet[3]))
                    return item.getAbility();
            }
        }

        return null;
    }

    public double getCoolDown(IAbility ability) {
        if(ability.getCoolDown() == 0 || !this.coolDowns.containsKey(ability))
            return 0;
        final double timeElapsed = (System.currentTimeMillis() - this.coolDowns.get(ability)) - (ability.getCoolDown() * 1000);
        return timeElapsed >= 0 ? 0 : Math.abs((Math.floor(timeElapsed / 1000) + 1) * 1000);
    }

    public void useAbility(IAbility ability, PlayerEvent event) {
        if(ability.getType() != AbilityType.ITEM)
            return;

        final double coolDown = this.getCoolDown(ability);
        if(coolDown > 0) {
            final int seconds = (int) Math.round(coolDown / 1000);
            this.player.sendMessage("§cThis ability is currently on cooldown for " + seconds + " more second" + (seconds > 1 ? "s" : "") + "!");
            return;
        }

        final int intelligenceCost = ability.getIntelligenceCost();
        final Attribute attribute = this.gameAttributes.get(GameStats.INTELLIGENCE);
        final int currentIntelligence = attribute.getValue();
        final IntelligenceConsumeEvent intelligenceConsumeEvent = new IntelligenceConsumeEvent(this, intelligenceCost);
        if(this.eventManager.run(intelligenceConsumeEvent)) {
            final boolean success;
            if(event instanceof PlayerInteractEvent)
                success = ability.onInteract((PlayerInteractEvent) event);
            else if(event instanceof PlayerToggleSneakEvent)
                success = ability.onSneak((PlayerToggleSneakEvent) event);
            else if(event instanceof PlayerDropItemEvent)
                success = ability.onDrop((PlayerDropItemEvent) event);
            else
                success = false;

            if(!success && intelligenceCost != 0) {
                attribute.setValue(currentIntelligence);
                return;
            }

            if(ability.getCoolDown() > 0)
                this.coolDowns.put(ability, System.currentTimeMillis());
            this.player.sendTip("§aUsed §6" + ability.getDisplayName() + "§r§a!" + (intelligenceCost != 0 ? " §b(-" + intelligenceConsumeEvent.getIntelligence() + " Mana)" : ""));
            return;
        }

        this.player.sendMessage("§cYou do not have enough mana to do this!");
    }

    public void load() {

    }

    public void save() {

    }

}
