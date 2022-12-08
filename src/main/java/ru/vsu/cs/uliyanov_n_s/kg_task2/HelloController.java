package ru.vsu.cs.uliyanov_n_s.kg_task2;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class HelloController {
    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() throws Exception {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        Sector sector = new Sector(canvas.getGraphicsContext2D(), 200, 200, 100, Math.PI / 3, 2*Math.PI , new Color(1,1,0,1.0), new Color(1,0,0,1.0));
        sector.drawSector();
    }



}