package core.data.food;

import core.value.Position2D;

public class FoodData {
	//value is for the value of energy it give the cell
	Position2D pos;
	int value;
	
	public FoodData(Position2D pos,int value) {
		this.pos = pos;
		this.value = value;
	}
	
	
	//GETTERS AND SETTERS
	public Position2D getPos() {
		return this.pos;
	}
	public int getValue() {
		return this.value;
	}
}
