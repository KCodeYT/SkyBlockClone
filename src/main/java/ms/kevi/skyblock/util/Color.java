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

import lombok.Getter;

@Getter
public class Color {

    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color DARK_BLUE = new Color(0, 0, 170);
    public static final Color DARK_GREEN = new Color(0, 170, 0);
    public static final Color DARK_AQUA = new Color(0, 170, 170);
    public static final Color DARK_RED = new Color(170, 0, 0);
    public static final Color DARK_PURPLE = new Color(170, 0, 170);
    public static final Color ORANGE = new Color(255, 170, 0);
    public static final Color GRAY = new Color(170, 170, 170);
    public static final Color DARK_GRAY = new Color(85, 85, 85);
    public static final Color BLUE = new Color(85, 85, 255);
    public static final Color GREEN = new Color(85, 255, 85);
    public static final Color AQUA = new Color(85, 255, 255);
    public static final Color RED = new Color(255, 85, 85);
    public static final Color LIGHT_PURPLE = new Color(255, 85, 255);
    public static final Color YELLOW = new Color(255, 255, 85);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color GOLD = new Color(221, 214, 5);

    private final int red;
    private final int green;
    private final int blue;
    private final int alpha;

    public Color() {
        this(0, 0, 0);
    }

    public Color(String colorString) {
        this(Integer.parseInt(colorString.split(":")[0]),
                Integer.parseInt(colorString.split(":")[1]),
                Integer.parseInt(colorString.split(":")[2]),
                colorString.split(":").length > 3 ?
                        Integer.parseInt(colorString.split(":")[3]) :
                        255);
    }

    public Color(int red, int green, int blue) {
        this(red, green, blue, 255);
    }

    public Color(int red, int green, int blue, int alpha) {
        this.red = (red & 0xFF);
        this.green = (green & 0xFF);
        this.blue = (blue & 0xFF);
        this.alpha = (alpha & 0xFF);
    }

    public static Color fromHex(String hexCode) {
        if(hexCode.startsWith("#"))
            hexCode = hexCode.substring(1);
        return new Color(Integer.valueOf(hexCode.substring(0, 2), 16), Integer.valueOf(hexCode.substring(2, 4), 16), Integer.valueOf(hexCode.substring(4, 6), 16));
    }

    public int toARGB() {
        return (this.alpha << 24) | (this.red << 16) | (this.green << 8) | this.blue;
    }

    public int toRGBA() {
        return (this.red << 24) | (this.green << 16) | (this.blue << 8) | this.alpha;
    }

}
