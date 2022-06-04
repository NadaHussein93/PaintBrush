/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appletpackage;
import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import paintbrush.*;
import paintbrush.SelectedDrawingOption;
/**
 *
 * @author Mariam Abu El-Hamd & Nada Hussien
 */
public class PaintApplet extends Applet {
    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    //Color buttons
    Button redButton,greenButton,blueButton,yellowButton,blackButton;
    //Shape buttons
    Button rectangleButton, ovalButton ,lineButton,freehandButton,eraserButton,clearButton,undoButton,saveButton;
    Checkbox dottedCheckbox,filledCheckbox;
    ArrayList<Shape> shapes,temps;
    Color c;
    Graphics2D g2d;
    Point p1,p2,p_rect;
    int x1,y1,x2,y2,w_rect,h_rect,imageIndex=1;
    boolean isDragged,isDotted,isFilled;
    SelectedDrawingOption myshape;
    @Override
    public void init()
    {
        resize(2000,2000);
        setLayout(null);
        c = Color.BLACK;

        isDragged = false;
        isDotted = false;
        isFilled = false;

        shapes = new ArrayList<>();
        temps = new ArrayList<>();

        addMouseMotionListener(new MyMouseMotionListener());
        addMouseListener(new MyMouseListener());

        redButton = new Button();
        greenButton = new Button();
        blueButton = new Button();
        yellowButton = new Button();
        blackButton = new Button();

        redButton.setBounds(10, 10, 30, 30);
        greenButton.setBounds(10,50, 30, 30);
        blueButton.setBounds(10, 90, 30, 30);
        yellowButton.setBounds(10, 130, 30, 30);
        blackButton.setBounds(10, 170, 30, 30);

        redButton.setBackground(Color.RED);
        greenButton.setBackground(Color.GREEN);
        blueButton.setBackground(Color.BLUE);
        yellowButton.setBackground(Color.YELLOW);
        blackButton.setBackground(Color.BLACK);

        redButton.addActionListener(
           new ActionListener(){
                  @Override
                  public void actionPerformed(ActionEvent ev)
                  {
                      c = Color.RED;
                  }
           });        
        greenButton.addActionListener(
            new ActionListener(){
                  @Override
                  public void actionPerformed(ActionEvent ev)
                  {
                    c = Color.GREEN;
                  }
           });
        blueButton.addActionListener(
           new ActionListener(){
                  @Override
                  public void actionPerformed(ActionEvent ev)
                  {
                     c = Color.BLUE;
                  }
           });
        yellowButton.addActionListener(
           new ActionListener(){
                  @Override
                  public void actionPerformed(ActionEvent ev)
                  {
                     c = Color.YELLOW;
                  }
           });
        blackButton.addActionListener(
           new ActionListener(){
                  @Override
                  public void actionPerformed(ActionEvent ev)
                  {
                    c = Color.BLACK;
                  }
           });
        rectangleButton = new Button(" Rectangle ");
        rectangleButton.setBounds(50, 10, 70, 50);
        rectangleButton.addActionListener(
           new ActionListener(){
                  public void actionPerformed(ActionEvent ev)
                  {
                  myshape = SelectedDrawingOption.RECTANGLE;
                  }
           });
        ovalButton = new Button(" Oval ");
        ovalButton.setBounds(130, 10, 70, 50);
        ovalButton.addActionListener(
           new ActionListener(){
                  public void actionPerformed(ActionEvent ev)
                  {
                  myshape = SelectedDrawingOption.OVAL;
                  }
           });
        lineButton = new Button(" Line ");
        lineButton.setBounds(210, 10, 70, 50);
        lineButton.addActionListener(
           new ActionListener(){
                  public void actionPerformed(ActionEvent ev)
                  {
                  myshape = SelectedDrawingOption.LINE;
                  }
           });
        freehandButton = new Button(" Free Hand ");
        freehandButton.setBounds(290, 10, 70, 50);
        freehandButton.addActionListener(
           new ActionListener(){
                  public void actionPerformed(ActionEvent ev)
                  {
                     myshape = SelectedDrawingOption.FREEHAND;
                  }
           });
        eraserButton = new Button(" Eraser ");
        eraserButton.setBounds(370, 10, 70, 50);
        eraserButton.addActionListener(
           new ActionListener(){
                  public void actionPerformed(ActionEvent ev)
                  {
                     myshape = SelectedDrawingOption.ERASER;
                  }
           });
        clearButton = new Button(" Clear All ");
        clearButton.setEnabled(false);
        clearButton.setBounds(450, 10, 70, 50);
        clearButton.addActionListener(
           new ActionListener(){
                  public void actionPerformed(ActionEvent ev)
                  {
                     temps = shapes;
                     myshape = SelectedDrawingOption.CLEARALL;
                     shapes = new ArrayList<>();
                     c = Color.BLACK;
                     repaint();
                  }
           });
        dottedCheckbox = new Checkbox(" Dotted ");
        dottedCheckbox.setBounds(530,10,70,50);
        dottedCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
               if(e.getStateChange()==1)
                   isDotted = true;
               else
                   isDotted = false;
            }
        });
        filledCheckbox = new Checkbox(" Filled ");
        filledCheckbox.setBounds(610,10,70,50);
        filledCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
               if(e.getStateChange()==1)
                   isFilled = true;
               else
                   isFilled = false;
            }
        });
        undoButton = new Button(" Undo ");
        undoButton.setBounds(690,10,70,50);
        undoButton.setEnabled(false);
        undoButton.addActionListener(
           new ActionListener(){
                  public void actionPerformed(ActionEvent ev)
                  {
                     if(myshape == SelectedDrawingOption.CLEARALL)
                            shapes = temps;
                     else if(shapes.get(shapes.size()-1)instanceof FreeHand ||shapes.get(shapes.size()-1)instanceof Eraser)
                     {
                        for(int i = shapes.size()-1,count=0;i>=0;i--,count++)
                        {
                           if(shapes.get(i) instanceof FreeHand )
                               shapes.remove(i);
                           else
                             break;
                           if(count==25)
                             break;
                        }
                        for(int i = shapes.size()-1,count=0;i>=0;i--,count++)
                        {
                           if(shapes.get(i) instanceof Eraser )
                               shapes.remove(i);
                           else
                             break;
                            if(count==25)
                             break;
                        }
                     }
                     else
                          shapes.remove(shapes.size()-1);
                     repaint();
                  }
           });
        saveButton = new Button(" Save ");
        saveButton.setBounds(770,10,70,50);
        saveButton.addActionListener(
           new ActionListener(){
                  public void actionPerformed(ActionEvent ev)
                  {
                     try{
BufferedImage screencapture = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()) );
File file = new File("Painting1.jpg");
while(file.exists()){
     imageIndex++;
     file = new File("Painting" + imageIndex+ ".jpg");
} 
ImageIO.write(screencapture, "jpg", file);

}catch(Exception e)
{
}JOptionPane.showMessageDialog(saveButton,"Image saved successfully");
                  }
           });
         
        add(redButton);  
        add(greenButton);
        add(blueButton);
        add(yellowButton);
        add(blackButton);
        add(rectangleButton);
        add(ovalButton);
        add(lineButton);
        add(freehandButton);
        add(eraserButton);
        add(clearButton);
        add(dottedCheckbox);
        add(filledCheckbox);
        add(undoButton);
        add(saveButton);
    }
    @Override
    public void paint(Graphics g){
        g2d = (Graphics2D) g;
        if(!shapes.isEmpty())
        {
            undoButton.setEnabled(true);
            clearButton.setEnabled(true);
        }      
        else 
        {
             undoButton.setEnabled(false);
             clearButton.setEnabled(false);
        }
        if(myshape == SelectedDrawingOption.CLEARALL)
            undoButton.setEnabled(true);
        Graphics2D g2 =(Graphics2D) g;
        for(Shape sh: shapes)
        {
         g.setColor(sh.getShapeColor());
         if(sh.isDotted())
           g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10,  new float[]{9}, 0));
         else 
           g2.setStroke(new BasicStroke(0));
         if(sh instanceof Line)
               g.drawLine(((Line)sh).getP1().x, ((Line)sh).getP1().y, ((Line)sh).getP2().x,  ((Line)sh).getP2().y);
         if(sh instanceof paintbrush.Rectangle)
         {
              if(((paintbrush.Rectangle)sh).isFilled())
                    g.fillRect( ((paintbrush.Rectangle)sh).getStartPoint().x,  ((paintbrush.Rectangle)sh).getStartPoint().y, ((paintbrush.Rectangle)sh).getWidth(),  ((paintbrush.Rectangle)sh).getHeight());
              else
                    g.drawRect( ((paintbrush.Rectangle)sh).getStartPoint().x,  ((paintbrush.Rectangle)sh).getStartPoint().y, ((paintbrush.Rectangle)sh).getWidth(),  ((paintbrush.Rectangle)sh).getHeight());
         }  
         if(sh instanceof Oval)
         {
              if(((Oval)sh).isFilled())
                    g.fillOval(((Oval)sh).getStartPoint().x,  ((Oval)sh).getStartPoint().y, ((Oval)sh).getWidth(),  ((Oval)sh).getHeight());
              else
                    g.drawOval(((Oval)sh).getStartPoint().x,  ((Oval)sh).getStartPoint().y, ((Oval)sh).getWidth(),  ((Oval)sh).getHeight());
         }
         if(sh instanceof FreeHand)
              g.fillOval(((FreeHand)sh).getStartPoint().x,  ((FreeHand)sh).getStartPoint().y, ((FreeHand)sh).getWidth(),  ((FreeHand)sh).getHeight());
         if(sh instanceof Eraser)
              g.fillRect(((Eraser)sh).getStartPoint().x,  ((Eraser)sh).getStartPoint().y, ((Eraser)sh).getWidth(),  ((Eraser)sh).getHeight());
        }
        /*for (Line line : lines) {
            g.setColor(line.getShapeColor());
            g.drawLine( line.getP1().x,  line.getP1().y, line.getP2().x,  line.getP2().y);
        }
        for (Rectangle rect : rects) {
            g.setColor(rect.getShapeColor());
            g.drawRect( rect.getStartPoint().x,  rect.getStartPoint().y, rect.getWidth(),  rect.getHeight());
        }
        for (Oval oval : ovals) {
            g.setColor(oval.getShapeColor());
            g.drawOval(oval.getStartPoint().x,  oval.getStartPoint().y, oval.getWidth(),  oval.getHeight());
        }*/
        if(isDragged)
        {
            g.setColor(c);
            if(isDotted)
              g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10,  new float[]{9}, 0));
            else 
               g2.setStroke(new BasicStroke(0));
            if(myshape == SelectedDrawingOption.LINE)
                g.drawLine( p1.x,  p1.y, p2.x,  p2.y);
            if(isFilled)
            {
                if(myshape == SelectedDrawingOption.RECTANGLE)
                    g.fillRect(Math.min(x1, x2),Math.min(y1, y2),Math.abs(x1-x2),Math.abs(y1-y2)) ;
                if(myshape == SelectedDrawingOption.OVAL)
                    g.fillOval(Math.min(x1, x2),Math.min(y1, y2),Math.abs(x1-x2),Math.abs(y1-y2)) ;
            }
            else
            {
               if(myshape == SelectedDrawingOption.RECTANGLE)
                   g.drawRect(Math.min(x1, x2),Math.min(y1, y2),Math.abs(x1-x2),Math.abs(y1-y2)) ;
               if(myshape == SelectedDrawingOption.OVAL)
                   g.drawOval(Math.min(x1, x2),Math.min(y1, y2),Math.abs(x1-x2),Math.abs(y1-y2)) ;
            }
        }
    }
    class MyMouseMotionListener implements MouseMotionListener
    {
        @Override
        public void mouseDragged(MouseEvent e) 
        {
            if(myshape == SelectedDrawingOption.LINE)
            {
             p2 = e.getPoint();
             isDragged = true;
             repaint();   
            } 
           if(myshape == SelectedDrawingOption.RECTANGLE || myshape == SelectedDrawingOption.OVAL)
            {
                x2 = e.getX();
                y2 = e.getY();
                isDragged = true;
                repaint(); 
            }  
           if(myshape == SelectedDrawingOption.FREEHAND)
            {
             shapes.add(new FreeHand(e.getPoint(),c));
             isDragged = true;
             repaint();
            }
            if(myshape == SelectedDrawingOption.ERASER)
            {
             shapes.add(new Eraser(e.getPoint()));
             isDragged = true;
             repaint();
            }

        }
        @Override
        public void mouseMoved(MouseEvent e)
        {
          
        }
        
    }
    class MyMouseListener implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent e) {
      
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            if(myshape == SelectedDrawingOption.LINE )
            {
             p1 = e.getPoint();
             isDragged = false;
             repaint();
            } 
             if(myshape == SelectedDrawingOption.RECTANGLE || myshape == SelectedDrawingOption.OVAL||myshape == SelectedDrawingOption.FREEHAND)
            {
               x1 = e.getX();
               y1 = e.getY();
               isDragged = false;
               repaint();
            } 
            if(myshape == SelectedDrawingOption.FREEHAND)
            {
             shapes.add(new FreeHand(e.getPoint(),c));
             isDragged = false;
            repaint();
            }
            if(myshape == SelectedDrawingOption.ERASER)
            {
             shapes.add(new Eraser(e.getPoint()));
             isDragged = true;
             repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) 
        {
            if(myshape == SelectedDrawingOption.LINE)
            {
            //lines.add(new Line(p1,p2,c));
            shapes.add(new Line(p1,p2,c,isDotted));
            isDragged = false;
            repaint();
            } 
             if(myshape == SelectedDrawingOption.RECTANGLE)
            {
               p_rect = new Point(Math.min(x1, x2),Math.min(y1, y2));
               w_rect = Math.abs(x1-x2);
               h_rect = Math.abs(y1-y2);
               //rects.add(new Rectangle(p_rect,w_rect,h_rect,c));
               shapes.add(new paintbrush.Rectangle(p_rect,w_rect,h_rect,c,isDotted,isFilled));
               isDragged = false;
               repaint();
            } 
            if( myshape == SelectedDrawingOption.OVAL)
            {
               p_rect = new Point(Math.min(x1, x2),Math.min(y1, y2));
               w_rect = Math.abs(x1-x2);
               h_rect = Math.abs(y1-y2);
               //ovals.add(new Oval(p_rect,w_rect,h_rect,c));
               shapes.add(new Oval(p_rect,w_rect,h_rect,c,isDotted,isFilled));
               isDragged = false;
               repaint();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
        }

        @Override
        public void mouseExited(MouseEvent e) {
          
        }
    
    }
}
