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

package ms.kevi.skyblock.util.tool;

import java.util.ArrayList;
import java.util.List;

public class AnimationTool {

    public static List<String> animateText(String text) {
        final List<String> animations = new ArrayList<>();
        animations.add("§e" + text);
        final char[] chars = text.toCharArray();
        for(int i0 = 0; i0 < chars.length; i0++) {
            final char c0 = chars[i0];
            if(c0 != ' ') {
                final StringBuilder line = new StringBuilder();
                for(int i1 = 0; i1 < chars.length; i1++)
                    line.append(i1 < i0 ? "§f" : i1 > i0 ? "§e" : "§6").append(chars[i1]);
                animations.add(line.toString());
            }
        }
        for(int i = 0; i < 3; i++) {
            animations.add("§f" + text);
            animations.add("§e" + text);
        }
        for(int i = 0; i < 20; i++)
            animations.add("§e" + text);
        return animations;
    }

}
