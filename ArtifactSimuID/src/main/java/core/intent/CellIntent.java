package core.intent;

import core.id.CellId;

public sealed interface CellIntent
        permits MoveIntent, EatIntent, IdleIntent {

    CellId cellId();
}