import java.awt.*;

/**
 * An Object that represents a piece of food that pigeons can eat.
 * Each piece of food is represented by a stationary circle of radius 5.
 * 
 * @author Michael Li
 * @version January 9, 2019
 */
public class Food
{
    /**
     * The x-coordinate of the top-left corner of the piece of food.
     */
    private final int x;
    
    /**
     * The y-coordinate of the top-left corner of the piece of food.
     */
    private final int y;

    /**
     * This constructor method creates a new piece of food at a given location.
     * 
     * @param x1 The given x-coordinate.
     * @param y1 The given y-coordinate.
     */
    public Food (int x1, int y1)
    {
        x = x1;
        y = y1;
    } // Food(int,int) constructor
    
    
    /**
     * This accessor method return the x-coordinate of the piece of food.
     * 
     * @return The value of the <code>x</code> field.
     * @see x
     */
    public int getX ()
    {
        return x;
    } // getX() method
    
    
    /**
     * This accessor method return the y-coordinate of the piece of food.
     * 
     * @return The value of the <code>ys</code> field.
     * @see y
     */
    public int getY ()
    {
        return y;
    } // getY() method
    
    
    /**
     * This method displays the piece of food using a given Graphics.
     * 
     * @param g The Graphics that will display stuff.
     */
    public void display (Graphics g)
    {
        g.setColor (new Color (150, 75, 0)); // Brown colour
        g.fillOval (x, y, 10, 10);
    } // display(Graphics) method
} // Food class
