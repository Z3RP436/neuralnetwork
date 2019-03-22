package snake;

import javafx.util.Pair;

import java.util.Random;

public class Utils {
	public static Pair<Integer, Integer> randomVelocitiy(){
		Random random = new Random();
		int velX,velY = 0;
		velX = random.nextInt(3) - 1;
		if(velX == 0){
			velY = random.nextInt(3) - 1;
			if(velY == 0) velY = 1;
		}
		return new Pair<>(velX,velY);
	}

	public static String boardToKey(int[][] board){
		StringBuilder ret = new StringBuilder();
		for(int i = 0; i< board.length;i++){
			for(int j = 0; j< board[i].length;j++){
				ret.append(board[i][j]);
			}
		}
		return ret.toString();
	}
}
