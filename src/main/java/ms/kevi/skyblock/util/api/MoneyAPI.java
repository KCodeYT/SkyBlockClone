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

package ms.kevi.skyblock.util.api;

import cn.nukkit.Server;
import ms.kevi.skyblock.game.GameHolder;
import ms.kevi.skyblock.game.bank.Bank;
import ms.kevi.skyblock.game.bank.BankHistory;
import ms.kevi.skyblock.game.player.SkyBlockPlayer;

import java.util.UUID;

public class MoneyAPI {

    public static long getCoins(UUID uniqueId) {
        return GameHolder.getPlayer(uniqueId) != null ? GameHolder.getPlayer(uniqueId).getBank().getCoins() : 0;
    }

    public static void addCoins(UUID uniqueId, long coins) {
        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(uniqueId);
        if(skyBlockPlayer != null) {
            final Bank bank = skyBlockPlayer.getBank();
            bank.setCoins(bank.getCoins() + coins);
            Server.getInstance().getPlayer(uniqueId).ifPresent(ScoreboardAPI::update);
        }
    }

    public static void addBankCoins(String name, UUID uniqueId, long coins) {
        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(uniqueId);
        if(skyBlockPlayer != null) {
            final Bank bank = skyBlockPlayer.getBank();
            bank.setBankedCoins(bank.getBankedCoins() + coins);
            bank.getHistory().addChange(System.currentTimeMillis(), UUID.randomUUID(), name, BankHistory.ChangeType.DEPOSIT, coins);
        }
    }

    public static void removeCoins(UUID uniqueId, long coins) {
        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(uniqueId);
        if(skyBlockPlayer != null) {
            final Bank bank = skyBlockPlayer.getBank();
            bank.setCoins(bank.getCoins() - coins);
            Server.getInstance().getPlayer(uniqueId).ifPresent(ScoreboardAPI::update);
        }
    }

    public static void removeBankCoins(String name, UUID uniqueId, long coins) {
        final SkyBlockPlayer skyBlockPlayer = GameHolder.getPlayer(uniqueId);
        if(skyBlockPlayer != null) {
            final Bank bank = skyBlockPlayer.getBank();
            bank.setBankedCoins(bank.getBankedCoins() - coins);
            bank.getHistory().addChange(System.currentTimeMillis(), UUID.randomUUID(), name, BankHistory.ChangeType.WITHDRAW, coins);
        }
    }

}
