package core.intent;

import core.id.CellId;
import core.value.Position2D;

public record MoveIntent(
        CellId cellId,
        Position2D target
) implements CellIntent {}
