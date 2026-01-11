package core.system.ConflictResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.intent.MoveIntent;
import core.value.Position2D;

public class MoveConflictResolver {

    public List<MoveIntent> resolve(List<MoveIntent> moves) {
        Map<Position2D, MoveIntent> chosen = new HashMap<>();
        
        //this for loop goes through all moves
        //and delete all repitions
        for (MoveIntent m : moves) {
            chosen.putIfAbsent(m.target(), m); // first wins
        }
        return new ArrayList<>(chosen.values());
    }
}