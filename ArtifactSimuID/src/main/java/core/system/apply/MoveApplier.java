package core.system.apply;

import core.data.cell.CellData;
import core.data.world.WorldData;
import core.id.ChunkId;
import core.intent.MoveIntent;
import core.value.Position2D;

public class MoveApplier {

    public void apply(MoveIntent intent, WorldData world) {
        CellData cell = world.getCellData(intent.cellId());

        Position2D from = cell.getPosition();
        Position2D to   = intent.target();

        ChunkId oldChunk = world.getChunkFromPos(from);
        ChunkId newChunk = world.getChunkFromPos(to);

        // update position
        cell.teleport(to);

        // update chunk membership if needed
        if (!oldChunk.equals(newChunk)) {
            world.getChunkFromId(oldChunk).getAllCellId().remove(intent.cellId());
            world.getChunkFromId(newChunk).getAllCellId().add(intent.cellId());
        }
    }
}
