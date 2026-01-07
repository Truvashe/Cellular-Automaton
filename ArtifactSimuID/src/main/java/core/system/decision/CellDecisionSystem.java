package core.system.decision;

import core.data.cell.CellData;
import core.data.world.WorldData;
import core.id.CellId;
import core.id.FoodId;
import core.intent.*;
import core.value.Position2D;

public class CellDecisionSystem {

    public CellIntent decide(CellId id,WorldData world) {
    	CellData cell = world.getCellData(id);

        if (cell.getEnergy() < 100) {
        	FoodId foodfound = cell.seekNearbyFood(world);
        	
        	if (foodfound != null) {
        		return new EatIntent(id, foodfound);
        	}
        }

        // naive movement example
        Position2D pos = cell.getPosition();
        Position2D target = pos.translate(1, 0);

        return new MoveIntent(id, target);
    }
}
