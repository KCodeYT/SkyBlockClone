package ms.kevi.skyblock.game.effect;

import ms.kevi.skyblock.game.booster.StatsBooster;

import java.util.*;

public class Effects {

    private final List<EffectInstance> effects = new ArrayList<>();

    public void tick() {
        final Iterator<EffectInstance> iterator = this.effects.iterator();
        while(iterator.hasNext()) {
            final EffectInstance effect = iterator.next();
            final int currentDuration = effect.getDuration();

            if(currentDuration > 0)
                effect.setDuration(currentDuration - 1);
            else iterator.remove();
        }
    }

    public StatsBooster apply() {
        final Set<StatsBooster> boosters = new LinkedHashSet<>();
        for(EffectInstance effect : this.effects) {
            boosters.add(effect.getType().getStatsBooster(effect.getAmplifier()));
        }

        return StatsBooster.combine(boosters);
    }

    public void add(Effect type, int duration, int amplifier) {
        this.effects.add(new EffectInstance(type, duration, amplifier));
    }

    public void remove(Effect type) {
        this.effects.removeIf(effect -> effect.getType() == type);
    }

    public void removeAll() {
        this.effects.clear();
    }

}
