package snake;

import javafx.util.Pair;
import ki.BaseKi;

import java.util.Random;

public class Game {
	private int[][] board;
	private Snake snake;
	private BaseKi snakeKi;
	private int velX;
	private int velY;

	public Game(int width, int height, BaseKi snakeKi) {
		this.board = new int[width][height];
		this.snakeKi = snakeKi;
	}

	public void init() {
		snake = new Snake(board.length / 2, board[0].length / 2);
	}

	public void randomInit() {
		Random random = new Random();
		snake = new Snake(random.nextInt(board.length - 1), random.nextInt(board[0].length - 1));
		Pair<Integer, Integer> vel = Utils.randomVelocitiy();
		this.velX = vel.getKey();
		this.velY = vel.getValue();
	}

	public void snakeOnBoard() {
		if (snake.isAlive()) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					board[i][j] = 0;
				}
			}

			for (SnakePart snakePart : snake.getSnakeParts()) {
				board[snakePart.getX()][snakePart.getY()] = -1;
			}
		}
	}

	public void move() {
		snake.move(velX, velY);
	}

	public void checkCollision() {
		if (snake.getHead().getX() < 0 || snake.getHead().getY() < 0 || snake.getHead().getX() >= board.length
				|| snake.getHead().getY() >= board[0].length) {
			snake.setAlive(false);
		}

		if (snake.getSnakeParts().size() > 1) {
			for (int i = 1; i < snake.getSnakeParts().size(); i++) {
				if (snake.getHead().getX() == snake.getSnakeParts().get(i).getX() && snake.getHead().getY() == snake.getSnakeParts().get(i).getY()) {
					snake.setAlive(false);
				}
			}
		}
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	public BaseKi getSnakeKi() {
		return snakeKi;
	}

	public void setSnakeKi(BaseKi snakeKi) {
		this.snakeKi = snakeKi;
	}
}
