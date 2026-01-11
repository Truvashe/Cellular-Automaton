package core.system.ConflictResolver;

import java.util.ArrayList;
import java.util.List;

import core.intent.CellIntent;
import core.intent.EatIntent;
import core.intent.IdleIntent;
import core.intent.MoveIntent;

public class ConflictResolver {
	
	public List<CellIntent> resolve(List<CellIntent> intents) {
		List<CellIntent> result = new ArrayList<>();
		
		List<MoveIntent> Move_L = new ArrayList<>();
		List<EatIntent>	Eat_L	= new ArrayList<>();
		
		EatConflictResolver EatResolver = new EatConflictResolver();
		MoveConflictResolver MoveResolver = new MoveConflictResolver();
		
		for (int i = 0;i<intents.size();i++) {
			switch (intents.get(i)) {
            case MoveIntent move -> Move_L.add(move);
            case EatIntent eat  -> Eat_L.add(eat);
            case IdleIntent idle -> { /* no-op */ }
			}
		}
		
		Move_L = MoveResolver.resolve(Move_L);
		Eat_L = EatResolver.resolve(Eat_L);
		
		result.addAll(Move_L);
		result.addAll(Eat_L);
		
		return result;
	}
}
