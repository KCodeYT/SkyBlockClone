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

import ms.kevi.skyblock.item.ability.*;

public class ItemAbilityRegistry extends Registry<IAbility> {

    public static final IAbility SUPERIOR_BLOOD = new SuperiorBloodAbility();
    public static final IAbility WISE_BLOOD = new WiseBloodAbility();
    public static final IAbility INSTANT_TRANSMISSION = new InstantTransmissionAbility();
    public static final IAbility WITHERBORN = new WitherbornAbility();
    public static final IAbility SHOWTIME = new ShowtimeAbility();

}
