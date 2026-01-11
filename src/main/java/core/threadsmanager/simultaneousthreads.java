package core.threadsmanager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import core.data.world.WorldData;
import core.id.CellId;
import core.intent.CellIntent;
import core.system.decision.CellDecisionSystem;

public class simultaneousthreads {
	
	WorldData world;
	
	public simultaneousthreads(WorldData world) {
		this.world = world;
	}

    public List<CellIntent> createandrunallthreads() throws InterruptedException {
    	List<CellIntent> intents = new CopyOnWriteArrayList<>();
        // The latch is what make the threads start at once
        final CountDownLatch latch = new CountDownLatch(1);

        for (CellId id : world.getAllCellIds()) {
            CellDecisionSystem decision = new CellDecisionSystem();
            
            Thread t = new Thread(() -> {
                try {
                    // Each thread waits here until the gate opens
                    latch.await(); 
                    
                    intents.add(decision.decide(id, world));
                    System.out.println(Thread.currentThread().getName() + " has started!");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            t.start();
        }
        // Give a little time for all threads to reach their await() state
        Thread.sleep(100);
        // lunch all threads at once
        latch.countDown();
        return intents;
    }
}
