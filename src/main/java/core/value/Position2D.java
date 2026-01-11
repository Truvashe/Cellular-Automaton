package core.value;

public record Position2D(int posh,int posw) {

	public Position2D translate(int i, int j) {
		return new Position2D(posh() + i,posw() + j);
	}
}
