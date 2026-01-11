package core.system.ConflictResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.id.FoodId;
import core.intent.EatIntent;

public class EatConflictResolver {

    public List<EatIntent> resolve(List<EatIntent> moves) {
        Map<FoodId, EatIntent> chosen = new HashMap<>();
        
        //this for loop goes through all moves
        //and delete all repetitions
        for (EatIntent m : moves) {
            chosen.putIfAbsent(m.foodId(), m); // first wins
        }
        return new ArrayList<>(chosen.values());
    }
}