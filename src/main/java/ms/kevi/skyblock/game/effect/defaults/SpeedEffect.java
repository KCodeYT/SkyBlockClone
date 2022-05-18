package ms.kevi.skyblock.game.effect.defaults;

import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.game.effect.Effect;
import ms.kevi.skyblock.game.stats.GameStats;

public class SpeedEffect implements Effect {

    @Override
    public String getDisplayName() {
        return "§9Speed";
    }

    @Override
    public int getMaxLevel() {
        return 8;
    }

    @Override
    public StatsBooster getStatsBooster(int level) {
        return StatsBooster.create().
                put(GameStats.SPEED, 5 * level);
    }

}
