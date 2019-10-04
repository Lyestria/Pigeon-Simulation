import java.awt.*;

/**
 * An Object that represents a predator that chases and eats pigeons.
 * Each predator is represented with a 10 by 10 square.
 * Predators are not allowed to go within 20 pixels of an edge and are not allowed to go into the food region.
 * 
 * @author George Chen
 * @author Michael Li
 * @version January 12, 2019
 */
public class Predator
{
    /**
     * The maximum possible speed of the predator, in pixels per frame.
     */
    private double speed;
    
    /**
     * The x-coordinate of the top left corner of the predator.
     */
    private double x;
    
    /**
     * The y-coordinate of the top left corner of the predator.
     */
    private double y;
    

    /**
     * This constructor method creates a new random predator at the top edge of the world.
     */
    public Predator ()
    {
        x = Math.random () * 630;
        y = 0; // Spawn at top edge
        speed = Math.random () * 0.5 + 1;
    } // Predator() constructor
    

    /**
     * This accessor method returns the x-coordinate of the top left corner of the predator.
     * 
     * @return The value of the <code>x</code> field.
     * @see x
     */
    public double getX ()
    {
        return x;
    } // getX() method
    

    /**
     * This accessor method returns the y-coordinate of the top left corner of the predator.
     * 
     * @return The value of the <code>y</code> field.
     * @see y
     */
    public double getY ()
    {
        return y;
    } // getY() method
    

    /**
     * This accessor method returns the maximum possible speed of the predator.
     * 
     * @return The value of the <code>speed</code> field.
     * @see speed
     */
    public double getSpeed ()
    {
        return speed;
    } // getSpeed() method
    

    /**
     * This method displays the predator using a given Graphics.
     * 
     * @param g The Graphics that will display stuff.
     */
    public void display (Graphics g)
    {
        g.setColor (new Color (255, 160, 0)); // Orange colour
        g.fillRect ((int) x, (int) y, 10, 10);
    } // display(Graphics) method

    
    /**
     * This method sets the speed of the predator to a new given speed.
     * 
     * @param newSpeed The new given speed of the predator.
     */
    public void setSpeed (double newSpeed)
    {
        speed = newSpeed;
    } // setSpeed(double) method
    
    
    /**
     * This method causes the predator to move in a given way, limited by its speed.
     * Note that the predator is not allowed to go in the food region.
     * Note that the predator is not allowed to go within 20 pixels of an edge.
     * 
     * @param dx The given change in x-coordinate.
     * @param dy The given change in y-coordinate.
     */
    public void move (double dx, double dy)
    {
        // Check boundaries
        if (x + dx < 20)
        {
            dx = 20 - x;
        }
        if (x + dx > 610)
        {
            dx = 610 - x;
        }
        if (y + dy < 20)
        {
            dy = 20 - y;
        }
        if (y + dy > 550) // Food region is at y >= 560
        {
            dy = 550 - y;
        }
        
        // Check to see if maximum speed is exceeded
        double dist = Math.sqrt (dx * dx + dy * dy);
        if (dist > getSpeed ())
        {
            // Scale down the motion vector
            dx *= getSpeed () / dist;
            dy *= getSpeed () / dist;
        }
        
        // Change the position fields
        x += dx;
        y += dy;
    } // move() method
} // Predator class
