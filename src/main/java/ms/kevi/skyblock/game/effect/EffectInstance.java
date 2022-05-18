package ms.kevi.skyblock.game.effect;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EffectInstance {

    private final Effect type;
    private int duration;
    private int amplifier;

}
