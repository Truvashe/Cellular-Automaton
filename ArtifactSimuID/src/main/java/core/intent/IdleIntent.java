package core.intent;

import core.id.CellId;

public record IdleIntent( 
        CellId cellId
) implements CellIntent {}
