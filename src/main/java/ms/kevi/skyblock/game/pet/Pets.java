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

package ms.kevi.skyblock.game.pet;

import cn.nukkit.Player;
import lombok.Getter;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.GameRarity;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Pets {

    private final List<PetData> pets = new ArrayList<>();
    private final PetHolder active = new PetHolder();

    public void addPet(IPetType petType, GameRarity rarity, double experience) {
        this.addPet(new PetData(petType, rarity, experience));
    }

    public void addPet(IPetType petType, GameRarity rarity, int level) {
        this.addPet(new PetData(petType, rarity, PetLevelData.getExperience(rarity, level)));
    }

    public void addPet(PetData petData) {
        this.pets.add(petData);
    }

    public void removePet(PetData petData) {
        this.pets.remove(petData);
    }

    public List<PetData> getPets() {
        return Collections.unmodifiableList(this.pets.stream().sorted().collect(Collectors.toList()));
    }

    public boolean enablePet(Player player, PetData petData) {
        this.disablePet();
        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(player);
        if(skyBlockPlayer == null)
            return false;

        this.active.setEntity(petData.createPet(player));
        this.active.setPetData(petData);
        return true;
    }

    public void disablePet() {
        if(this.active.getEntity() != null) {
            this.active.getEntity().close();
            this.active.setEntity(null);
            this.active.setPetData(null);
        }
    }

}
