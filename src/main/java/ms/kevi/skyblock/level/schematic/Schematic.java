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

package ms.kevi.skyblock.level.schematic;

import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySpawnable;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.BlockVector3;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.stream.NBTInputStream;
import cn.nukkit.nbt.stream.NBTOutputStream;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.BinaryStream;
import ms.kevi.skyblock.scheduler.TaskExecutor;
import ms.kevi.skyblock.util.DoubleValue;

import java.io.*;
import java.util.*;

public class Schematic {

    private final Map<Vector3, Block> blocks;
    private final List<SchematicBlockEntity> blockEntities;
    private final List<SchematicEntity> entities;

    public Schematic(File file) {
        this.blocks = new HashMap<>();
        this.blockEntities = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.parseFile(file);
    }

    public Schematic() {
        this.blocks = new HashMap<>();
        this.blockEntities = new ArrayList<>();
        this.entities = new ArrayList<>();
    }

    private void parseFile(File file) {
        try {
            final FileInputStream fileInputStream = new FileInputStream(file);

            int available;
            while((available = fileInputStream.available()) > 0) {
                byte[] bytes = new byte[available];
                bytes = Arrays.copyOf(bytes, fileInputStream.read(bytes));
                final BinaryStream binaryStream = new BinaryStream(bytes);
                final int blocks = binaryStream.getVarInt();
                for(int i = 0; i < blocks; i++) {
                    final int blockX = binaryStream.getVarInt();
                    final int blockY = binaryStream.getVarInt();
                    final int blockZ = binaryStream.getVarInt();

                    final int blockId = binaryStream.getVarInt();
                    final int blockMeta = binaryStream.getVarInt();

                    this.addBlock(new Vector3(blockX, blockY, blockZ), Block.get(blockId, blockMeta));
                }

                final int entities = binaryStream.getVarInt();
                for(int i = 0; i < entities; i++) {
                    final float x = binaryStream.getFloat();
                    final float y = binaryStream.getFloat();
                    final float z = binaryStream.getFloat();

                    final String type = binaryStream.getString();

                    final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(binaryStream.getByteArray());
                    final NBTInputStream nbtStream = new NBTInputStream(byteArrayInputStream);
                    final CompoundTag compoundTag = (CompoundTag) CompoundTag.readNamedTag(nbtStream);
                    nbtStream.close();
                    byteArrayInputStream.close();
                    this.addEntity(new Vector3(x, y, z), type, compoundTag);
                }

                final int blockEntities = binaryStream.getVarInt();
                for(int i = 0; i < blockEntities; i++) {
                    final int x = binaryStream.getVarInt();
                    final int y = binaryStream.getVarInt();
                    final int z = binaryStream.getVarInt();

                    final String type = binaryStream.getString();

                    final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(binaryStream.getByteArray());
                    final NBTInputStream nbtStream = new NBTInputStream(byteArrayInputStream);
                    final CompoundTag compoundTag = (CompoundTag) CompoundTag.readNamedTag(nbtStream);
                    nbtStream.close();
                    byteArrayInputStream.close();
                    this.addBlockEntity(new BlockVector3(x, y, z), type, compoundTag);
                }
            }

            fileInputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addBlock(Vector3 vector3, Block block) {
        this.blocks.put(vector3, block);
    }

    public synchronized void addBlockEntity(BlockVector3 blockVector3, String type, CompoundTag compoundTag) {
        this.blockEntities.add(new SchematicBlockEntity(blockVector3, type, compoundTag));
    }

    public synchronized void addEntity(Vector3 vector3, String type, CompoundTag compoundTag) {
        this.entities.add(new SchematicEntity(vector3, type, compoundTag));
    }

    public void buildInstant(Vector3 vector3, Level level, boolean ignoreAir) {
        final int startX = vector3.getFloorX();
        final int startY = vector3.getFloorY();
        final int startZ = vector3.getFloorZ();

        synchronized(this.blocks) {
            final List<BaseFullChunk> chunkList = new ArrayList<>();
            for(Vector3 blockVector : this.blocks.keySet()) {
                final Block block = this.blocks.get(blockVector);

                if(block.getId() == Block.AIR && ignoreAir)
                    continue;

                final int x = startX + blockVector.getFloorX();
                final int y = startY + blockVector.getFloorY();
                final int z = startZ + blockVector.getFloorZ();

                if(y <= 0)
                    continue;

                final BaseFullChunk chunk = level.getChunk(x >> 4, z >> 4, true);
                chunk.setBlock(x & 15, y, z & 15, block.getId(), block.getDamage());
                if(!chunkList.contains(chunk))
                    chunkList.add(chunk);
            }

            for(SchematicEntity schematicEntity : this.entities) {
                final Vector3 entityPosition = schematicEntity.getPosition().add(startX, startY, startZ);
                final BaseFullChunk chunk = level.getChunk(entityPosition.getFloorX() >> 4, entityPosition.getFloorZ() >> 4, true);
                final Entity entity = Entity.createEntity(schematicEntity.getType(), chunk, schematicEntity.getCompoundTag().remove("Pos").putList(Entity.getDefaultNBT(entityPosition).getList("Pos")));
                entity.spawnToAll();
                if(!chunk.getEntities().containsValue(entity))
                    chunk.addEntity(entity);
            }

            for(SchematicBlockEntity schematicBlockEntity : this.blockEntities) {
                final BlockVector3 position = schematicBlockEntity.getPosition().add(startX, startY, startZ);
                final BaseFullChunk chunk = level.getChunk(position.getX() >> 4, position.getZ() >> 4, true);
                final BlockEntity blockEntity = BlockEntity.createBlockEntity(schematicBlockEntity.getType(), chunk, schematicBlockEntity.getCompoundTag().putString("id", schematicBlockEntity.getType()).putInt("x", position.getX()).putInt("y", position.getY()).putInt("z", position.getZ()));
                if(blockEntity instanceof BlockEntitySpawnable)
                    ((BlockEntitySpawnable) blockEntity).spawnToAll();
                if(!chunk.getBlockEntities().containsValue(blockEntity))
                    chunk.addBlockEntity(blockEntity);
            }

            for(BaseFullChunk baseFullChunk : chunkList)
                level.getPlayers().values().forEach(player -> level.requestChunk(baseFullChunk.getX(), baseFullChunk.getZ(), player));
        }
    }

    public void buildAnimated(Vector3 vector3, Level level, boolean ignoreAir) {
        final int startX = vector3.getFloorX();
        final int startY = vector3.getFloorY();
        final int startZ = vector3.getFloorZ();

        synchronized(this.blocks) {
            final Map<Integer, List<DoubleValue<Vector3, Block>>> tasksMap = new HashMap<>();
            int longestDelay = 0;
            for(Vector3 blockVector : this.blocks.keySet()) {
                final Block block = this.blocks.get(blockVector);

                if(block.getId() == Block.AIR && ignoreAir)
                    continue;

                final int x = startX + blockVector.getFloorX();
                final int y = startY + blockVector.getFloorY();
                final int z = startZ + blockVector.getFloorZ();

                if(y <= 0)
                    continue;

                final int delay = block.getId() == Block.AIR ? 0 : (blockVector.getFloorY() < 0 ? blockVector.getFloorY() * -1 : blockVector.getFloorY()) * 2;
                if(delay > longestDelay)
                    longestDelay = delay;
                tasksMap.putIfAbsent(delay, new ArrayList<>());
                tasksMap.get(delay).add(new DoubleValue<>(new Vector3(x, y, z), block));
                TaskExecutor.delayed(() -> level.setBlock(new Vector3(x, y, z), block, false, false), delay);
            }

            int finalDelay = longestDelay;

            for(Integer delay : tasksMap.keySet()) {
                final List<DoubleValue<Vector3, Block>> blockList = tasksMap.get(delay);
                TaskExecutor.delayed(() -> {
                    for(DoubleValue<Vector3, Block> blockDoubleValue : blockList)
                        level.setBlock(blockDoubleValue.getValue1(), blockDoubleValue.getValue2());
                    if(delay == finalDelay) {
                        for(SchematicEntity schematicEntity : this.entities) {
                            final Vector3 entityPosition = schematicEntity.getPosition().add(startX, startY, startZ);
                            final BaseFullChunk chunk = level.getChunk(entityPosition.getFloorX() >> 4, entityPosition.getFloorZ() >> 4, true);
                            Entity.createEntity(schematicEntity.getType(), chunk, schematicEntity.getCompoundTag().remove("Pos").putList(Entity.getDefaultNBT(entityPosition).getList("Pos"))).spawnToAll();
                        }

                        for(SchematicBlockEntity schematicBlockEntity : this.blockEntities) {
                            final BlockVector3 position = schematicBlockEntity.getPosition().add(startX, startY, startZ);
                            final BaseFullChunk chunk = level.getChunk(position.getX() >> 4, position.getZ() >> 4, true);
                            final BlockEntity blockEntity = BlockEntity.createBlockEntity(schematicBlockEntity.getType(), chunk, schematicBlockEntity.getCompoundTag().putString("id", schematicBlockEntity.getType()).putInt("x", position.getX()).putInt("y", position.getY()).putInt("z", position.getZ()));
                            if(blockEntity instanceof BlockEntitySpawnable)
                                ((BlockEntitySpawnable) blockEntity).spawnToAll();
                        }
                    }
                }, delay);
            }
        }
    }

    public void save(File file) {
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(file);

            synchronized(this.blocks) {
                final BinaryStream binaryStream = new BinaryStream();
                binaryStream.putVarInt(this.blocks.size());
                for(Vector3 blockVector : this.blocks.keySet()) {
                    Block block = this.blocks.get(blockVector);

                    binaryStream.putVarInt(blockVector.getFloorX());
                    binaryStream.putVarInt(blockVector.getFloorY());
                    binaryStream.putVarInt(blockVector.getFloorZ());

                    binaryStream.putVarInt(block.getId());
                    binaryStream.putVarInt(block.getDamage());
                }

                binaryStream.putVarInt(this.entities.size());
                for(SchematicEntity schematicEntity : this.entities) {
                    binaryStream.putFloat((float) schematicEntity.getPosition().getX());
                    binaryStream.putFloat((float) schematicEntity.getPosition().getY());
                    binaryStream.putFloat((float) schematicEntity.getPosition().getZ());

                    binaryStream.putString(schematicEntity.getType());

                    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    final NBTOutputStream nbtStream = new NBTOutputStream(byteArrayOutputStream);
                    CompoundTag.writeNamedTag(schematicEntity.getCompoundTag(), nbtStream);
                    byteArrayOutputStream.flush();
                    binaryStream.putByteArray(byteArrayOutputStream.toByteArray());
                    byteArrayOutputStream.close();
                }

                binaryStream.putVarInt(this.blockEntities.size());
                for(SchematicBlockEntity schematicBlockEntity : this.blockEntities) {
                    binaryStream.putVarInt(schematicBlockEntity.getPosition().getX());
                    binaryStream.putVarInt(schematicBlockEntity.getPosition().getY());
                    binaryStream.putVarInt(schematicBlockEntity.getPosition().getZ());

                    binaryStream.putString(schematicBlockEntity.getType());

                    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    final NBTOutputStream nbtStream = new NBTOutputStream(byteArrayOutputStream);
                    CompoundTag.writeNamedTag(schematicBlockEntity.getCompoundTag(), nbtStream);
                    byteArrayOutputStream.flush();
                    binaryStream.putByteArray(byteArrayOutputStream.toByteArray());
                    byteArrayOutputStream.close();
                }

                fileOutputStream.write(binaryStream.getBuffer());
            }

            fileOutputStream.flush();
            fileOutputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
