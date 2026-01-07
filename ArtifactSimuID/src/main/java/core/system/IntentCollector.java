package core.system;

import java.util.ArrayList;
import java.util.List;

import core.data.world.WorldData;
import core.id.CellId;
import core.intent.CellIntent;
import core.system.decision.CellDecisionSystem;

public class IntentCollector {

    private final CellDecisionSystem decisionSystem = new CellDecisionSystem();

    public List<CellIntent> collect(WorldData world) {

        List<CellIntent> intents = new ArrayList<>();

        for (CellId id : world.getAllCellIds()) {
            intents.add(decisionSystem.decide(id, world));
        }

        return intents;
    }
}
