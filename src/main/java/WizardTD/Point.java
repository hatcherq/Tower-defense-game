package WizardTD;

import java.util.List;

public class Point {
    private int row; 
    private int col;
    private List<String> movements;

    /**
     * is called when I need to solve the maze 
     * @param row represents the row (vertical) position of a point in the maze.
     * @param col represents the column (horizontal) position of a point in the maze.
     * @param movements a list of movements that have been made to reach the current point in the maze
     */
    Point(int row, int col, List<String> movements) {
        this.row = row;
        this.col = col;
        this.movements = movements;
    }

    public int getrow(){
        return row;
    }

    public int getcol(){
        return col;
    }
    
    public List<String> getMovements(){
        return movements;
    }



}
