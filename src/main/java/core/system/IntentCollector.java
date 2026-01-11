package core.system;

import java.util.List;
import core.data.world.WorldData;
import core.intent.CellIntent;
import core.threadsmanager.simultaneousthreads;

public class IntentCollector {
    public List<CellIntent> collect(WorldData world) throws InterruptedException {
    	simultaneousthreads cellintents = new simultaneousthreads(world);
        List<CellIntent> intents = cellintents.createandrunallthreads();
        return intents;
    }
}
