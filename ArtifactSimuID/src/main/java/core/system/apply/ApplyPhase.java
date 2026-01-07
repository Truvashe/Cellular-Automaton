package core.system.apply;

import java.util.List;

import core.data.world.WorldData;
import core.intent.CellIntent;
import core.system.ConflictResolver.ConflictResolver;

public class ApplyPhase {

    private final IntentApplier applier = new IntentApplier();
    private final ConflictResolver resolver = new ConflictResolver();

    public void applyAll(List<CellIntent> intents, WorldData world) {
    	
    	List<CellIntent> resolvedIntents = resolver.resolve(intents);
    	
        for (CellIntent intent : resolvedIntents) {
            applier.apply(intent, world);
        }
    }
}