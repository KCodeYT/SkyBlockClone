package ms.kevi.skyblock.game.effect;

import ms.kevi.skyblock.game.booster.StatsBooster;
import ms.kevi.skyblock.registry.Registrable;
import ms.kevi.skyblock.registry.Registries;

public interface Effect extends Registrable {

    String getDisplayName();

    int getMaxLevel();

    StatsBooster getStatsBooster(int level);

    @Override
    default String name() {
        return Registries.EFFECTS.nameOf(this);
    }

}
