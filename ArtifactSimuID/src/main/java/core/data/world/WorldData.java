package core.data.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.data.cell.CellData;
import core.data.chunk.ChunkData;
import core.data.food.FoodData;
import core.id.CellId;
import core.id.ChunkId;
import core.id.FoodId;
import core.value.Position2D;

public class WorldData {
	protected int height;
	protected int width;
	protected int[] chunkSize = new int[2];
	
	protected int numberOfChunks;
	
	
	protected Map<ChunkId,ChunkData> chunkMap;
	protected Map<CellId,CellData> cellMap = new HashMap<>();
	protected Map<FoodId,FoodData> foodMap = new HashMap<>();
	
	public int getFullChunkSize() {
		return this.chunkSize[0]*this.chunkSize[1];
	}
	
	//constructor initiate chunks
	public WorldData(int h, int w, int ch, int cw) {
		//ensure smallest possible world 10x10
		if (h<10) {
			h = 10;
		}
		if (w<10) {
			w = 10;
		}
		if (ch<10) {
			ch = 10;
		}
		if (cw<10) {
			cw = 10;
		}
		//ensure the world is always divisible by 2
		if (h % 2 != 0) {
			h = h + 1;
			System.out.println("Height of WORLD incremented by 1 due to divisibility by 2");
		}
		if (w % 2 != 0) {
			w = w + 1;
			System.out.println("Width of WORLD incremented by 1 due to divisibility by 2");
		}
		//ensure the chunks are always divisible by 2
		if (ch % 2 != 0) {
			ch = ch + 1;
			System.out.println("Height of CHUNK incremented by 1 due to divisibility by 2");
		}
		if (cw % 2 != 0) {
			cw = cw + 1;
			System.out.println("Width of CHUNK incremented by 1 due to divisibility by 2");
		}
		//ensure the chunks are smaller than the world it self
		if (ch > h) {
			ch = h;
			System.out.println("CHUNK Hight capped to WORLD Hight");
		}
		if (cw > w) {
			cw = w;
			System.out.println("CHUNK Width capped to WORLD Width");
		}
		
		this.height = h;
		this.width  = w;
		this.chunkSize[0] = ch;
		this.chunkSize[1] = cw;
		
		float cinh = h/ch;
		float cinw = w/cw;
		
		int nofcinh = (int) cinh;
		int nofcinw = (int) cinw;

		this.numberOfChunks = nofcinh * nofcinw;
		
		//CREATION OF CHUNKS
		//INITIALIZING CHUNK MAP
		this.chunkMap = new HashMap<>();
		
		//I suspect i need to start my loops at 1 (because distance/surface cannot be 0)
		//Yet when i do that, it give me an error
		//better look at it later
		//after a good look and some tests, I use the 0s as coordinates not as distance or surface measures,
		//so it's all good. The first chunk first "pixel" or "position" is labeled 0 x 0
		for (int i = 0; i < nofcinh; i++) {
			for (int j = 0; j < nofcinw; j++) {
				this.chunkMap.put(new ChunkId(i * ch,j * cw),new ChunkData(new ChunkId(i * ch,j * cw),new ArrayList<>(),new ArrayList<>()));
			}
		}
		
		//CREATION OF CELLS
		//INTIALIZING CELLS MAP
		//this.cellMap = new HashMap<>();
		
		//CREATION OF FOOD
		//INITIALIZING FOOD MAP
		//this.foodMap = new HashMap<>();
	}
	
	//GETTERS AND SETTERS			+============================================================+
	
	public int getHeight(){
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getChunkHSize() {
		return this.chunkSize[0];
	}
	
	public int getChunkWSize() {
		return this.chunkSize[1];
	}
	
	public int getNumberOfChunks() {
		return this.numberOfChunks;
	}
	
	public Map<ChunkId,ChunkData> getChunkMap(){
		return this.chunkMap;
	}
	
	public Map<CellId,CellData> getCellMap(){
		return this.cellMap;
	}
	
	public Map<FoodId,FoodData> getFoodMap(){
		return this.foodMap;
	}
	
	//END OF GETTERS AND SETTERS 	+============================================================+
	
	public ChunkId chunkIdFromPos(int x,int y)
	{
	    int chunkHeight = this.chunkSize[0];
	    int chunkWidth = this.chunkSize[1];
	    
	    // Math.floorDiv ensures that -1 / 16 results in -1, not 0
	    int chunkX = Math.floorDiv(x, chunkHeight) * chunkHeight;
	    int chunkY = Math.floorDiv(y, chunkWidth) * chunkWidth;
	    
	    return new ChunkId(chunkX, chunkY);
	    
	    //THIS WAS MY ORIGINAL CODE :
	    //int chunkHeight = this.chunkSize[0];
	    //int chunkWidth = this.chunkSize[1];
	    //float hRatio = x / chunkHeight;
	    //float wRatio = y / chunkWidth;
	    //ChunkId result = new ChunkId((int)hRatio*chunkHeight,(int)wRatio*chunkWidth);
	    //return result;
	}
	
	//==========================================
	//		CHUNKS
	//==========================================
	
	public ChunkData getChunkFromId(ChunkId id)
	{
		if (this.chunkMap.containsKey(id)) {
			return this.chunkMap.get(id);
		}
		return null;
	}
	
	public ChunkId getChunkFromPos(Position2D pose)
	{
		int h,w;
		
		if (this.isPoseInWorld(pose)) {
			h = pose.posh() % this.getChunkHSize();
			h = pose.posh() - h;
			
			w = pose.posw() % this.getChunkWSize();
			w = pose.posw() - w;
			
			return new ChunkId(h,w);
		}
		
		System.out.println("pose is out of world");
		return null;
	}
	
	public int[] getChunkURCorner(ChunkId id)
	{
		int posx = id.posx() + this.chunkSize[0];
		int posy = id.posy() + this.chunkSize[1];
		
		return new int[] {posx, posy};
	}
	
	public int[] getChunkCenter(ChunkId id)
	{
		int posx = id.posx() + (this.chunkSize[0] / 2);
		int posy = id.posy() + (this.chunkSize[1] / 2);
		
		return new int[] {posx, posy};
	}
	
	public boolean isPoseInChunk(ChunkId id,int[] pose) {
		if (((pose[0] > id.posx()) || (pose[0] < (id.posx() + this.getChunkHSize()))) && ((pose[1] > id.posy()) || (pose[1] < (id.posx() + this.getChunkWSize())))){
			return true;
		}
		return false;
	}
	
	public boolean isPoseInWorld(Position2D pose) {
		int h = pose.posh();
		int w = pose.posw();
		if ((h < 0) || (w < 0)) {
			return false;
		}
		if ((h < this.getHeight()) || (w < this.getWidth())){
			return true;
		}
		return false;
	}
	
	public boolean isChunkReal(ChunkId id) {
		if (this.getChunkMap().containsKey(id))
		{
			return true;
		}
		return false;
	}
	
	//This function get the next valid ID of a chunk while having 
	public ChunkId getChunkFromWrongId(ChunkId wrong_id) {
		Position2D p = new Position2D(wrong_id.posx(),wrong_id.posy());
		int h = p.posh();
		int w = p.posw();
		System.out.println("Getting valid chunk ID from wrong ID");
		
		//if pose is out of world, make it the last pose in world
		if (!this.isPoseInWorld(p)){
			h = this.getHeight();
			w = this.getWidth();
			System.out.println("Position too far, capped to world height and width");
		}
		
		Position2D vp = new Position2D(h,w);
		
		System.out.println("Chunk from pose");
		ChunkId validChunk = getChunkFromPos(vp);
		
		if (isChunkReal(validChunk))
		{
			return validChunk;
		}
		
		return null;
	}
	
	//==========================================
	//		CHUNKS
	//==========================================

	public CellId[] getAllCellIds() {
		return this.chunkMap.values().stream()
			    .flatMap(chunk -> chunk.getAllCellId().stream())
			    .toArray(CellId[]::new);
	}

	public CellData getCellData(CellId id) {
		return this.cellMap.get(id);
	}
	
	public void addCellByPos(CellId id,Position2D pos) {
		ChunkId chunkid = getChunkFromPos(pos);
		ChunkData chunkdata = this.chunkMap.get(chunkid);
		
		CellData celldata = new CellData(pos);
		
		//set the cell in the chunk
		chunkdata.getAllCellId().add(id);
		
		//set the cell in the world
		this.cellMap.put(id, celldata);
	}
	
	//==========================================
	//		FOOD
	//==========================================
	
	//Food ID = position
	
	public List<Position2D> getallfoodpos(){
		List<FoodData> a = new ArrayList<>(this.foodMap.values());
		List<Position2D> b = new ArrayList<>();
		
		for (int i = 0;i < a.size(); i++) {
			b.add(a.get(i).getPos());
		}
		
		return b;
	}
	
	public void deletefood(FoodId id) {
		this.foodMap.remove(id);
	}
	
	public void addFoodByPos(Position2D pos,int value) {
		ChunkId chunkid = getChunkFromPos(pos);
		ChunkData chunkdata = this.chunkMap.get(chunkid);
		
		FoodData fooddata = new FoodData(pos,value);
		
		//set the food in the chunk
		chunkdata.getAllFoodId().add(new FoodId(pos.posh(),pos.posw()));
		
		//set the cell in the world
		this.foodMap.put(new FoodId(pos.posh(),pos.posw()), fooddata);
	}
	
}

