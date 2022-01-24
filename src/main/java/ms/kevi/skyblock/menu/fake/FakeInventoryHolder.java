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

package ms.kevi.skyblock.menu.fake;

import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.math.BlockVector3;
import cn.nukkit.math.Vector3;
import lombok.Getter;

@Getter
class FakeInventoryHolder extends Vector3 implements InventoryHolder {

    private FakeChestMenu inventory;

    void init(FakeChestMenu inventory) {
        final BlockVector3 vector3 = inventory.getBlockVectors().get(0);
        this.x = vector3.x;
        this.y = vector3.y;
        this.z = vector3.z;
        this.inventory = inventory;
    }

}
