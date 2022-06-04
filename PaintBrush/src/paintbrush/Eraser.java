/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paintbrush;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Mariam
 */
public class Eraser extends Rectangle
{
    public Eraser(Point startPoint) {
        super(startPoint, 15, 15, Color.WHITE,false,false);
    }
}
