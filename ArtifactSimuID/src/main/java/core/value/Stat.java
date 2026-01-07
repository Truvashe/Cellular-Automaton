package core.value;

public class Stat {
	int max;
	int min;
	
	int current;
	
	public Stat(int current, int min, int max) {
        this.current = current;
        this.min = min;
        this.max = max;
    }

    public void add(int amount) {
        current = Math.max(min, Math.min(max, current + amount));
    }

    public void subtract(int amount) {
        current = Math.max(min, Math.min(max, current - amount));
    }

    public int get() { return current; }
    
    public int getMax() {
    	return this.max;
    }
    
    public int getMin() {
    	return this.min;
    }
}
