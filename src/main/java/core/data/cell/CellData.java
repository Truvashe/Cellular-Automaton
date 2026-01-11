package core.data.cell;

import core.value.Stat;
import core.data.world.WorldData;
import core.id.FoodId;
import core.value.Position2D;

public class CellData {
	Position2D pos;
	Stat energy;
	
	static int numberOfCellsCreated = 0; //to keep an eye on the how many cells have been created
	
	//all cells get to born at 100energy
	public CellData(Position2D pos) {
		this.pos = pos;
		energy = new Stat(100,0,100);
		numberOfCellsCreated++;
	}
	
	// START OF GETTERS AND SETTERS
	public Position2D getPosition() {
		return this.pos;
	}
	public int getEnergy() {
		return this.energy.get();
	}
	public static int getNumberOfCellsCreated() {
		return numberOfCellsCreated;
	}
	
	//END OF GETTERS AND SETTERS
	//assuming new positions are in world (we can check that using isPoseInWorld())
	public boolean moveu() {
		this.pos = this.pos.translate(1, 0);
		return true;
	}
	
	public boolean teleport(Position2D pos) {
		int h = pos.posh();
		int w = pos.posw();
		this.pos = new Position2D(h,w);
		return true;
	}
	
	public boolean energySteal(int ammount) {
		this.energy.subtract(ammount);
		
		return true;
	}
	
	public boolean energyGive(int ammount) {
		this.energy.add(ammount);
		
		return true;
	}
	
	public FoodId seekNearbyFood(WorldData world) {
		Position2D cpos = this.pos;
		
		
		for (int i=cpos.posh()+1;i >= cpos.posh()-1;i-=1) {
			for (int j=cpos.posw()-1;j <= cpos.posw()+1;j++) {
				if (world.getFoodMap().containsKey(new FoodId(i,j))) {
					return new FoodId(i,j);
				}
			}
		}
		return null;
	}
	
}
