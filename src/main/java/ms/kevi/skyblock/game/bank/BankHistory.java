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

package ms.kevi.skyblock.game.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BankHistory {

    private final List<Change> changes = new ArrayList<>();

    public void addChange(long timeStamp, UUID uniqueId, String username, ChangeType changeType, long amount) {
        this.changes.add(new Change(timeStamp, uniqueId, username, changeType, amount));
    }

    public List<Change> getChanges() {
        return Collections.unmodifiableList(this.changes);
    }

    public List<Change> getChangesAfter(long timeStamp) {
        final List<Change> changes = this.getChanges();
        changes.removeIf(change -> change.timeStamp > timeStamp);
        return Collections.unmodifiableList(changes);
    }

    public List<Change> getChangesBefore(long timeStamp) {
        final List<Change> changes = this.getChanges();
        changes.removeIf(change -> change.timeStamp < timeStamp);
        return Collections.unmodifiableList(changes);
    }

    @AllArgsConstructor
    public enum ChangeType {
        DEPOSIT("+"),
        WITHDRAW("-");

        private final String toString;

        @Override
        public String toString() {
            return this.toString;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Change {

        private final long timeStamp;
        private final UUID uniqueId;
        private final String username;
        private final ChangeType changeType;
        private final long amount;

    }

}
