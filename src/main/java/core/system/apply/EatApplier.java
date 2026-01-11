package core.system.apply;

import core.data.cell.CellData;
import core.data.chunk.ChunkData;
import core.data.food.FoodData;
import core.data.world.WorldData;
import core.intent.EatIntent;

public class EatApplier {

    public void apply(EatIntent intent, WorldData world) {
        CellData cell = world.getCellData(intent.cellId());
        FoodData food = world.getFoodMap().get(intent.foodId());
        ChunkData chunk = world.getChunkFromId(world.getChunkFromPos(cell.getPosition()));

        int taken = food.getValue();

        cell.energyGive(taken);		//minus because energy steal remove energy
        								
        //removing the food from both chunk and world
        chunk.getAllFoodId().remove(intent.foodId());
        world.getFoodMap().remove(intent.foodId());
    }
}
