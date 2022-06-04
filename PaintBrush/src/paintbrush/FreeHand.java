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
public class FreeHand extends Oval{

    public FreeHand(Point startPoint,Color shapeColor) {
        super(startPoint, 20, 20, shapeColor,false,false);
    }

}
