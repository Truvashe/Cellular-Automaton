package core.data.chunk;

import java.util.List;

import core.id.ChunkId;
import core.id.CellId;
import core.id.FoodId;

public class ChunkData {
	ChunkId Id;
	List<CellId> L_CellId;
	List<FoodId> L_FoodId;
	
	public ChunkData(ChunkId Id, List<CellId> L_CellId, List<FoodId> L_FoodPos)
	{
		this.Id = Id;
		this.L_CellId = L_CellId;
		this.L_FoodId = L_FoodPos;
	}
	
	//getters and setters
	public List<CellId> getAllCellId(){
		return this.L_CellId;
	}
	
	public List<FoodId> getAllFoodId(){
		return this.L_FoodId;
	}
}