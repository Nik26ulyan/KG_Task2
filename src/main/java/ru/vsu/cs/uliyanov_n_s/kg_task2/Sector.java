package ru.vsu.cs.uliyanov_n_s.kg_task2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Sector {
    GraphicsContext graphicsContext;
    int centerX;
    int centerY;
    int r;
    double startAngle;
    double endAngle;
    Color color1;
    Color color2;

    public Sector(GraphicsContext graphicsContext, int x_center, int y_center, int r, double firstAngle, double secondAngle, Color color1, Color color2) {
        this.graphicsContext = graphicsContext;
        this.centerX = x_center;
        this.centerY = y_center;
        this.r = r;
        this.startAngle = firstAngle;
        this.endAngle = secondAngle;
        this.color1 = color1;
        this.color2 = color2;
        
        isCorrectAngles(secondAngle, endAngle);
    }

    private static void isCorrectAngles(double firstAngle, double secondAngle) {
        if (!(firstAngle <= 2 * Math.PI && secondAngle <= 2 * Math.PI)) {
            try {
                throw new Exception("Invalid angles!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static double distanceBetweenPoints(int x, int y, int x_center, int y_center) {
        return (x - x_center) * (x - x_center) + (y - y_center) * (y - y_center);
    }

    private static int getQuarterByPoint(double x, double y, int x_center, int y_center) {
        if (x >= x_center && y < y_center) {
            return 1;
        } else {
            if (x < x_center && y <= y_center) {
                return 2;
            } else {
                if (x <= x_center && y > y_center) {
                    return 3;
                }
            }
        }
        return 4;
    }

    public void drawSector() {
        PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        
        int x_firstPoint = (int) (centerX + r * Math.cos(startAngle));
        int y_firstPoint = (int) (centerY - r * Math.sin(startAngle));
        int x_secondPoint = (int) (centerX + r * Math.cos(endAngle));
        int y_secondPoint = (int) (centerY - r * Math.sin(endAngle));

        List<Integer> quarters = new ArrayList<>();

        int quarterFirstPoint = getQuarterByPoint(x_firstPoint, y_firstPoint, centerX, centerY);
        int quarterSecondPoint = getQuarterByPoint(x_secondPoint, y_secondPoint, centerX, centerY);

        quarters.add(quarterFirstPoint);
        quarters.add(quarterSecondPoint);

        for (int i = 0; i < 4; i++) {
            if (!quarters.contains(i)) {
                if (quarterFirstPoint < quarterSecondPoint) {
                    if (i > quarterFirstPoint && i < quarterSecondPoint) {
                        quarters.add(i);
                    }
                } else {
                    if (i < quarterFirstPoint || i > quarterSecondPoint) {
                        quarters.add(i);
                    }
                }
            }
        }

        drawWithInterpolation(pixelWriter,x_firstPoint, y_firstPoint, x_secondPoint, y_secondPoint, quarters);
    }

    

    private void drawWithInterpolation(PixelWriter pixelWriter, int x_firstPoint, int y_firstPoint, int x_secondPoint, int y_secondPoint, List<Integer> quarters) {
        double red, green, blue;
        double part;
        double D1, D2;

        int xStart = centerX - r;
        int yStart = centerY - r;
        int quarterFirstPoint = getQuarterByPoint(x_firstPoint, y_firstPoint, centerX, centerY);
        int quarterSecondPoint = getQuarterByPoint(x_secondPoint, y_secondPoint, centerX, centerY);

        for (int row = 0; row < 2 * r; row++) {
            xStart++;
            for (int col = 0; col < 2 * r; col++) {
                yStart++;
                if (quarters.contains(getQuarterByPoint(xStart, yStart, centerX, centerY))) {
                    double dist = distanceBetweenPoints(xStart, yStart, centerX, centerY);
                    D1 = (xStart - centerX) * (y_firstPoint - centerY) - (yStart - centerY) * (x_firstPoint - centerX);
                    D2 = (xStart - centerX) * (y_secondPoint - centerY) - (yStart - centerY) * (x_secondPoint - centerX);
                    if (dist < Math.pow(r, 2)) {
                        if ((quarterSecondPoint - quarterFirstPoint <= 1 && quarterSecondPoint - quarterFirstPoint >= 0
                                || quarterFirstPoint == 4 && quarterSecondPoint == 1 && D1 > 0 && D2 < 0) || (D1 > 0 || D2 < 0)) {

                                part = Math.sqrt(dist);
                                red = (color1.getRed() + (color2.getRed() - color1.getRed()) * part / r);
                                green = (color1.getGreen() + (color2.getGreen() - color1.getGreen()) * part / r);
                                blue = (color1.getBlue() + (color2.getBlue() - color1.getBlue()) * part / r);

                                pixelWriter.setColor(xStart, yStart, new Color(red, green, blue, 1));

                        }
                    }
                }
            }
            yStart = centerY - r;
        }
    }
}