package snake;

import java.util.LinkedList;

public class Snake {
	private boolean alive = true;
	private LinkedList<SnakePart> snakeParts = new LinkedList<>();

	public Snake(int x, int y) {
		snakeParts.add(new SnakePart(x, y, null));
	}

	public void move(int velX, int velY) {
		for (int i = snakeParts.size() - 1; i >= 0; i--) {
			SnakePart snakePart = snakeParts.get(i);
			if(snakePart.getParent() == null){
				snakePart.setX(snakePart.getX() + velX);
				snakePart.setY(snakePart.getY() + velY);
			}else {
				snakePart.setX(snakePart.getParent().getX());
				snakePart.setY(snakePart.getParent().getY());
			}
		}
	}

	public LinkedList<SnakePart> getSnakeParts() {
		return snakeParts;
	}

	public void setSnakeParts(LinkedList<SnakePart> snakeParts) {
		this.snakeParts = snakeParts;
	}

	public SnakePart getHead(){
		return snakeParts.get(0);
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
