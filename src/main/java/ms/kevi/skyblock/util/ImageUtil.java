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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

public class ImageUtil {

    private static final int TYPE = BufferedImage.TYPE_INT_ARGB;

    public static BufferedImage readImage(File file) {
        try {
            return ImageIO.read(file);
        } catch(IOException e) {
            return new BufferedImage(0, 0, TYPE);
        }
    }

    public static BufferedImage readImage(String url) {
        try {
            return readImage(new URL(url));
        } catch(IOException e) {
            return new BufferedImage(0, 0, TYPE);
        }
    }

    public static BufferedImage readImage(URL url) {
        try {
            return ImageIO.read(url);
        } catch(IOException e) {
            return new BufferedImage(0, 0, TYPE);
        }
    }

    public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        return resizeImage(image, width, height, 0, 0);
    }

    public static BufferedImage resizeImage(BufferedImage image, int width, int height, int appendX, int appendY) {
        BufferedImage bufferedImage = new BufferedImage(width, height, image.getType());
        for(int x = 0; x < image.getWidth(); x++)
            for(int y = 0; y < image.getHeight(); y++)
                bufferedImage.setRGB(x + appendX, y + appendY, image.getRGB(x, y));
        return bufferedImage;
    }

    public static void addImage(BufferedImage originalImage, BufferedImage image, int startX, int startY, int endX, int endY) {
        for(int x = 0; x < originalImage.getWidth(); x++) {
            for(int y = 0; y < originalImage.getHeight(); y++) {
                if(x >= startX && x < endX && y >= startY && y < endY)
                    originalImage.setRGB(x, y, image.getRGB(x, y));
            }
        }
    }

    public static void removePart(BufferedImage originalImage, int startX, int startY, int endX, int endY) {
        for(int x = 0; x < originalImage.getWidth(); x++) {
            for(int y = 0; y < originalImage.getHeight(); y++) {
                if(x >= startX && x < endX && y >= startY && y < endY)
                    originalImage.setRGB(x, y, 0);
            }
        }
    }

    public static BufferedImage base64Decode(String imageBase64) {
        byte[] imageData = Base64.getDecoder().decode(imageBase64);
        int imageWidth = ((imageData[0] & 0xFF) << 8 | (imageData[1] & 0xFF));
        int imageHeight = ((imageData[2] & 0xFF) << 8 | (imageData[3] & 0xFF));
        int cursor = 4;

        BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight, TYPE);

        for(int y = 0; y < bufferedImage.getHeight(); y++) {
            for(int x = 0; x < bufferedImage.getWidth(); x++) {
                byte r = imageData[cursor++];
                byte g = imageData[cursor++];
                byte b = imageData[cursor++];
                byte a = imageData[cursor++];

                bufferedImage.setRGB(x, y, ((a & 0xFF) << 24) |
                        ((r & 0xFF) << 16) |
                        ((g & 0xFF) << 8) |
                        (b & 0xFF));
            }
        }

        return bufferedImage;
    }

    public static String base64Encode(BufferedImage bufferedImage) {
        byte[] imageData = new byte[bufferedImage.getWidth() * bufferedImage.getHeight() * 4 + 4];
        imageData[0] = (byte) ((bufferedImage.getWidth() >> 8) & 0xFF);
        imageData[1] = (byte) (bufferedImage.getWidth() & 0xFF);
        imageData[2] = (byte) ((bufferedImage.getHeight() >> 8) & 0xFF);
        imageData[3] = (byte) (bufferedImage.getHeight() & 0xFF);
        int cursor = 4;

        for(int y = 0; y < bufferedImage.getHeight(); y++) {
            for(int x = 0; x < bufferedImage.getWidth(); x++) {
                int color = bufferedImage.getRGB(x, y);
                imageData[cursor++] = (byte) ((color >> 16) & 0xFF);
                imageData[cursor++] = (byte) ((color >> 8) & 0xFF);
                imageData[cursor++] = (byte) (color & 0xFF);
                imageData[cursor++] = (byte) ((color >> 24) & 0xFF);
            }
        }

        return Base64.getEncoder().encodeToString(imageData);
    }

}
