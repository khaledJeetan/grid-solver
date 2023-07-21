package com.example.aihw;


import javafx.geometry.Point2D;
import javafx.scene.control.Button;

public class Cell extends Button {
    private Type type;
    private double h;
    private int g;
    private Point2D pos;
    private Cell parent;

    public Cell() {
        super();
        type = Type.UNBLOCKED;
        h = 0;
        g = 0;
        pos = new Point2D(0, 0);
        parent = null;
//        this.setText("UnBlocked");
        this.setColor(Color.UNBLOCKED.getColor()); //"#f5f3f6");//"#fafcfc");
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public Cell getParentCell() {
        return parent;
    }

    public void setParentCell(Cell parent) {
        this.parent = parent;
    }

    public void setG(int g) {
        this.g = g;
    }

    public double getF() {
        return g+h;
    }

    public Point2D getPos() {
        return pos;
    }

    public void setColor(String color) {
        int size = this.getType() != Type.SOURCE && this.getType() != Type.TARGET ?  13 : 18;
        this.setStyle(
                "-fx-border-radius: 0;" +
                "-fx-border-color: #65a8ec;" +
                "-fx-border-width: 1;" +
                "-fx-background-color: "+color +";" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: "+size +";" +
                "-fx-font-family: 'Bookman Old Style';");
    }

    public void setPos(Point2D pos) {
        this.pos = pos;
    }

    public void makeObstacle() {
        this.setColor(Color.BLOCKED.getColor());//"#6b6b6b");
        this.setType(Type.BLOCKED);
        this.setText("");
    }
    public void resetCell(){
        this.setText("");
        this.setType(Type.UNBLOCKED);
        this.setColor(Color.UNBLOCKED.getColor()); //"#f5f3f6");
    }
    public void updateCell(Cell oldCell){
        this.setType(oldCell.getType());
        this.setStyle(oldCell.getStyle());
        this.setText(oldCell.getText());

        /**
         * I should make 2 ENUM
         * 1- for colors ==> or can make it in separate style sheet
         * 2- for Types
         */
    }
}
