package core.system.apply;

import core.data.world.WorldData;
import core.intent.*;

public class IntentApplier {

    private final MoveApplier moveApplier = new MoveApplier();
    private final EatApplier eatApplier = new EatApplier();

    public void apply(CellIntent intent, WorldData world) {
        switch (intent) {
            case MoveIntent m -> moveApplier.apply(m, world);
            case EatIntent e  -> eatApplier.apply(e, world);
            case IdleIntent i -> { /* no-op */ }
        }
    }
}
