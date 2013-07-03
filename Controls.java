/**  Made by Tom Taubkin
 * Controls to controls the AI and program
 */
public class Controls {
	/** Check if the piece can go to that spot */ 
	public static boolean canGo(int y, int x, int[][] grid) {

	        if (allowed(y, x) && grid[y][x] == 0)

	            return true;

	        return false;

	    }
	/**Check if that location in the array is valid*/
	public static boolean allowed(int y, int x){
		  if(y >= 8 || y < 0 || x >= 8 || x < 0)
			  return false;
		  return true;
	  }
	/**Switch the turn tyo the other player */
	 public static int switchTurn(int turn) {

	        if (turn == 1) {

	            return 2;

	        }

	        return 1;

	    }
	 /**Check if a capture on the board exists */
	 public static boolean canCapture(int turn, int[][] grid) {

	        boolean can = false;

	        for (int i = 0; i < 8; i++) {

	            for (int j = 0; j < 8; j++) {
	                if(canTakePiece(grid, i, j, turn)){
	                	return true;
	                }

	            }

	        }

	        return false;

	    }
	 /**Check is a player lost*/
	 public static boolean lost(int turn, int[][] grid){
		   if(canCapture(turn, grid)){
			   return false;
		   }
		   int num = 0;
		   for(int i = 0; i < 8; i++){
			   for(int j = 0; j < 8; j++){
				  if(grid[i][j] == 3){
					  return false;
				  }
				   if(grid[i][j] == turn || grid[i][j] == turn + 3){
					  
					   num++;
					  
					   if (grid[i][j] == turn + 3) {
	                       if (canGo(i + 1, j + 1, grid)) {

	                         return false;

	                       }
	                       if (canGo(i + 1, j - 1, grid)) {

	                          return false;

	                       }
	                       if (canGo(i - 1, j + 1, grid)) {

	                          return false;

	                       }
	                       if (canGo(i - 1, j - 1, grid)) {

	                    	   return false;

	                       }
	                   }

	                   if (turn == 2) {
	                	   
	                       if (canGo(i + 1, j + 1, grid)) {

	                          return false;
	                       }

	                       if (canGo(i + 1, j - 1, grid)) {

	                    	   return false;

	                       }

	                   } else {
	                	   
	                       if (canGo(i - 1, j + 1, grid)) {
	                    	   
	                           return false;

	                       }

	                       if (canGo(i - 1, j - 1, grid)) {
	                    	  
	                          return false;

	                       }

	                   }

	               }
					  
				   }
			   }
		   
		   
		   return true;
	   }
	 /**Check if a specific piece can make a capture */
	 public static boolean canTakePiece(int[][] grid, int i, int j, int turn){
	    	if(grid[i][j] == 4 && turn == 1){
	            if (canGetLeftTop(turn, i, j, grid) || canGetRightTop(turn, i, j, grid)) {

	                return true;

	            }
	            if (canGetLeftBottom(turn, i, j, grid) || canGetRightBottom(turn, i, j, grid)) {

	                return true;

	            }
	        } if(grid[i][j] == 5 && turn == 2){
	            if (canGetLeftTop(turn, i, j, grid) || canGetRightTop(turn, i, j, grid)) {

	                return true;

	            }
	            if (canGetLeftBottom(turn, i, j, grid) || canGetRightBottom(turn, i, j, grid)) {

	                return true;

	            }
	        }
	        if (grid[i][j] == 1 && turn == 1) {

	            if (canGetLeftTop(turn, i, j, grid) || canGetRightTop(turn, i, j, grid)) {

	                return true;

	            }

	        }
	       
	       

	        if (grid[i][j] == 2 && turn == 2) {

	            if (canGetLeftBottom(turn, i, j, grid) || canGetRightBottom(turn, i, j, grid)) {

	                return true;

	            }

	        }
	        return false;
	    }
	 /** Checks if piece can capture in the top left */
	 public static boolean canGetLeftTop(int turn, int i, int j, int[][] grid) {

	        int opp = switchTurn(turn);

	        if (allowed(i - 1, j - 1)
	                && (grid[i - 1][j - 1] == opp || grid[i - 1][j - 1] == opp + 3)
	                && allowed(i - 2, j - 2) && (grid[i - 2][j - 2] == 0 || grid[i - 2][j - 2] == 3)) {

	            return true;

	        }

	        return false;

	    }
	 /**Checks if piece can capture in the right top */
	 public static boolean canGetRightTop(int turn, int i, int j, int[][] grid) {

	        int opp = switchTurn(turn);

	        if (allowed(i - 1, j + 1)
	                && (grid[i - 1][j + 1] == opp || grid[i - 1][j + 1] == opp + 3)
	                && allowed(i - 2, j + 2) && (grid[i - 2][j + 2] == 0 || grid[i - 2][j + 2] == 3)) {

	            return true;

	        }

	        return false;

	    }
	 /**Checks if piece can capture in the left bottom */
	 public static boolean canGetLeftBottom(int turn, int i, int j, int[][] grid) {

	        int opp = switchTurn(turn);

	        if (allowed(i + 1, j - 1)
	                && (grid[i + 1][j - 1] == opp || grid[i + 1][j - 1] == opp + 3)
	                && allowed(i + 2, j - 2) && (grid[i + 2][j - 2] == 0 || grid[i + 2][j - 2] == 3)) {

	            return true;

	        }

	        return false;

	    }
	 /**Checks if piece can capture in the right bottom */
	 public static boolean canGetRightBottom(int turn, int i, int j, int[][] grid) {

	        int opp = switchTurn(turn);

	        if (allowed(i + 1, j + 1)
	                && (grid[i + 1][j + 1] == opp || grid[i + 1][j + 1] == opp + 3)
	                && allowed(i + 2, j + 2) && (grid[i + 2][j + 2] == 0 || grid[i + 2][j + 2] == 3)) {

	            return true;

	        }

	        return false;

	    }
	 /** Check where the AI moved */
	 public static int[] canChange(int[][] grid, int[][] ngrid){
		 for(int i = 0; i < 8; i++){
			 for(int j = 0; j < 8; j++){
				 if(grid[i][j] == 0 && (ngrid[i][j] == 1 || ngrid[i][j] == 4)){
					 int[] point = new int[2];
					 point[0] = i;
					 point[1] = j;
					 return point;
					 
				 }
			 }
		 }
	 
	 return null;
}
	 /** Check if the AI captured a piece */
	 public static boolean didCapture(int[][] grid, int[][] ngrid){
		 int num2 = 0;
		 
		 int diffnum2 = 0;
		 for(int i = 0; i < 8; i++){
			 for(int j = 0; j < 8; j++){
				 if(grid[i][j] == 2 || grid[i][j] == 5){
					 num2++;
				 }
				 if(ngrid[i][j] == 2 || ngrid[i][j] == 5){
					 diffnum2++;
				 }
			 }
		 }
		 if(num2 != diffnum2){
			 return true;
		 }
		 return false;
	 }
}
