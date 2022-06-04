/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintbrush;

import java.awt.Color;

/**
 *
 * @author Mariam
 */
public class Shape
{
    protected Color shapeColor;
    boolean dotted;

    public Shape(Color shapeColor, boolean dotted) {
        this.shapeColor = shapeColor;
        this.dotted = dotted;
    }

    public void setDotted(boolean dotted) {
        this.dotted = dotted;
    }

    public boolean isDotted() {
        return dotted;
    }

    public Color getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(Color shapeColor) {
        this.shapeColor = shapeColor;
    }

    public Shape() {
    }

    public Shape(Color shapeColor)
    {
        this.shapeColor = shapeColor;
    }
    
}
