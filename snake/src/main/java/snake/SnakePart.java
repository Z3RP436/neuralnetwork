package snake;

public class SnakePart {
	private int x;
	private int y;
	private SnakePart parent;
	private SnakePart child;

	public SnakePart(int x, int y, SnakePart parent) {
		this.x = x;
		this.y = y;
		this.parent = parent;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public SnakePart getParent() {
		return parent;
	}

	public void setParent(SnakePart parent) {
		this.parent = parent;
	}

	public SnakePart getChild() {
		return child;
	}

	public void setChild(SnakePart child) {
		this.child = child;
	}
}
