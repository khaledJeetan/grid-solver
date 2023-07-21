package com.example.aihw;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GenerateGridController {
    private HelloApplication app = new HelloApplication();
    private static Cell[][] cellList;
    private static Cell source = new Cell();
    private static Cell target = new Cell();
    private static GridPane grid;

    @FXML
    private ToggleGroup configure;

    @FXML
    private RadioButton blocked;

    @FXML
    private RadioButton goal;

    @FXML
    private AnchorPane paneGrid;

    @FXML
    private TextField col;

    @FXML
    private Label stepsNumber;

    @FXML
    private RadioButton start;

    @FXML
    private TextField row;

    @FXML
    private ToggleGroup algorithm;

    @FXML
    private RadioButton manhattan;

    public void initialize() {
        app.stopMusic();
        initializeGrid(5,5, 80, 80);
    }

    @FXML
    protected void onResizeGridButtonClick(){
        isNumber(row);
        isNumber(col);
        initializeGrid(
                Integer.parseInt(row.getText()),
                Integer.parseInt(col.getText()),
                80,80);
    }

    @FXML
    protected void onSolveButtonClick(){

        ArrayList<Cell> path = Calculation.AStar(cellList,algorithm.getSelectedToggle().equals(manhattan));
        if(path == null){
            Alert warn = new Alert(Alert.AlertType.WARNING, "Not Number", ButtonType.CLOSE);
            warn.setHeaderText("No Solution");
            warn.setContentText("Grid Cannot be solved NO Solution");
            warn.show();

        }
        for (Cell cell : path) {
            // Colorize Tested Nodes
                cell.setColor(Color.TESTED.getColor());
                cell.setText(String.valueOf(cell.getF()));
        }
        highlightPath();
        source.setColor(Color.SOURCE.getColor());
        source.setText("Start");
        stepsNumber.setText("Steps Number:- " +String.valueOf(path.size()) + "\n" );
    }

    private void highlightPath() {
        Cell cell = target;
        while (cell.getParentCell() != null){
            if (cell != source && cell != target)
                cell.setColor(Color.Path.getColor());
            cell = cell.getParentCell();
        }
    }

    @FXML
    protected void onBackButtonClick() {
        app.changeScene("hello-view.fxml");
    }

    protected void initializeGrid(int row, int col, int cellWidth, int cellHeight) {
        try {
            paneGrid.getChildren().clear();
            GridPane grid = new GridPane();
            this.grid = grid;
            grid.setVgap(0);
            grid.setHgap(0);
            System.out.println("Grid Lines Status:- "+grid.isGridLinesVisible());
            cellList = new Cell[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    cellList[i][j] = new Cell();
                    cellList[i][j].setPos(new Point2D(j,i));
                    cellList[i][j].setPrefSize(cellWidth,cellHeight);
                    setOnClickEventHandler(cellList[i][j]);
                    grid.add(cellList[i][j], i,j);
                }
            }
            cellList[0][0].setType(Type.SOURCE);
            cellList[0][0].setColor(Color.SOURCE.getColor());
            cellList[0][0].setText("Start");
            source = cellList[0][0];
            cellList[row - 1][col - 1].setType(Type.TARGET);
            cellList[row - 1][col - 1].setColor(Color.TARGET.getColor());
            cellList[row - 1][col - 1].setText("Goal");
            target = cellList[row - 1][col - 1];
            paneGrid.getChildren().add(grid);

        }catch (Exception e) {
            Alert warn = new Alert(Alert.AlertType.WARNING, "Not Number", ButtonType.CLOSE);
            warn.setHeaderText("Grid Initializer Error");
            warn.setContentText("GRid can't be initialized.. Error With Cells or Grid!!\n"+e.toString());
            e.printStackTrace();
            warn.show();
        }

    }

    private void setOnClickEventHandler(Cell cell) {
        cell.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Cell cell = ((Cell) (mouseEvent.getSource()));

                if (configure.getSelectedToggle().equals(start)) {

                    if(isSourceTarget(cell))
                        return;
                    System.out.println("Set "+ cell.getPos().toString()  + " as Start, update Source");
                    Cell temp = source;
                    source = cell;
                    cell.updateCell(temp);
                    temp.resetCell();
                }
                else if(configure.getSelectedToggle().equals(goal)){

                    if(isSourceTarget(cell))
                        return;

                    System.out.println("Set "+ cell.getPos().toString()  + " as Target, update target");
                    Cell temp = target;
                    target = cell;
                    cell.updateCell(temp);
                    temp.resetCell();
                }
                else if(configure.getSelectedToggle().equals(blocked)){
                    if(!isSourceTarget(cell))
                        cell.makeObstacle();
                }
                else{
                    if( !isSourceTarget(cell))
                        cell.resetCell();
                }
            }
        });
    }

    private boolean isSourceTarget(Cell cell) {
        if(cell.getType().equals(Type.SOURCE) || cell.getType().equals(Type.TARGET) ){
            Alert warn = new Alert(Alert.AlertType.WARNING, "Invalid Move", ButtonType.CLOSE);
            warn.setHeaderText("source && target cannot be replaced!!! Obstacles Cannot be set here");
            warn.setContentText("GRid can't be initialized.. Error With Cells or Grid!!\n");
            warn.show();
            return true;
        }
        return false;

    }

    private boolean isNumber(TextField x) {
        try {
            Integer.parseInt(x.getText());
            return true;
        }catch (Exception e) {
            Alert warn = new Alert(Alert.AlertType.WARNING, "Not Number", ButtonType.CLOSE);
            warn.setHeaderText("Invalid Input");
            warn.setContentText("you must specify an Integer number!!\n"+e.toString());
            e.printStackTrace();
            warn.show();
            x.setText("4");
            return false;
        }
    }

}
