/**  Made by Tom Taubkin
 * This is the AI for the checkers program which looks about 200 million moves into the future using the minimax algorithm and heuristic search
 */
public class AI {
	static int[][] grid = new int[][] { { 0, 2, 0, 2, 0, 2, 0, 2 },
			{ 2, 0, 2, 0, 2, 0, 2, 0 }, { 0, 2, 0, 2, 0, 2, 0, 2 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 1, 0, 1, 0, 1, 0 }, { 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 1, 0, 1, 0, 1, 0, 1, 0 } };
	static boolean beforecapture;
	static Controls c;
	static int xval;
	static int yval;
	static int search = 7;
	static boolean capture = false;
	final static int INFINITY = Integer.MAX_VALUE;
	final static int CHECKER = 100; //one checker worth 100  
	final static int POS=1;  //one position along the -j worth 1
	final static int KING=200; //a king's worth
	final static int EDGE=10; // effect of king being on the edge
	final static int RANDOM_WEIGHT=10; // weight factor

	public static void main(String[] args) {

	}
	public static boolean again = false;
	/** Start the AI which is a minimax algorithm returns a grid*/
	public static int[][] ai(int[][] grid) {
		
		int[] coord = new int[2];
		int max = Integer.MIN_VALUE;
		int[][] ngrid = copy(grid);
		int x = 0;
		int y = 0;
		
		again = false;
		if (c.canCapture(1, grid)) {
			capture = true;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (grid[i][j] == 1 || grid[i][j] == 4) {
						if (c.canGetRightTop(1, i, j, grid)) {

							int[][] cgrid = copy(grid);
							cgrid[i - 2][j + 2] = grid[i][j];
							cgrid[i][j] = 0;
							cgrid[i - 1][j + 1] = 0;
							int value = 0;
							if (c.canTakePiece(cgrid, i - 2, j + 2, 1)) {
								value = max(search, 0, cgrid);
							} else {
								value = min(search, 1, cgrid);
							}
							if (value > max) {
								y = i;
								x = j;
								ngrid = cgrid;
								max = value;
							}

						}
						if (c.canGetLeftTop(1, i, j, grid)) {

							int[][] cgrid = copy(grid);
							cgrid[i - 2][j - 2] = grid[i][j];
							cgrid[i][j] = 0;
							cgrid[i - 1][j - 1] = 0;
							int value = 0;
							if (c.canTakePiece(cgrid, i - 2, j - 2, 1)) {
								value = max(search, 0, cgrid);
							} else {
								value = min(search, 1, cgrid);
							}
							if (value >= max) {
								y = i;
								x = j;
								ngrid = cgrid;
								max = value;
							}

						}
					}
					if (grid[i][j] == 4) {
						if (c.canGetRightBottom(1, i, j, grid)) {

							int[][] cgrid = copy(grid);
							cgrid[i + 2][j + 2] = grid[i][j];
							cgrid[i][j] = 0;
							cgrid[i + 1][j + 1] = 0;
							int value = 0;
							if (c.canTakePiece(cgrid, i + 2, j + 2, 1)) {
								value = max(search, 0, cgrid);
							} else {
								value = min(search, 0 + 1, cgrid);
							}
							if (value >= max) {
								y = i;
								x = j;
								ngrid = cgrid;
								max = value;
							}

						}
						if (c.canGetLeftBottom(1, i, j, grid)) {

							int[][] cgrid = copy(grid);
							cgrid[i + 2][j - 2] = grid[i][j];
							cgrid[i][j] = 0;
							cgrid[i + 1][j - 1] = 0;
							int value = 0;
							if (c.canTakePiece(cgrid, i + 2, j - 2, 1)) {
								value = max(search, 0, cgrid);
							} else {
								value = min(search, 0 + 1, cgrid);
							}
							if (value >= max) {
								y = i;
								x = j;
								ngrid = cgrid;
								max = value;
							}

						}
					}
				}
			}
		}
		else {
			capture = false;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (grid[i][j] == 1 || grid[i][j] == 4) {
						if (c.canGo(i - 1, j + 1, grid)) {
							int[][] cgrid = copy(grid);
							cgrid[i - 1][j + 1] = grid[i][j];
							cgrid[i][j] = 0;
							int val = min(search, 1, cgrid);
							if (val >= max) {
								y = i;
								x = j;
								ngrid = cgrid;
								max = val;
							}
							
						}
						if (c.canGo(i - 1, j - 1, grid)) {
							int[][] cgrid = copy(grid);
							cgrid[i - 1][j - 1] = grid[i][j];
							cgrid[i][j] = 0;
							int val = min(search, 0 + 1, cgrid);
							if (val > max) {
								y = i;
								x = j;
								ngrid = cgrid;
								max = val;
							}
							
						}
					}
					if (grid[i][j] == 4) {
						if (c.canGo(i + 1, j + 1, grid)) {
							int[][] cgrid = copy(grid);
							cgrid[i + 1][j + 1] = grid[i][j];
							cgrid[i][j] = 0;
							int val = min(search, 0 + 1, cgrid);
							if (val > max) {
								y= i;
								x = j;
								ngrid = cgrid;
								max = val;
							}
							
						}
						if (c.canGo(i + 1, j - 1, grid)) {
							int[][] cgrid = copy(grid);
							cgrid[i + 1][j - 1] = grid[i][j];
							cgrid[i][j] = 0;
							int val = min(search, 0 + 1, cgrid);
							if (val > max) {
								y = i;
								x = j;
								ngrid = cgrid;
								max = val;
							}
							
						}
					}

				}
			}
		}
		
		xval = x;
		yval = y;
		
		return ngrid;

	}
	/** The heurisitc for the board returns an integer */
	public static int value(int grid[][]) {
		int score=0;
	    
	    for (int i=0; i<8; i++)
	      for (int j=0; j<8; j++)
		  {
		    if (grid[i][j] == 2)
		      {
				score-=CHECKER;
				score-=POS*j*j;
		      }
		    
		    else if (grid[i][j] == 5){
		      score-=KING;
			  if (i==0 || i==7)
				  score += EDGE;
			  if (j==0 || j==7)
				  score += EDGE;
		    }

		    else if (grid[i][j] == 4){
		      score+=KING;
			  if (i==0 || i==7)
				  score -= EDGE;
			  if (j==0 || j==7)
				  score -= EDGE;
		    }
		    
		    else if (grid[i][j] == 1)
		    {
				score+=CHECKER;
				score+=POS*(7-j)*(7-j); 
		    }  
		  }//end for
	        score += (int)(Math.random() * RANDOM_WEIGHT);
	    return score;
	}
	/** Checks the best move that the AI can make at a current step*/
	public static int max(int depth, int curdepth, int[][] grid) {
		
		if (c.lost(1, grid)) {
			return -1000;
		}
		if (curdepth >= depth) {
			return value(grid);
		}
		
		int max = Integer.MIN_VALUE;
		if (c.canCapture(1, grid)) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (grid[i][j] == 1 || grid[i][j] == 4) {
						if (c.canGetRightTop(1, i, j, grid)) {

							int[][] cgrid = copy(grid);
							cgrid[i - 2][j + 2] = grid[i][j];
							cgrid[i][j] = 0;
							cgrid[i - 1][j + 1] = 0;
							int value = 0;
							if (c.canTakePiece(cgrid, i - 2, j + 2, 1)) {
								value = max(depth, curdepth, cgrid);
							} else {
								value = min(depth, curdepth + 1, cgrid);
							}
							if (value > max) {
								max = value;
							}

						}
						if (c.canGetLeftTop(1, i, j, grid)) {

							int[][] cgrid = copy(grid);
							cgrid[i - 2][j - 2] = grid[i][j];
							cgrid[i][j] = 0;
							cgrid[i - 1][j - 1] = 0;
							int value = 0;
							if (c.canTakePiece(cgrid, i - 2, j - 2, 1)) {
								value = max(depth, curdepth, cgrid);
							} else {
								value = min(depth, curdepth + 1, cgrid);
							}
							if (value > max) {
								max = value;
							}

						}
					}
					if (grid[i][j] == 4) {
						if (c.canGetRightBottom(1, i, j, grid)) {

							int[][] cgrid = copy(grid);
							cgrid[i + 2][j + 2] = grid[i][j];
							cgrid[i][j] = 0;
							cgrid[i + 1][j + 1] = 0;
							int value = 0;
							if (c.canTakePiece(cgrid, i + 2, j + 2, 1)) {
								value = max(depth, curdepth, cgrid);
							} else {
								value = min(depth, curdepth + 1, cgrid);
							}
							if (value > max) {
								max = value;
							}

						}
						if (c.canGetLeftBottom(1, i, j, grid)) {

							int[][] cgrid = copy(grid);
							cgrid[i + 2][j - 2] = grid[i][j];
							cgrid[i][j] = 0;
							cgrid[i + 1][j - 1] = 0;
							int value = 0;
							if (c.canTakePiece(cgrid, i + 2, j - 2, 1)) {
								value = max(depth, curdepth, cgrid);
							} else {
								value = min(depth, curdepth + 1, cgrid);
							}
							if (value > max) {
								max = value;
							}

						}
					}
				}
			}
		} else {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (grid[i][j] == 1 || grid[i][j] == 4) {
						if (c.canGo(i - 1, j + 1, grid)) {
							int[][] cgrid = copy(grid);
							cgrid[i - 1][j + 1] = grid[i][j];
							cgrid[i][j] = 0;
							int val = min(depth, curdepth + 1, cgrid);
							if (val > max) {
								max = val;
							}
						}
						if (c.canGo(i - 1, j - 1, grid)) {
							int[][] cgrid = copy(grid);
							cgrid[i - 1][j - 1] = grid[i][j];
							cgrid[i][j] = 0;
							int val = min(depth, curdepth + 1, cgrid);
							if (val > max) {
								max = val;
							}
						}
					}
					if (grid[i][j] == 4) {
						if (c.canGo(i + 1, j + 1, grid)) {
							int[][] cgrid = copy(grid);
							cgrid[i + 1][j + 1] = grid[i][j];
							cgrid[i][j] = 0;
							int val = min(depth, curdepth + 1, cgrid);
							if (val > max) {
								max = val;
							}
						}
						if (c.canGo(i + 1, j - 1, grid)) {
							int[][] cgrid = copy(grid);
							cgrid[i + 1][j - 1] = grid[i][j];
							cgrid[i][j] = 0;
							int val = min(depth, curdepth + 1, cgrid);
							if (val > max) {
								max = val;
							}
						}
					}

				}
			}
		}

		return max;
	}
	/** Checks the best move that the player can make at a certain step */
	public static int min(int depth, int curdepth, int[][] grid) {
		
		if (c.lost(2, grid)) {
			return 1000;
		}
		if (curdepth >= depth) {
			return value(grid);
		}

		int min = Integer.MAX_VALUE;
		if (c.canCapture(2, grid)) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (grid[i][j] == 2 || grid[i][j] == 5) {
						if (c.canGetRightBottom(2, i, j, grid)) {

							int[][] cgrid = copy(grid);
							cgrid[i + 2][j + 2] = grid[i][j];
							cgrid[i][j] = 0;
							cgrid[i + 1][j + 1] = 0;
							int value = 0;
							if (c.canTakePiece(cgrid, i + 2, j + 2, 2)) {
								value = min(depth, curdepth, cgrid);
							} else {
								value = max(depth, curdepth + 1, cgrid);
							}
							if (value < min) {
								min = value;
							}

						}
						if (c.canGetLeftBottom(2, i, j, grid)) {

							int[][] cgrid = copy(grid);
							cgrid[i + 2][j - 2] = grid[i][j];
							cgrid[i][j] = 0;
							cgrid[i + 1][j - 1] = 0;
							int value = 0;
							if (c.canTakePiece(cgrid, i + 2, j - 2, 2)) {
								value = min(depth, curdepth, cgrid);
							} else {
								value = max(depth, curdepth + 1, cgrid);
							}
							if (value < min) {
								min = value;
							}

						}
					}
					if (grid[i][j] == 5) {
						if (c.canGetRightTop(2, i, j, grid)) {

							int[][] cgrid = copy(grid);
							cgrid[i - 2][j + 2] = grid[i][j];
							cgrid[i][j] = 0;
							cgrid[i - 1][j + 1] = 0;
							int value = 0;
							if (c.canTakePiece(cgrid, i - 2, j + 2, 2)) {
								value = min(depth, curdepth, cgrid);
							} else {
								value = max(depth, curdepth + 1, cgrid);
							}
							if (value < min) {
								min = value;
							}

						}
						if (c.canGetLeftTop(2, i, j, grid)) {

							int[][] cgrid = copy(grid);
							cgrid[i - 2][j - 2] = grid[i][j];
							cgrid[i][j] = 0;
							cgrid[i - 1][j - 1] = 0;
							int value = 0;
							if (c.canTakePiece(cgrid, i - 2, j - 2, 2)) {
								value = min(depth, curdepth, cgrid);
							} else {
								value = max(depth, curdepth + 1, cgrid);
							}
							if (value < min) {
								min = value;
							}

						}
					}
				}
			}
		} else {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (grid[i][j] == 2 || grid[i][j] == 5) {
						if (c.canGo(i + 1, j + 1, grid)) {
							int[][] cgrid = copy(grid);
							cgrid[i + 1][j + 1] = grid[i][j];
							cgrid[i][j] = 0;
							int val = max(depth, curdepth + 1, cgrid);
							if (val < min) {
								min = val;
							}
						}
						if (c.canGo(i + 1, j - 1, grid)) {
							int[][] cgrid = copy(grid);
							cgrid[i + 1][j - 1] = grid[i][j];
							cgrid[i][j] = 0;
							int val = max(depth, curdepth + 1, cgrid);
							if (val < min) {
								min = val;
							}
						}
					}
					if (grid[i][j] == 4) {
						if (c.canGo(i - 1, j + 1, grid)) {
							int[][] cgrid = copy(grid);
							cgrid[i - 1][j + 1] = grid[i][j];
							cgrid[i][j] = 0;
							int val = max(depth, curdepth + 1, cgrid);
							if (val < min) {
								min = val;
							}
						}
						if (c.canGo(i - 1, j - 1, grid)) {
							int[][] cgrid = copy(grid);
							cgrid[i - 1][j - 1] = grid[i][j];
							cgrid[i][j] = 0;
							int val = max(depth, curdepth + 1, cgrid);
							if (val < min) {
								min = val;
							}
						}
					}

				}
			}
		}

		return min;
	}
	/** Copy the grid returns a grid */
	public static int[][] copy(int[][] grid) {
		int[][] cgrid = new int[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				cgrid[i][j] = grid[i][j];
			}
		}
		return cgrid;
	}

}
