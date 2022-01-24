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

package ms.kevi.skyblock.game.server;

import cn.nukkit.Nukkit;
import lombok.Getter;
import ms.kevi.skyblock.game.crafting.CraftingManager;
import ms.kevi.skyblock.game.crafting.recipe.Recipe;
import ms.kevi.skyblock.registry.Registries;
import ms.kevi.skyblock.util.Utils;

import java.util.Random;
import java.util.UUID;

@Getter
public class GameServer {

    private final Random random;

    private final UUID uniqueId;
    private final String displayId;
    private final ServerType type;

    private final CraftingManager craftingManager;

    public GameServer(ServerSize serverSize, ServerType serverType) {
        this.random = new Random(Nukkit.START_TIME);
        this.uniqueId = UUID.randomUUID();
        this.displayId = this.generateId(serverSize);
        this.type = serverType;

        this.craftingManager = new CraftingManager();
        for(Recipe recipe : Registries.RECIPES.values())
            this.craftingManager.addRecipe(recipe);
    }

    private String generateId(ServerSize serverSize) {
        final int number = this.random.nextInt(900) + 100;
        final char letter = Utils.randomAlphabeticChar(this.random);
        return serverSize.getPrefix() + number + letter;
    }

}
