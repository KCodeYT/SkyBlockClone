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

import ms.kevi.skyblock.game.pet.ability.*;

public class PetAbilities {

    public static final IPetAbility END_STRIKE = new EndStrikePetAbility();
    public static final IPetAbility ONE_WITH_THE_DRAGONS = new OneWithTheDragonsPetAbility();
    public static final IPetAbility SUPERIOR = new SuperiorPetAbility();

    public static final IPetAbility HIVE = new HivePetAbility();
    public static final IPetAbility BUSY_BUZZ_BUZZ = new BusyBuzzBuzzPetAbility();
    public static final IPetAbility WEAPONIZED_HONEY = new WeaponizedHoneyPetAbility();

}
