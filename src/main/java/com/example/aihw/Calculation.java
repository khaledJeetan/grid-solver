package com.example.aihw;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Calculation {


    public static ArrayList<Cell> AStar(Cell[][] cellList ,boolean isManhattan){

        ArrayList<Cell> closedList = new ArrayList<>();
        ArrayList<Cell> openList = new ArrayList<>();
        Cell start = getCell(cellList,Type.SOURCE);
        Cell target = getCell(cellList,Type.TARGET);
        start.setH(calculateH(start.getPos(),target.getPos(),isManhattan));
        openList.add(start);


        do{
            if (openList.isEmpty()){
                return null;
            }
            Cell n = getBestFCell(openList);

            if(n.getType().equals(Type.TARGET))
                return closedList;

            ArrayList<Cell> neighbors = getNeighbors(n,cellList);

            if(!neighbors.isEmpty())
            for (Cell cell: neighbors ) {

                if(!openList.contains(cell) && !closedList.contains(cell)){
                    // set G (cost) for nodes
                    cell.setG(n.getG()+1);  // not mentioned in book but i needed it in "else" code below
                    openList.add(cell);
                    cell.setH(calculateH(cell.getPos(),target.getPos(),isManhattan));
                    cell.setParentCell(n);


                    //colorize ExpandedNodes and its children
                    if(cell != start && cell != target) {
                        cell.setColor(Color.EXPANDED.getColor());
                        cell.setText(String.valueOf(cell.getF()));
                    }

                }
                else {
                    if(is_G_better(cell,n)){

                        cell.setH(calculateH(
                                cell.getPos(),
                                closedList.get(closedList.indexOf(cell)).getPos(), //
                                isManhattan
                        ));
//                        Cell temp = closedList.get(closedList.indexOf(cell));
//                        closedList.remove(temp);
                        closedList.remove(cell);
                        openList.add(cell);
                        cell.setParentCell(n);


                    }
                    
                }
            }
            openList.remove(n);
            closedList.add(n);

        }while(!openList.isEmpty());
        return closedList;
    }

    private static boolean is_G_better(Cell cell,Cell n) {
        return (cell.getG() > n.getG()+1);
    }

    private static ArrayList<Cell> getNeighbors(Cell n,Cell[][] gridCells) {
       ArrayList<Cell> neighbors = new ArrayList<>();

        int currentColumn = (int) n.getPos().getX();
        int currentRow = (int) n.getPos().getY();
        int rows = gridCells.length;
        int cols = gridCells[0].length;

        int neighborColumn;
        int neighborRow;

        // top
        neighborColumn = currentColumn;
        neighborRow = currentRow - 1;

        if (neighborRow >= 0) {
            if( !gridCells[neighborRow][neighborColumn].getType().equals(Type.BLOCKED)) {
                neighbors.add(gridCells[neighborRow][neighborColumn]);
            }
        }

        // bottom
        neighborColumn = currentColumn;
        neighborRow = currentRow + 1;

        if (neighborRow < rows) {
            if( !gridCells[neighborRow][neighborColumn].getType().equals(Type.BLOCKED)) {
                neighbors.add(gridCells[neighborRow][neighborColumn]);
            }
        }

        // left
        neighborColumn = currentColumn - 1;
        neighborRow = currentRow;

        if ( neighborColumn >= 0) {
            if( !gridCells[neighborRow][neighborColumn].getType().equals(Type.BLOCKED)) {
                neighbors.add(gridCells[neighborRow][neighborColumn] );
            }
        }

        // right
        neighborColumn = currentColumn + 1;
        neighborRow = currentRow;

        if ( neighborColumn < cols) {
            if( !gridCells[neighborRow][neighborColumn].getType().equals(Type.BLOCKED)) {
                neighbors.add(gridCells[neighborRow][neighborColumn]);
            }
        }
        return neighbors;
    }

    private static Cell getBestFCell(ArrayList<Cell> openList) {
        Cell bestFcell = openList.get(0);
        for (Cell cell : openList) {
            if (cell.getF() < bestFcell.getF()) {
                bestFcell = cell;
            }
            else if(cell.getF() == bestFcell.getF()) {
                if (cell.getH() < bestFcell.getH()) {
                    bestFcell = cell;
                }
            }
        }
        return bestFcell;
    }


    public static double calculateH(Point2D a, Point2D b, boolean isManhattan){
        if(isManhattan)
            return   (Math.abs(a.getX() - b.getX()) +  Math.abs(a.getY()-b.getY()));
        else
            return (Math.sqrt(Math.pow((a.getX()-b.getX()),2) + Math.pow((a.getY()-b.getY()),2)) );
    }

    public static Cell getCell(Cell[][] list, Type type){
        for (int i = 0; i < list.length ; i++) {
            for (int j = 0; j < list[0].length; j++) {
                if(list[i][j].getType() == type){
                    return list[i][j];
                }
            }
        }
        return null;
    }

}
