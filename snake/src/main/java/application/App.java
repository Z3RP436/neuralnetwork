package application;

import javafx.util.Pair;
import snake.Game;
import snake.Snake;
import snake.Utils;

import java.util.HashMap;
import java.util.Map;

public class App {
	public static void main(String[] args){
		App app = new App(10,10);
		app.learn(1000);
	}

	//eat food, hit wall, hit snake, else
	private final double[] rewardTable = new double[]{1,-1,-1,-0.1};
	private final double alpha = 0.1; // Learning rate
	private final double gamma = 0.9; // Eagerness - 0 looks in the near future, 1 looks in the distant future

	private int boardWidth;
	private int boardHeight;
	private int actionCount = 4;
	private Map<String, double[][]> qTable = new HashMap<>();

	public App(int boardWidth, int boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
	}

	public void learn(int epochs){
		for(int i = 0;i< epochs;i++){
			Game game = new Game(boardWidth,boardHeight,null);
			game.randomInit();
			Snake snake = game.getSnake();
			while(snake.isAlive()){
				Pair<Integer,Integer> vel = Utils.randomVelocitiy();
				game.setVelX(vel.getKey());
				game.setVelY(vel.getValue());

				game.snakeOnBoard();
				String key = Utils.boardToKey(game.getBoard());
				double[][] rewardTable;
				if(qTable.containsKey(key)){
					rewardTable = qTable.get(key);
				}else{
					rewardTable = new double[3][3];
				}

				game.move();
				game.checkCollision();

				double reward = 0;
				if(snake.isAlive()){
					reward += 0.1;
				}else{
					reward += -10;
				}

				rewardTable[game.getVelX()+1][game.getVelY()+1] = reward;
				qTable.put(key,rewardTable);
			}
		}
		System.out.println("finish");
	}

	public void printQTable(){

	}
}
