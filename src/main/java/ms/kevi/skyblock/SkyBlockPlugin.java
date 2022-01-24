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

package ms.kevi.skyblock;

import cn.nukkit.entity.Entity;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import com.google.gson.Gson;
import lombok.Getter;
import ms.kevi.skyblock.command.*;
import ms.kevi.skyblock.entity.crystal.EntityCrystal;
import ms.kevi.skyblock.entity.minion.EntityMinion;
import ms.kevi.skyblock.entity.orb.EntityOrb;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.date.GameCalender;
import ms.kevi.skyblock.game.server.GameServer;
import ms.kevi.skyblock.game.server.ServerSize;
import ms.kevi.skyblock.game.server.ServerType;
import ms.kevi.skyblock.level.LevelUtil;
import ms.kevi.skyblock.level.VoidGenerator;
import ms.kevi.skyblock.level.block.BlockAnvil;
import ms.kevi.skyblock.level.block.BlockEnchantingTable;
import ms.kevi.skyblock.level.block.BlockFarmlandOverride;
import ms.kevi.skyblock.level.block.BlockOverride;
import ms.kevi.skyblock.listener.DamageListener;
import ms.kevi.skyblock.listener.ItemHandlerListener;
import ms.kevi.skyblock.listener.PlayerListener;
import ms.kevi.skyblock.listener.SkullListener;
import ms.kevi.skyblock.scheduler.TaskExecutor;
import ms.kevi.skyblock.util.CommandUtil;
import ms.kevi.skyblock.util.DefaultUtils;
import ms.kevi.skyblock.util.EventUtil;
import ms.kevi.skyblock.util.Timings;

import java.io.File;

public class SkyBlockPlugin extends PluginBase {

    public static final Gson GSON = new Gson();

    @Getter
    private static SkyBlockPlugin instance;
    @Getter
    private static Config serverConfig;

    @Override
    public void onLoad() {
        instance = this;

        try {
            Class.forName("ms.kevi.skyblock.registry.Registries");
            Class.forName("ms.kevi.skyblock.registry.ItemRegistryInit");
        } catch(Throwable e) {
            e.printStackTrace();
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        Generator.addGenerator(VoidGenerator.class, "Void", Generator.TYPE_FLAT);

        Entity.registerEntity("Minion", EntityMinion.class, true);
        Entity.registerEntity("Crystal", EntityCrystal.class, true);
        Entity.registerEntity("PowerOrb", EntityOrb.class, true);

        this.getLogger().info("Load skyblock server...");
        final ConfigSection configSection = new ConfigSection();
        configSection.put("ServerType", ServerType.HUB.name());
        configSection.put("ServerSize", ServerSize.MEGA.name());
        serverConfig = new Config(new File("./server.yml"), Config.YAML, configSection);

        final GameServer gameServer = GameHolder.getGameServer();
        this.getServer().setPropertyString("motd", "§r§6SkyBlock §r§7" + gameServer.getDisplayId());

        LevelUtil.init(gameServer.getType());
        this.getLogger().info("SkyBlock server information:");
        this.getLogger().info("UniqueId: " + gameServer.getUniqueId());
        this.getLogger().info("Display: " + gameServer.getDisplayId());
        this.getLogger().info("Type: " + gameServer.getType());
        this.getLogger().info("Loaded skyblock server successfully!");
    }

    @Override
    public void onEnable() {
        EventUtil.registerListener(new SkullListener());
        EventUtil.registerListener(new PlayerListener());
        EventUtil.registerListener(new DamageListener());
        EventUtil.registerListener(new ItemHandlerListener());

        CommandUtil.register(new CreativeMenuCommand());
        CommandUtil.register(new NewTimingsCommand());
        CommandUtil.register(new SbClearCommand());
        CommandUtil.register(new SetStatsCommand());
        CommandUtil.register(new GivePetCommand());
        CommandUtil.register(new GivePetXpCommand());
        CommandUtil.register(new GiveMinionCommand());
        CommandUtil.register(new GiveCommand());
        CommandUtil.register(new GiveItemToCommand());
        CommandUtil.register(new EnchantCommand());
        CommandUtil.register(new ViewRecipeCommand());
        TaskExecutor.repeating(() -> Timings.addEntityCount(this.getServer().getDefaultLevel().getEntities().length), 10);
        TaskExecutor.repeating(() -> this.getServer().getLevels().values().forEach(level -> level.setTime(GameCalender.getCurrentLevelTime(level))), 166);

        BlockOverride.register(new BlockFarmlandOverride());
        BlockOverride.register(new BlockEnchantingTable());
        BlockOverride.register(new BlockAnvil());
        DefaultUtils.init(this);
    }

    @Override
    public void onDisable() {

    }

}
