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

package ms.kevi.skyblock.util.scoreboard.network;

import cn.nukkit.network.protocol.DataPacket;

public class SetScorePacket extends DataPacket {

    public static final byte TYPE_CHANGE = 0;
    public static final byte TYPE_REMOVE = 1;

    public byte type;

    public ScoreEntry[] entries;

    @Override
    public byte pid() {
        return 0x6c;
    }

    @Override
    public void decode() {
        this.type = (byte) this.getByte();
        this.entries = new ScoreEntry[(int) this.getUnsignedVarInt()];

        for(int index = 0; index < this.entries.length; index++) {
            ScoreEntry entry = new ScoreEntry();
            entry.scoreId = this.getVarLong();
            entry.objectiveName = this.getString();
            entry.score = this.getLInt();
            if(this.type == TYPE_CHANGE) {
                entry.type = (byte) this.getByte();
                switch(entry.type) {
                    case ScoreEntry.TYPE_PLAYER:
                    case ScoreEntry.TYPE_ENTITY:
                        entry.entityUniqueId = this.getEntityUniqueId();
                        break;
                    case ScoreEntry.TYPE_FAKE_PLAYER:
                        entry.displayName = this.getString();
                        break;
                }
            }
            this.entries[index] = entry;
        }
    }

    @Override
    public void encode() {
        this.reset();
        this.putByte(this.type);
        this.putUnsignedVarInt(this.entries.length);
        for(ScoreEntry entry : this.entries) {
            this.putVarLong(entry.scoreId);
            this.putString(entry.objectiveName);
            this.putLInt(entry.score);
            if(this.type == TYPE_CHANGE) {
                this.putByte(entry.type);
                switch(entry.type) {
                    case ScoreEntry.TYPE_PLAYER:
                    case ScoreEntry.TYPE_ENTITY:
                        this.putEntityUniqueId(entry.entityUniqueId);
                        break;
                    case ScoreEntry.TYPE_FAKE_PLAYER:
                        this.putString(entry.displayName);
                        break;
                }
            }
        }
    }

}
