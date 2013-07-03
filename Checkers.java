/**  Made by Tom Taubkin
 * The actual checkers interface
 */

import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.Scanner; //allows us to use the scanner package

public class Checkers {
	static PrintWriter p;
    static boolean capture = false;
    
    static Board b; // initialize board named b
    static Controls c;
    static AI a;
    static final int WIDTH = 8;
    static int[][] grid = new int[][] { { 0, 2, 0, 2, 0, 2, 0, 2 },
		{ 2, 0, 2, 0, 2, 0, 2, 0 }, { 0, 2, 0, 2, 0, 2, 0, 2 },
		{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
		{ 1, 0, 1, 0, 1, 0, 1, 0 }, { 0, 1, 0, 1, 0, 1, 0, 1 },
		{ 1, 0, 1, 0, 1, 0, 1, 0 } };
        


    // 0 is no colour, 1 is red, 2 is green

   
    /**clean all the green moves from the board*/
    public static void cleanYellow() {

        for (int i = 0; i < WIDTH; i++) {

            for (int j = 0; j < WIDTH; j++) {

                if (grid[i][j] == 3) {

                    b.putPeg("White", i, j);

                    grid[i][j] = 0;

                }

            }

        }

    }

  /**Print the grid to a file */
    public static void printGrid(){
    	for(int i = 0; i < WIDTH; i++){
    		
    		for(int j = 0; j < WIDTH; j++){
    			p.print(grid[i][j] + ",");
    		}
    	
    		p.println();
    	}
    	p.println();
    	p.println();
    }
    /** Make all the corner pieces kings */
    public static void makeKing(){
        for(int i = 0; i < WIDTH; i++){
            if(grid[0][i] == 1 || grid[0][i] == 4){
                grid[0][i] = 4;
            }
            
            if(grid[7][i] == 2 || grid[7][i] == 5){
                grid[7][i] = 5;
            }
        }
    }
   
   
    /** Draw the actual colored Grid */
    public static void drawColouredGrid(int[][] grid) {

        for (int i = 0; i < WIDTH; i++) {

            for (int j = 0; j < WIDTH; j++) {

                if (i % 2 == 0 && j % 2 == 0) {

                    b.putPeg("Black", i, j);

                } else if (i % 2 != 0 && j % 2 != 0) {

                    b.putPeg("Black", i, j);

                } else {

                    if (grid[i][j] == 0) {

                        b.putPeg("White", i, j);

                    }

                    if (grid[i][j] == 1) {

                        b.putPeg("RED", i, j);

                    }

                    if (grid[i][j] == 2) {

                        b.putPeg("BLUE", i, j);

                    }

                    if (grid[i][j] == 3) {

                        b.putPeg("Green", i, j);

                    }

                    if (grid[i][j] == 5) {

                        b.putPeg("cyan", i, j);

                    }

                    if (grid[i][j] == 4) {

                        b.putPeg("pink", i, j);

                    }

                }

            }

        }

    }

   
    /** The main method with all the controls 
     * @throws FileNotFoundException */
    public static void main(String[] args) throws FileNotFoundException {
    	p = new PrintWriter(new File("log.txt"));
        Scanner input = new Scanner(System.in);
        //The number of rows and columns
        final int ROWS = WIDTH, COLS = WIDTH;
        
        int row = 0, col = 0;

        boolean finished = false;
        //Start the board class
        b = new Board(ROWS, COLS);
        //Should the AI move again?
        boolean again = false;

        drawColouredGrid(grid);
        
        int turn = 2;
        //Get the last x click
        int cx = -1;
        //Get the last Y click
        int cy = -1;
        boolean localgo = false;

        int captureval = -1;
        
        //main game loop
        while (true) {
        	
        	drawColouredGrid(grid);
        	//Print the grid to a file
        	printGrid();
        	//Make the kings
        	makeKing();
        	//Run the AI
        	if(turn == 1){
        		
        		if(c.lost(1, grid)){
                    System.out.println("The computer lost");
                	break;
                }
        		int[][] ngrid = new int[WIDTH][WIDTH];
        		ngrid = a.ai(grid);
        		int[] point = c.canChange(grid, ngrid);
        		
        		turn = 2;
        		
        		if(c.canTakePiece(ngrid, point[0], point[1], 1) && c.didCapture(ngrid, grid)){
        			turn = 1;
        		}
        		grid = ngrid;
        		
        		drawColouredGrid(grid);
        		makeKing();
        	   continue;
           }
        	//Check if player lost
        	if(c.lost(2, grid)){
        		b.displayMessage("A person is no match for a computer");
            	break;
            }
        	//Make kings again
        	makeKing();
            //Check if can capture and then capture
        	if (c.canCapture(2, grid)) {
        		b.displayMessage("Capture is true");
                capture = true;

               

            }
            if(capture == true & !c.canCapture(2, grid)){
            	capture = false;
            	b.displayMessage("");
            }
            
            Coordinate click = b.getClick();

            int x = click.getCol();

            int y = click.getRow();
            //If can capture then force a capture
            if (capture) {
            	
            	drawColouredGrid(grid);
                if(grid[y][x] == 5){
                    cleanYellow();
                    //System.out.println("this is where I should be");
                    if (c.canGetRightTop(turn, y, x, grid)) {

                        System.out.println("Here");

                        grid[y - 2][x + 2] = 3;

                        drawColouredGrid(grid);

                        cx = x;

                        cy = y;

                        captureval = 1;

                    }

                    if (c.canGetLeftTop(turn, y, x, grid)) {

                       

                        grid[y - 2][x - 2] = 3;

                        drawColouredGrid(grid);

                        cy = y;

                        cx = x;

                        

                    }
                    if (c.canGetRightBottom(turn, y, x, grid)) {

                        

                        grid[y + 2][x + 2] = 3;

                        drawColouredGrid(grid);

                        cy = y;

                        cx = x;

                        
                        

                    }

                    if (c.canGetLeftBottom(turn, y, x, grid)) {

                        

                        grid[y + 2][x - 2] = 3;

                        drawColouredGrid(grid);

                        cy = y;

                        cx = x;

                        

                    }
                    continue;
                   
                    
                }
                

                if (grid[y][x] == 2) {

                    System.out.println("I am here");

                    cleanYellow();

                    if (c.canGetRightBottom(turn, y, x, grid)) {

                        

                        grid[y + 2][x + 2] = 3;

                        drawColouredGrid(grid);

                        cy = y;

                        cx = x;

                        
                        

                    }

                    if (c.canGetLeftBottom(turn, y, x, grid)) {

                        

                        grid[y + 2][x - 2] = 3;

                        drawColouredGrid(grid);

                        cy = y;

                        cx = x;

                        

                    }

                    continue;

                }
                //If the player is ready to move
                if (grid[y][x] == 3) {
                	//move the pieces and check if another capture is possible
                    int val = grid[cy][cx];
                    grid[cy][cx] = 0;
                    grid[y][x] = val;
                    grid[(cy + y)/2][(cx + x)/2] = 0;
                    makeKing();
                    cleanYellow();

                    if (!c.canTakePiece(grid, y, x, turn)) {

                        capture = false;
                        b.displayMessage("");

                       turn = 1;

                    }

                    drawColouredGrid(grid);

                }

                continue;

            }
            //if clicked on a black square
            if (!b.isWhite(y, x)) {

                continue;

            } else {

                if (grid[y][x] == turn || grid[y][x] == turn + 3) {

                    cx = x;

                    cy = y;

                    cleanYellow();
                   //Move the peices to the needed positions
                    if (grid[y][x] == 5) {
                        if (c.canGo(y + 1, x + 1, grid)) {

                            grid[y + 1][x + 1] = 3;

                        }
                        if (c.canGo(y + 1, x - 1, grid)) {

                            grid[y + 1][x - 1] = 3;

                        }
                        if (c.canGo(y - 1, x + 1, grid)) {

                            grid[y - 1][x + 1] = 3;

                        }
                        if (c.canGo(y - 1, x - 1, grid)) {

                            grid[y - 1][x - 1] = 3;

                        }
                    }

                    if (turn == 2) {

                        if (c.canGo(y + 1, x + 1, grid)) {

                            grid[y + 1][x + 1] = 3;

                        }

                        if (c.canGo(y + 1, x - 1, grid)) {

                            grid[y + 1][x - 1] = 3;

                        }

                    } else {

                        if (c.canGo(y - 1, x + 1, grid)) {

                            grid[y - 1][x + 1] = 3;

                        }

                        if (c.canGo(y - 1, x - 1, grid)) {

                            grid[y - 1][x - 1] = 3;

                        }

                    }

                }  else if (grid[y][x] == 3) {

                    cleanYellow();
                    grid[y][x] = grid[cy][cx];
                    grid[cy][cx] = 0;

                    if (y == 0 && turn == 1 && grid[y][x] < 4) {
                    	
                        grid[y][x] = grid[y][x] + 3;
                        

                    }

                    else if (y == 7 && turn == 2 && grid[y][x] < 4) {

                        grid[y][x] = grid[y][x] + 3;

                    }
                    makeKing();
                    turn = 1;

                    if (c.canCapture(turn, grid)) {

                        capture = true;

                        System.out.println("capture is true");

                    }

                } else {

                    // System.out.println("Here");

                    continue;

                }
                //draw the grid
                drawColouredGrid(grid);

            }

        }
       p.close();
    }
    
}