package main;

import java.util.List;

import core.data.cell.CellData;
import core.system.IntentCollector;
import core.system.apply.ApplyPhase;
import core.system.apply.IntentApplier;
import core.system.decision.CellDecisionSystem;
import core.data.world.WorldData;
import core.id.CellId;
import core.id.ChunkId;
import core.id.FoodId;
import core.intent.CellIntent;
import core.intent.EatIntent;
import core.intent.IdleIntent;
import core.intent.MoveIntent;
import core.value.Position2D;

public class mainTest {
	
	//functions
	public static void initializingWord(WorldData world) {
		int h = world.getHeight();
		int w = world.getWidth();
		int ch= world.getChunkHSize();
		int cw= world.getChunkWSize();
		
		System.out.print("World initialised with :");
		System.out.println(h + " " + w + " " + ch + " " + cw);
		System.out.println("\n\nTEST ONE : INITIALIZING WORLD");
		System.out.println("\nWorld Height = " + world.getHeight() + "\nWorld Width = " + world.getWidth());
		System.out.println("\nCHUNKS :\nNumber OF Chunks = " + world.getNumberOfChunks() + "\nChunk Height = " + world.getChunkHSize() + "\nChunk Width = " + world.getChunkWSize());
		System.out.println("\nChunk in 0 x 0 test :");
		System.out.println("\nChunk Corner = " + world.getChunkURCorner(new ChunkId(0,0))[0] + " " + world.getChunkURCorner(new ChunkId(0,0))[1] +"\nChunk Center = " + world.getChunkCenter(new ChunkId(0,0))[0] + " " + world.getChunkCenter(new ChunkId(0,0))[1]);
		System.out.println("\nChunk in 1 x 0 test :");
		System.out.println("\nChunk Corner = " + world.getChunkURCorner(new ChunkId(1,0))[0] + " " + world.getChunkURCorner(new ChunkId(1,0))[1] +"\nChunk Center = " + world.getChunkCenter(new ChunkId(1,0))[0] + " " + world.getChunkCenter(new ChunkId(1,0))[1]);
	}
	
	public static void invalidChunk(WorldData world, int h, int w) {
		System.out.println("\n\nTEST TWO : INVALID CHUNK");
		ChunkId wc = new ChunkId(h,w);
		ChunkId vc = null;
		//vc stands for valid chunk btw
		
		System.out.println("Chunk ID : " + wc.posx() + "x" + wc.posy());
		if (!world.isChunkReal(wc)){
			System.out.println("IS NOT REAL");
			vc = world.getChunkFromWrongId(wc);
		}
		else {
			vc = wc;
		}
		
		System.out.println("Wrong Chunk had been corrected and or validated all tests unless vs is null");
		System.out.println("Valid Chunk : " + vc.posx() + "x" + vc.posy());
	}
	
	public static void cellsfound(WorldData world) {
		System.out.println("\n\nStart OF CELLS FOUND\n\n");
		CellId[] c = world.getAllCellIds();
		
		System.out.println("Cells Created : " + CellData.getNumberOfCellsCreated());
		System.out.println("There is over : " + c.length + " Cells in this world\n\n"); 
		
		for (int i = 0; i < c.length;i ++) {
			CellData d = world.getCellData(c[i]);
			System.out.println("Cell ID: " + c[i].Id() + "\nPosition : " + d.getPosition().posh() + " x " + d.getPosition().posw() +"\nEnergy : " + d.getEnergy());
		}
		
		System.out.println("\n\nEND OF CELLS FOUND");
	}
	
	public static void movecell(WorldData world,CellId id) {
		System.out.println("\n\nMOVING CELL TEST");
		System.out.println("Cell ID: " + id.Id());
		world.getCellMap().get(id).moveu();
		System.out.println("Had moved up by 1");
	}
	
	public static void damagecell(WorldData world,CellId id,int damage) {
		System.out.println("\n\nDAMAGING CELL TEST");
		System.out.println("Cell ID: " + id.Id());
		world.getCellMap().get(id).energySteal(damage);
	}
	
	public static void givingcellchoice(WorldData world,CellId id) {
		CellDecisionSystem decision = new CellDecisionSystem();
		CellIntent intent = decision.decide(id, world);
		
		System.out.println("\n\nATTENTION A CELL IS DECIDING SOMETHING");
		System.out.println("Cell ID: " + id);
		
		if(intent.getClass().isRecord()) {
			System.out.println("A decision has been made : ");
			if(intent.getClass() == EatIntent.class) {
				System.out.println("It want to eat !");
			}else if(intent.getClass() == MoveIntent.class){
				System.out.println("It want to move !");
			}else if(intent.getClass() == IdleIntent.class) {
				System.out.println("It want to... stay still...");
			}else{
				System.out.println("The intent class is a record. Yet no intent has been recognized");
			};
		}else {
			System.out.println("there has been an error in decision making, no intent has been recognized. No record has been recognized.");
		}
	}
	
	public static void givingcellchoiceandapply(WorldData world,CellId id) {
		System.out.println("Apply function start");
		CellDecisionSystem decision = new CellDecisionSystem();
		System.out.println("Decision Made");
		CellIntent intent = decision.decide(id,world);
		System.out.println("Intent created");
		IntentApplier applier = new IntentApplier();
		System.out.println("Applying decisions");
		applier.apply(intent, world);
		System.out.println("Finish Applying decisions");
	}
	
	public static void MakeCellEat(WorldData world,CellId id) {
		CellData cell = world.getCellData(new CellId(1));
		System.out.println("Cell Data created");
		cell.energySteal(10);
		System.out.println("Cell lost energy");
		world.addFoodByPos(new Position2D(2,1), 10);
		System.out.println("Food Added to world");
		if (world.getallfoodpos() != null) {
			System.out.println("Food found :");
			for (int i = 0;i < world.getallfoodpos().size();i++) {
				System.out.println("\t" + world.getallfoodpos().get(i));
			}
			System.out.println("End of food found");
		}
		givingcellchoice(world,new CellId(1));
		givingcellchoiceandapply(world,new CellId(1));
		System.out.println("Cell decided and try to apply it");
		cellsfound(world);
		System.out.println("\nFood Check :");
		if (world.getallfoodpos().size() != 0) {
			System.out.println("Food found :");
			for (int i = 0;i < world.getallfoodpos().size();i++) {
				System.out.println("\t" + world.getallfoodpos().get(i));
			}
		}else {
			System.out.println("Food list empty");
		}
		System.out.println("End of food found");
	}
	
	public static void onevone(WorldData world) {
		//mini 1v1 to test decision, conflict and applying systems
		System.out.println("\n\n1V1 TEST START");
		CellId cell1_id = new CellId(1);
		CellId cell2_id = new CellId(2);
		System.out.println("\nCell1 and Cell2 created.");
		
		world.addCellByPos(cell1_id,new Position2D(1,1));
		world.addCellByPos(cell2_id,new Position2D(1,3));
		System.out.println("\nCell1 and Cell2 added to world.");
		
		cellsfound(world);
		
		
		//let's see them fight on food
		world.addFoodByPos(new Position2D(1,2), 10);
		
		damagecell(world,cell1_id,10);
		System.out.println("\nCell 1 damaged -10");
		damagecell(world,cell2_id,10);
		System.out.println("\nCell 1 damaged -10");
		//the way the program is made, makes the cell1 always wins
		//maybe using threads here can be interesting
		
		//world intents
		IntentCollector collector = new IntentCollector();
		System.out.println("\nCollector created.");
		List<CellIntent> intents = collector.collect(world);
		System.out.println("\nIntents Collected.");
		ApplyPhase applier = new ApplyPhase();
		System.out.println("\nApplier Created.");
		
		applier.applyAll(intents, world);
		System.out.println("\nIntents Applied.");
		
		cellsfound(world);
	}
	//code
	
	//what is this program capable of for now:
	//creates a world
	//Initialize every chunk in that world
	//correct wrong id's of chunks if needed
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//world configuration
		int h = 110;
		int w = 110;
		int ch = 10;
		int cw = 10;

		WorldData world = new WorldData(h,w,ch,cw);
		
		System.out.println("TEST START");
		//initializingWord(world);
		//correcting invalid chunk automatically;
		//invalidChunk(world,1,2);
		//Creating and finding cells and displaying their data
		//world.addCellByPos(new CellId(1), new Position2D(1,1));
		//cellsfound(world);
		//moving cell by one height then finding it again
		//world.addCellByPos(new CellId(2), new Position2D(1,1));
		//movecell(world,new CellId(1));
		//cellsfound(world);
		//Damaging cell by 5 points then finding it again
		//damagecell(world,new CellId(1),99);
		//cellsfound(world);
		//DECISION MAKING
		//givingcellchoice(world,new CellId(1));
		//givingcellchoice(world,new CellId(2));
		//APPLYING DECISION
		//MakeCellEat(world,new CellId(1));
		onevone(world);
	}
}
