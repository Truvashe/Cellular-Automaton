package core.intent;

import core.id.CellId;
import core.id.FoodId;

public record EatIntent(
        CellId cellId,
        FoodId foodId
) implements CellIntent {}
