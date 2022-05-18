package ms.kevi.skyblock.registry;

import ms.kevi.skyblock.game.effect.Effect;
import ms.kevi.skyblock.game.effect.defaults.SpeedEffect;
import ms.kevi.skyblock.game.effect.defaults.StrengthEffect;

public class EffectRegistry extends Registry<Effect> {

    public static final Effect STRENGTH = new StrengthEffect();
    public static final Effect SPEED = new SpeedEffect();

}
