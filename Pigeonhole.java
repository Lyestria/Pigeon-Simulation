import java.awt.*;

/**
 * An Object that represents a pigeonhole that pigeons live in.
 * Each pigeonhole has a size of 40 by 40, and the hole itself is a circle of radius 30.
 * 
 * @author Michael Li
 * @version January 9, 2019
 */
public class Pigeonhole
{
    /**
     * The x-coordinate of the top left corner of the pigeonhole.
     */
    private final int x;
    
    /**
     * The y-coordinate of the top left corner of the pigeonhole.
     */
    private final int y;
    
    /**
     * The pigeon of each gender inside the pigeonhole.
     */
    private Pigeon[] filled;
    
    /**
     * The amount of time that the pigeons inside the pigeonhole have been breeding for.
     * It takes 150 frames of undisturbed breeding to successfully create a baby.
     */
    private int breedTimer;

    /**
     * This constructor method creates a new empty pigeonhole at a given location.
     * 
     * @param x1 The x-coordinate of the pigeonhole.
     * @param y1 The y-coordinate of the pigeonhole.
     */
    public Pigeonhole (int x1, int y1)
    {
        // Set variables
        x = x1;
        y = y1;
        filled = new Pigeon[2];
        filled[0] = filled[1] = null;
        breedTimer = 0;
    } // Pigeonhole(int,int) constructor
    
    
    /**
     * This accessor method returns the x-coordinate of the entrance point of a given pigeon.
     * Males should be placed at <code>(x + 20, y + 18)</code>, while females should be placed at <code>(x + 15, y + 18)</code>
     * 
     * @param pigeon The pigeon to be checked.
     * @return The x-coordinate of the entrance point of the pigeon.
     */
    public int getX (Pigeon pigeon)
    {
        return x + ((pigeon.getGender () == 0) ? 20 : 15);
    } // getX(Pigeon) method
    
    
    /**
     * This accessor method returns the y-coordinate of the entrance point of a given pigeon.
     * Males should be placed at <code>(x + 20, y + 18)</code>, while females should be placed at <code>(x + 15, y + 18)</code>
     * 
     * @param pigeon The pigeon to be checked.
     * @return The y-coordinate of the entrance point of the pigeon.
     */
    public int getY (Pigeon pigeon)
    {
        return y + 18;
    } // getY(Pigeon) method
    
    
    /**
     * This method determines whether or not a pigeon of a certain gender would be able to breed inside this pigeonhole.
     * 
     * @param gender An integer representing the gender to be checked.
     * @return Whether or not the pigeon has a breeding opportunity here.
     */
    public boolean canBreed (int gender)
    {
        // Check to see if pigeon can enter hole, pigeon of opposite gender exists, and pigeon of opposite gender can breed
        return filled[gender] == null && filled[1 - gender] != null && filled[1 - gender].canBreed ();
    } // canBreed(int) method
    
    
    /**
     * This accessor method determines whether or not the pigeonhole has a pigeon of a given gender.
     */
    public boolean getFilled (int gender)
    {
        return filled[gender] != null;
    } // getFilled(int) method
    
    
    /**
     * This method displays the pigeonhole using a given Graphics.
     * 
     * @param g The Graphics that will display stuff.
     */
    public void display (Graphics g)
    {
        g.setColor (Color.blue);
        g.fillRect (x, y, 40, 40);
        g.setColor (Color.black);
        g.fillOval (x + 5, y + 5, 30, 30);
    } // display(Graphics) method
    
    
    /**
     * This method tries to fill the pigeonhole with a given pigeon.
     * 
     * @param pigeon The pigeon that will try to enter the pigeonhole.
     * @throw IllegalArgumentException If a pigeon of the same gender already occupies the pigeonhole,
     *                                  or if the pigeon is not at the entrance point.
     */
    public void fill (Pigeon pigeon)
    {
        if (filled[pigeon.getGender ()] != null) // Check for filled pigeonhole
        {
            throw new IllegalArgumentException ("A pigeon of that gender already occupies the pigeonhole.");
        }
        if (Math.floor (pigeon.getX ()) != getX (pigeon) || Math.floor (pigeon.getY ()) != getY (pigeon)) // Check locations
        {
            throw new IllegalArgumentException ("The pigeon is too far away to enter the pigeonhole.");
        }
        
        filled[pigeon.getGender ()] = pigeon; // Store the pigeon
    } // fill(Pigeon) method
    
    
    /**
     * This method tries to release a given pigeon from the pigeonhole.
     * 
     * @param pigeon The pigeon to be released.
     * @throw IllegalArgumentException If the pigeonhole does not contain the required pigeon.
     */
    public void release (Pigeon pigeon)
    {
        if (filled[pigeon.getGender ()] == pigeon) // Contains the correct pigeon
        {
            filled[pigeon.getGender ()] = null; // No longer contains pigeon
            breedTimer = 0; // Can no longer breed without pigeons
        }
        else
        {
            throw new IllegalArgumentException ("The pigeon is already outside the pigeonhole.");
        }
    } // release(Pigeon) method
    
    
    /**
     * This method checks breeding processes inside the pigeonhole and adds a new baby to the colony if breeding is successful.
     * 
     * @see breedTimer
     */
    public void checkBreeding ()
    {
        // Check for dead pigeons that can't breed
        if (filled[0] != null && filled[0].isDead ())
        {
            filled[0] = null;
        }
        if (filled[1] != null && filled[1].isDead ())
        {
            filled[1] = null;
        }
        if (filled[0] == null || filled[1] == null)
        {
            return;
        }
        
        if (breedTimer == 0)
        {
            // Check for eligible parents
            if (filled[0] != null && filled[0].canBreed () && filled[1] != null && filled[1].canBreed ())
            {
                // Start breeding
                filled[0].startBreeding ();
                filled[1].startBreeding ();
                breedTimer++;
            }
        }
        else // Currently breeding
        {
            // Make sure pigeons are still inside pigeonhole and not too old
            if (filled[0] != null && filled[0].getAge () < 9000 && filled[1] != null && filled[1].getAge () < 9000)
            {
                breedTimer++;
            }
            else
            {
                breedTimer = 0; // No longer breeding
            }
            if (breedTimer == 150) // Done breeding
            {
                breedTimer = 0;
                PigeonholeSimulation.colony.add (new Pigeon (filled[0], filled[1]));
            }
        }
    } // checkBreeding() method
} // Pigeonhole class
