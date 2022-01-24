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

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.nbt.stream.FastByteArrayOutputStream;
import cn.nukkit.utils.SerializedImage;
import com.google.gson.Gson;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SkinUtil {

    static final BufferedImage EMPTY = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
    private static final Gson GSON = new Gson();

    private static final Map<?, ?> MAP_GEOMETRY_CUSTOM = GSON.fromJson(Skin.GEOMETRY_CUSTOM, Map.class);
    private static final Map<?, ?> MAP_GEOMETRY_CUSTOM_SLIM = GSON.fromJson(Skin.GEOMETRY_CUSTOM_SLIM, Map.class);

    public static final String SKULL_GEOMETRY_NAME = "geometry.custom.skull";
    public static final String SKULL_GEOMETRY = "{\"format_version\":\"1.12.0\",\"minecraft:geometry\":[{\"description\":{\"identifier\":\"" + SKULL_GEOMETRY_NAME + "\",\"texture_width\":64,\"texture_height\":64,\"visible_bounds_width\":2,\"visible_bounds_height\":1,\"visible_bounds_offset\":[0,1,0]},\"bones\":[{\"name\":\"root\",\"pivot\":[0,0,0]},{\"name\":\"waist\",\"parent\":\"root\",\"pivot\":[0,12,0]},{\"name\":\"body\",\"parent\":\"waist\",\"pivot\":[0,24,0]},{\"name\":\"head\",\"parent\":\"body\",\"pivot\":[0,24,0],\"cubes\":[{\"origin\":[-4,0,-4],\"size\":[8,8,8],\"uv\":[0,0]}]},{\"name\":\"hat\",\"parent\":\"head\",\"pivot\":[0,24,0],\"cubes\":[{\"origin\":[-4,0,-4],\"size\":[8,8,8],\"inflate\":0.25,\"uv\":[32,0]}]},{\"name\":\"cape\",\"parent\":\"body\",\"pivot\":[0,24,3]},{\"name\":\"leftArm\",\"parent\":\"body\",\"pivot\":[5,22,0]},{\"name\":\"leftSleeve\",\"parent\":\"leftArm\",\"pivot\":[5,22,0]},{\"name\":\"leftItem\",\"parent\":\"leftArm\",\"pivot\":[6,15,1]},{\"name\":\"rightArm\",\"parent\":\"body\",\"pivot\":[-5,22,0]},{\"name\":\"rightSleeve\",\"parent\":\"rightArm\",\"pivot\":[-5,22,0]},{\"name\":\"rightItem\",\"parent\":\"rightArm\",\"pivot\":[-6,15,1]},{\"name\":\"jacket\",\"parent\":\"body\",\"pivot\":[0,24,0]},{\"name\":\"leftLeg\",\"parent\":\"root\",\"pivot\":[1.9,12,0]},{\"name\":\"leftPants\",\"parent\":\"leftLeg\",\"pivot\":[1.9,12,0]},{\"name\":\"rightLeg\",\"parent\":\"root\",\"pivot\":[-1.9,12,0]},{\"name\":\"rightPants\",\"parent\":\"rightLeg\",\"pivot\":[-1.9,12,0]}]}]}";

    private static final String GEOMETRY_NAME = "geometry.custom.playerSkull";
    private static final String GEOMETRY = "{\"format_version\":\"1.12.0\",\"minecraft:geometry\":[{\"description\":{\"identifier\":\"" + GEOMETRY_NAME + "\",\"visible_bounds_width\":1,\"visible_bounds_height\":2,\"visible_bounds_offset\":[0,1,0],\"texture_width\":64,\"texture_height\":64},\"bones\":[{\"name\":\"root\",\"pivot\":[0.0,0.0,0.0]},{\"name\":\"body\",\"parent\":\"waist\",\"pivot\":[0.0,24.0,0.0],\"cubes\":[{\"origin\":[-4.0,12.0,-2.0],\"size\":[8,12,4],\"uv\":[16,16]}]},{\"name\":\"waist\",\"parent\":\"root\",\"pivot\":[0.0,12.0,0.0]},{\"name\":\"head\",\"parent\":\"body\",\"pivot\":[0.0,24.0,0.0],\"cubes\":[{\"origin\":[-4.0,24.6,-4.0],\"size\":[8,8,8],\"uv\":[0,0],\"inflate\":1.01}]},{\"name\":\"cape\",\"pivot\":[0.0,24,3.0],\"parent\":\"body\"},{\"name\":\"hat\",\"parent\":\"head\",\"pivot\":[0.0,24.0,0.0],\"cubes\":[{\"origin\":[-4.0,24.6,-4.0],\"size\":[8,8,8],\"uv\":[32,0],\"inflate\":1.51}]},{\"name\":\"leftArm\",\"parent\":\"body\",\"pivot\":[5.0,22.0,0.0],\"cubes\":[{\"origin\":[4.0,12.0,-2.0],\"size\":[4,12,4],\"uv\":[32,48]}]},{\"name\":\"leftSleeve\",\"parent\":\"leftArm\",\"pivot\":[5.0,22.0,0.0],\"cubes\":[{\"origin\":[4.0,12.0,-2.0],\"size\":[4,12,4],\"uv\":[48,48],\"inflate\":0.25}]},{\"name\":\"leftItem\",\"pivot\":[6.0,15.0,1.0],\"parent\":\"leftArm\"},{\"name\":\"rightArm\",\"parent\":\"body\",\"pivot\":[-5.0,22.0,0.0],\"cubes\":[{\"origin\":[-8.0,12.0,-2.0],\"size\":[4,12,4],\"uv\":[40,16]}]},{\"name\":\"rightSleeve\",\"parent\":\"rightArm\",\"pivot\":[-5.0,22.0,0.0],\"cubes\":[{\"origin\":[-8.0,12.0,-2.0],\"size\":[4,12,4],\"uv\":[40,32],\"inflate\":0.25}]},{\"name\":\"rightItem\",\"pivot\":[-6,15,1],\"locators\":{\"lead_hold\":[-6,15,1]},\"parent\":\"rightArm\"},{\"name\":\"leftLeg\",\"parent\":\"root\",\"pivot\":[1.9,12.0,0.0],\"cubes\":[{\"origin\":[-0.1,0.0,-2.0],\"size\":[4,12,4],\"uv\":[16,48]}]},{\"name\":\"leftPants\",\"parent\":\"leftLeg\",\"pivot\":[1.9,12.0,0.0],\"cubes\":[{\"origin\":[-0.1,0.0,-2.0],\"size\":[4,12,4],\"uv\":[0,48],\"inflate\":0.25}]},{\"name\":\"rightLeg\",\"parent\":\"root\",\"pivot\":[-1.9,12.0,0.0],\"cubes\":[{\"origin\":[-3.9,0.0,-2.0],\"size\":[4,12,4],\"uv\":[0,16]}]},{\"name\":\"rightPants\",\"parent\":\"rightLeg\",\"pivot\":[-1.9,12.0,0.0],\"cubes\":[{\"origin\":[-3.9,0.0,-2.0],\"size\":[4,12,4],\"uv\":[0,32],\"inflate\":0.25}]},{\"name\":\"jacket\",\"parent\":\"body\",\"pivot\":[0.0,24.0,0.0],\"cubes\":[{\"origin\":[-4.0,12.0,-2.0],\"size\":[8,12,4],\"uv\":[16,32],\"inflate\":0.25}]}]}]}";

    private static final String GEOMETRY_SLIM_NAME = "geometry.custom.playerSkullSlim";
    private static final String GEOMETRY_SLIM = "{\"format_version\":\"1.12.0\",\"minecraft:geometry\":[{\"description\":{\"identifier\":\"" + GEOMETRY_SLIM_NAME + "\",\"visible_bounds_width\":1,\"visible_bounds_height\":2,\"visible_bounds_offset\":[0,1,0],\"texture_width\":64,\"texture_height\":64},\"bones\":[{\"name\":\"root\",\"pivot\":[0.0,0.0,0.0]},{\"name\":\"waist\",\"parent\":\"root\",\"pivot\":[0.0,12.0,0.0]},{\"name\":\"body\",\"parent\":\"waist\",\"pivot\":[0.0,24.0,0.0],\"cubes\":[{\"origin\":[-4.0,12.0,-2.0],\"size\":[8,12,4],\"uv\":[16,16]}]},{\"name\":\"head\",\"parent\":\"body\",\"pivot\":[0.0,24.0,0.0],\"cubes\":[{\"origin\":[-4.0,24.6,-4.0],\"size\":[8,8,8],\"uv\":[0,0],\"inflate\":1.01}]},{\"name\":\"hat\",\"parent\":\"head\",\"pivot\":[0.0,24.0,0.0],\"cubes\":[{\"origin\":[-4.0,24.6,-4.0],\"size\":[8,8,8],\"uv\":[32,0],\"inflate\":1.51}]},{\"name\":\"rightLeg\",\"parent\":\"root\",\"pivot\":[-1.9,12.0,0.0],\"cubes\":[{\"origin\":[-3.9,0.0,-2.0],\"size\":[4,12,4],\"uv\":[0,16]}]},{\"name\":\"rightPants\",\"parent\":\"rightLeg\",\"pivot\":[-1.9,12.0,0.0],\"cubes\":[{\"origin\":[-3.9,0.0,-2.0],\"size\":[4,12,4],\"uv\":[0,32],\"inflate\":0.25}]},{\"name\":\"leftLeg\",\"parent\":\"root\",\"pivot\":[1.9,12.0,0.0],\"cubes\":[{\"origin\":[-0.1,0.0,-2.0],\"size\":[4,12,4],\"uv\":[0,16]}],\"mirror\":true},{\"name\":\"leftPants\",\"parent\":\"leftLeg\",\"pivot\":[1.9,12.0,0.0],\"cubes\":[{\"origin\":[-0.1,0.0,-2.0],\"size\":[4,12,4],\"uv\":[0,48],\"inflate\":0.25}]},{\"name\":\"leftArm\",\"parent\":\"body\",\"pivot\":[5.0,21.5,0.0],\"cubes\":[{\"origin\":[4.0,11.5,-2.0],\"size\":[3,12,4],\"uv\":[32,48]}]},{\"name\":\"leftSleeve\",\"parent\":\"leftArm\",\"pivot\":[5.0,21.5,0.0],\"cubes\":[{\"origin\":[4.0,11.5,-2.0],\"size\":[3,12,4],\"uv\":[48,48],\"inflate\":0.25}]},{\"name\":\"leftItem\",\"pivot\":[6,14.5,1],\"parent\":\"leftArm\"},{\"name\":\"rightArm\",\"parent\":\"body\",\"pivot\":[-5.0,21.5,0.0],\"cubes\":[{\"origin\":[-7.0,11.5,-2.0],\"size\":[3,12,4],\"uv\":[40,16]}]},{\"name\":\"rightSleeve\",\"parent\":\"rightArm\",\"pivot\":[-5.0,21.5,0.0],\"cubes\":[{\"origin\":[-7.0,11.5,-2.0],\"size\":[3,12,4],\"uv\":[40,32],\"inflate\":0.25}]},{\"name\":\"rightItem\",\"pivot\":[-6,14.5,1],\"locators\":{\"lead_hold\":[-6,14.5,1]},\"parent\":\"rightArm\"},{\"name\":\"jacket\",\"parent\":\"body\",\"pivot\":[0.0,24.0,0.0],\"cubes\":[{\"origin\":[-4.0,12.0,-2.0],\"size\":[8,12,4],\"uv\":[16,32],\"inflate\":0.25}]},{\"name\":\"cape\",\"pivot\":[0.0,24,-3.0],\"parent\":\"body\"}]}]}";

    private static final String HTTPS_MINECRAFT_TEXTURES_URL = "https://textures.minecraft.net/texture/";
    private static final String HTTP_MINECRAFT_TEXTURES_URL = "http://textures.minecraft.net/texture/";

    private static final Map<String, BufferedImage> SKINS = new HashMap<>();
    private static final Map<String, Skin> PLAYER_SKINS = new ConcurrentHashMap<>();

    public static BufferedImage base64Texture(String texture) {
        try {
            return fromTextureId((String) ((Map<?, ?>) ((Map<?, ?>) GSON.fromJson(new String(Base64.getDecoder().decode(texture)), Map.class).get("textures")).get("SKIN")).get("url"));
        } catch(Throwable throwable) {
            return EMPTY;
        }
    }

    public static BufferedImage fromTextureId(String textureId) {
        try {
            if(SKINS.containsKey(textureId))
                return SKINS.get(textureId);
            final String textureUrl;
            if(textureId.startsWith(HTTPS_MINECRAFT_TEXTURES_URL) || textureId.startsWith(HTTP_MINECRAFT_TEXTURES_URL))
                textureUrl = textureId;
            else
                textureUrl = HTTPS_MINECRAFT_TEXTURES_URL + textureId;
            final BufferedImage bufferedImage = ImageIO.read(new URL(textureUrl));
            SKINS.put(textureId, bufferedImage);
            return bufferedImage;
        } catch(Throwable throwable) {
            return EMPTY;
        }
    }

    public static void addSkull(Player player, BufferedImage image) {
        final Skin skin = player.getSkin();
        PLAYER_SKINS.put(player.getName(), cloneSkin(skin));

        final Map<?, ?> resourcePatch = GSON.fromJson(skin.getSkinResourcePatch(), Map.class);

        final boolean slimSkin = resourcePatch.equals(MAP_GEOMETRY_CUSTOM_SLIM);
        if(slimSkin || resourcePatch.equals(MAP_GEOMETRY_CUSTOM)) {
            final BufferedImage oldSkinImage = SkinUtil.asImage(skin.getSkinData());
            final BufferedImage skinImage = new BufferedImage(128, 128, oldSkinImage.getType());
            final Graphics skinImageGraphics = skinImage.getGraphics();
            skinImageGraphics.drawImage(oldSkinImage, 0, 0, 128, 128, null);
            skinImageGraphics.dispose();

            final BufferedImage headImage = new BufferedImage(128, 128, oldSkinImage.getType());
            final Graphics headImageGraphics = headImage.getGraphics();
            headImageGraphics.drawImage(image, 0, 0, 128, 128, null);
            headImageGraphics.dispose();

            ImageUtil.addImage(skinImage, headImage, 0, 0, 128, 32);

            final Skin skin0 = new Skin();
            skin0.setSkinId(UUID.nameUUIDFromBytes(fromImage(skinImage)).toString());
            skin0.setSkinData(skinImage);
            skin0.setGeometryData(slimSkin ? GEOMETRY_SLIM : GEOMETRY);
            skin0.setGeometryName(slimSkin ? GEOMETRY_SLIM_NAME : GEOMETRY_NAME);
            skin0.setCapeData(skin.getCapeData());
            updateSkin(player, skin0);
        }
    }

    public static void removeSkull(Player player) {
        final Skin skin = PLAYER_SKINS.get(player.getName());
        if(skin != null) updateSkin(player, skin);
    }

    private static BufferedImage asImage(SerializedImage image) {
        final BufferedImage bufferedImage = new BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_ARGB);
        for(int index = 0, y = 0; y < bufferedImage.getHeight(); y++)
            for(int x = 0; x < bufferedImage.getWidth(); x++, index += 4)
                bufferedImage.setRGB(x, y, ((image.data[index + 3] & 0xFF) << 24) | ((image.data[index] & 0xFF) << 16) | ((image.data[index + 1] & 0xFF) << 8) | (image.data[index + 2] & 0xFF));
        return bufferedImage;
    }

    public static byte[] fromImage(BufferedImage bufferedImage) {
        try(final FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream()) {
            for(int y = 0; y < bufferedImage.getHeight(); ++y) {
                for(int x = 0; x < bufferedImage.getWidth(); ++x) {
                    final java.awt.Color color = new java.awt.Color(bufferedImage.getRGB(x, y), true);
                    outputStream.write(color.getRed());
                    outputStream.write(color.getGreen());
                    outputStream.write(color.getBlue());
                    outputStream.write(color.getAlpha());
                }
            }

            return outputStream.toByteArray();
        } catch(IOException e) {
            e.printStackTrace();
            return SerializedImage.EMPTY.data;
        }
    }

    private static void updateSkin(EntityHuman entityHuman, Skin skin) {
        try {
            entityHuman.setSkin(skin);

            final Class<Entity> clazz = Entity.class;
            final Field field = clazz.getDeclaredField("hasSpawned");
            field.setAccessible(true);

            final List<Player> players = new ArrayList<>();
            if(entityHuman instanceof Player)
                players.add((Player) entityHuman);
            for(Object o : ((Map<?, ?>) field.get(entityHuman)).values())
                if(o instanceof Player)
                    players.add((Player) o);

            final Player[] playerArray = players.toArray(new Player[0]);
            entityHuman.getServer().updatePlayerListData(entityHuman.getUniqueId(), entityHuman.getId(), entityHuman instanceof Player ? ((Player) entityHuman).getDisplayName() : "", entityHuman.getSkin(), playerArray);
            if(!(entityHuman instanceof Player))
                entityHuman.getServer().removePlayerListData(entityHuman.getUniqueId(), playerArray);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            Server.getInstance().getLogger().warning("Could not change skin for entity " + entityHuman.getName() + ":" + entityHuman.getUniqueId(), e);
        }
    }

    private static Skin cloneSkin(Skin skin) {
        final Skin newSkin = new Skin();
        newSkin.setSkinId(skin.getSkinId());
        newSkin.setSkinData(skin.getSkinData());
        newSkin.setSkinResourcePatch(skin.getSkinResourcePatch());
        newSkin.setGeometryData(skin.getGeometryData());
        newSkin.setAnimationData(skin.getAnimationData());
        newSkin.setCapeId(skin.getCapeId());
        newSkin.setCapeData(skin.getCapeData());
        return newSkin;
    }

}
