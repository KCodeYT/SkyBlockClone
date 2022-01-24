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

package ms.kevi.skyblock.util;

import cn.nukkit.math.Vector3;

public class VectorUtil {

    public static Vector3 getRelativeToDirection(double yaw, double pitch, double left, double up, double forward) {
        final double var3 = Math.cos((yaw + 90D) * (Math.PI / 180D));
        final double var4 = Math.sin((yaw + 90D) * (Math.PI / 180D));
        final double var5 = Math.cos(-pitch * (Math.PI / 180D));
        final double var6 = Math.sin(-pitch * (Math.PI / 180D));
        final double var7 = Math.cos((-pitch + 90D) * (Math.PI / 180D));
        final double var8 = Math.sin((-pitch + 90D) * (Math.PI / 180D));
        final Vector3 var9 = new Vector3(var3 * var5, var6, var4 * var5);
        final Vector3 var10 = new Vector3(var3 * var7, var8, var4 * var7);
        final Vector3 var11 = var9.cross(var10).multiply(-1D);
        return new Vector3(
                var9.x * forward + var10.x * up + var11.x * left,
                var9.y * forward + var10.y * up + var11.y * left,
                var9.z * forward + var10.z * up + var11.z * left
        );
    }

}
