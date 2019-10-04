import java.awt.Graphics;
import java.util.ArrayList;

/**
 * An Object representing a colony of pigeons, food that the pigeons eat, and the predators that chase the pigeons.
 * 
 * @author Michael Li
 * @author Kevin Wan
 * @author George Chen
 * @version January 10, 2019
 */
public class Colony
{
    /**
     * A list of all the pigeons in the colony.
     */
    private ArrayList<Pigeon> pigeons;
    
    /**
     * The list of all pigeonholes in the world.
     */
    private ArrayList<Pigeonhole> holes;
    
    /**
     * The list of all foodstuffs in the world.
     */
    private ArrayList<Food> foods;
    
    /**
     * The list of all predators in the world.
     */
    private ArrayList<Predator> predators;
    
    /**
     * The age of the world of pigeons, in frames.
     */
    private int age;
    

    /**
     * Constructor for objects of class Colony
     */
    public Colony ()
    {
        age = 0; // Initialize age
        
        // Initialize list of random pigeons
        pigeons = new ArrayList<> ();
        for (int i = 0; i < 449; i++)
        {
            pigeons.add (new Pigeon ());
        }
        
        // Initialize grid of pigeonholes
        holes = new ArrayList<> ();
        for (int x = 0; x < 640; x += 40)
        {
            for (int y = 0; y < 560; y += 40)
            {
                holes.add (new Pigeonhole (x, y));
            }
        }
        
        // Initialize list of two predators
        predators = new ArrayList<> ();
        for (int i = 0; i < 2; i++)
        {
            predators.add (new Predator ());
        }
        
        // Initialize empty list of foodstuffs
        foods = new ArrayList<> ();
    } // Colony() constructor
    
    
    /**
     * This accessor method returns the age of the colony.
     * 
     * @return The value of the <code>age</code> field.
     * @see age
     */
    public int getAge ()
    {
        return age;
    } // getAge() method
    
    
    /**
     * This method calculates the average base speed of all the pigeons in the colony.
     * 
     * @return The average base speed of all the pigeons in the colony.
     * @see Pigeon.getBaseSpeed()
     * @throw ArithmeticException If the colony is empty.
     */
    public double getAvgSpeed ()
    {
        if (pigeons.isEmpty ())
        {
            throw new ArithmeticException ("The colony is empty."); // Average is undefined
        }
        
        // Loop to determine sum of base speeds
        double sum = 0;
        for (int i = 0; i < pigeons.size (); i++)
        {
            sum += pigeons.get (i).getBaseSpeed ();
        }
        
        return sum / pigeons.size (); // Average = sum / size
    } // getAvgSpeed() method
    
    
    /**
     * This method calculates the average base power of all the pigeons in the colony.
     * 
     * @return The average base power of all the pigeons in the colony.
     * @see Pigeon.getBasePower()
     * @throw ArithmeticException If the colony is empty.
     */
    public double getAvgPower ()
    {
        if (pigeons.isEmpty ())
        {
            throw new ArithmeticException ("The colony is empty."); // Average is undefined
        }
        
        // Loop to determine sum of base powers
        double sum = 0;
        for (int i = 0; i < pigeons.size (); i++)
        {
            sum += pigeons.get (i).getBasePower ();
        }
        
        return sum / pigeons.size (); // Average = sum / size
    } // getAvgPower() method
    
    
    /**
     * This method calculates the average base maximum HP of all the pigeons in the colony.
     * 
     * @return The average base maximum HP of all the pigeons in the colony.
     * @see Pigeon.getBaseHP()
     * @throw ArithmeticException If the colony is empty.
     */
    public double getAvgMaxHP ()
    {
        if (pigeons.isEmpty ())
        {
            throw new ArithmeticException ("The colony is empty."); // Average is undefined
        }
        
        // Loop to determine sum of base maximum HP
        double sum = 0;
        for (int i = 0; i < pigeons.size (); i++)
        {
            sum += pigeons.get (i).getBaseHP ();
        }
        
        return sum / pigeons.size (); // Average = sum / size
    } // getAvgMaxHP() method
    
    
    /**
     * This method calculates the average mutation level of all the pigeons in the colony.
     * 
     * @return The average mutation level of all the pigeons in the colony.
     * @see Pigeon.getMut()
     * @throw ArithmeticException If the colony is empty.
     */
    public double getAvgMut ()
    {
        if (pigeons.isEmpty ())
        {
            throw new ArithmeticException ("The colony is empty."); // Average is undefined
        }
        
        // Loop to determine sum of mutation levels
        double sum = 0;
        for (int i = 0; i < pigeons.size (); i++)
        {
            sum += pigeons.get (i).getMut ();
        }
        
        return sum / pigeons.size (); // Average = sum / size
    } // getAvgMut() method
    
    
    /**
     * This method calculates the average fullness of all the pigeons in the colony.
     * 
     * @return The average fullness of all the pigeons in the colony.
     * @see Pigeon.getFull()
     * @throw ArithmeticException If the colony is empty.
     */
    public double getAvgFull ()
    {
        if (pigeons.isEmpty ())
        {
            throw new ArithmeticException ("The colony is empty."); // Average is undefined
        }
        
        // Loop to determine sum of fullnesses
        double sum = 0;
        for (int i = 0; i < pigeons.size (); i++)
        {
            sum += pigeons.get (i).getFull ();
        }
        
        return sum / pigeons.size (); // Average = sum / size
    } // getAvgFull() method
    
    
    /**
     * This method calculates the percentage of all pigeons in the colony that are sick.
     * 
     * @return The percentage of sick pigeons in the colony.
     * @see Pigeon.isSick()
     * @throw ArithmeticException If the colony is empty.
     */
    public double getDisease ()
    {
        if (pigeons.isEmpty ())
        {
            throw new ArithmeticException ("The colony is empty."); // Percentage is undefined
        }
        
        // Loop to count sick pigeons
        int count = 0;
        for (int i = 0; i < pigeons.size (); i++)
        {
            count += pigeons.get (i).isSick () ? 1 : 0;
        }
        
        return 100.0 * count / pigeons.size (); // Percentage = count / size * 100%
    } // getDisease() method
    
    
    /**
     * This method calculates the percentage of all pigeons in the colony that are old.
     * A pigeon is old if and only if it is at least 9000 frames old.
     * 
     * @return The percentage of old pigeons in the colony.
     * @see Pigeon.getAge()
     * @throw ArithmeticException If the colony is empty.
     */
    public double getOld ()
    {
        if (pigeons.isEmpty ())
        {
            throw new ArithmeticException ("The colony is empty."); // Percentage is undefined
        }
        
        // Loop to count sick pigeons
        int count = 0;
        for (int i = 0; i < pigeons.size (); i++)
        {
            count += (pigeons.get (i).getAge () >= 9000) ? 1 : 0;
        }
        
        return 100.0 * count / pigeons.size (); // Percentage = count / size * 100%
    } // getOld() method
    
    
    /**
     * This method calculates the size of the colony of pigeons.
     * 
     * @return The number of pigeons currently in the colony.
     */
    public int size ()
    {
        return pigeons.size ();
    } // size() method
    
    
    /**
     * This method adds another pigeon to the colony.
     */
    public void add (Pigeon pigeon)
    {
        pigeons.add (pigeon);
    } // add(Pigeon) method
    
    
    /**
     * This method displays all the pigeons in the colony using a given Graphics.
     * 
     * @param g The Graphics that will draw stuff.
     */
    public void display (Graphics g)
    {
        // Loop to display stuff
        for (int i = 0; i < holes.size (); i++)
        {
            holes.get (i).display (g);
        }
        for (int i = 0; i < foods.size (); i++)
        {
            foods.get (i).display (g);
        }
        for (int i = 0; i < pigeons.size (); i++)
        {
            if (pigeons.get (i).isDead ())
            {
                pigeons.remove (i); // Weed out dead pigeons before attempting to display them
                i--;
            }
            else
            {
                pigeons.get (i).display (g);
            }
        }
        for (int i = 0; i < predators.size (); i++)
        {
            predators.get (i).display (g);
        }
    } // display(Graphics) method
    
    
    /**
     * This method determines the cross product of two 2-D vectors.
     * 
     * @param x1 The x-coordinate of the first vector.
     * @param x1 The x-coordinate of the first vector.
     * @param x1 The x-coordinate of the first vector.
     * @param x1 The x-coordinate of the first vector.
     * @return The required cross product.
     */
    private static double cross (double x1, double y1, double x2, double y2)
    {
        return x1 * y2 - y1 * x2;
    } // cross(double,double,double,double) method
    
    
    /**
     * This method simulates one frame (or iteration) of the world.
     */
    public void move ()
    {
        if (PigeonholeSimulation.DEBUG && age % 30 == 0)
        {
            System.out.println ("Age: " + age); // Get the colony's age
        }

        // Loop through predators to move them
        for (int i = 0; i < predators.size (); i++)
        {
            Predator p = predators.get (i);
            Pigeon closest = null;
            double minDist = 1e99;
            
            // Loop to search for closest victim
            for (int j = 0; j < pigeons.size (); j++)
            {
                Pigeon pigeon = pigeons.get (j);
                
                double dist = Math.sqrt ((pigeon.getX () - p.getX ()) * (pigeon.getX () - p.getX ())
                                        + (pigeon.getY () - p.getY ()) * (pigeon.getY () - p.getY ()));
                                        
                if (dist < minDist && pigeon.isHomeless ()) // Closest pigeon that is not hiding in a pigeonhole
                {
                    // Make sure that the predator can go towards the pigeon
                    if (pigeon.getX () > 15 && pigeon.getY () > 15 && pigeon.getX () < 620 && pigeon.getY () < 560)
                    {
                        closest = pigeon;
                        minDist = dist;
                    }
                }
            }
            try
            {
                // Move toward closest pigeon
                p.move (closest.getX () - p.getX (), closest.getY () - p.getY ());
            }
            catch (NullPointerException ex) // No pigeon to go to
            {
                p.move (Math.random () * 2e99 - 1e99, Math.random () * 2e99 - 1e99); // Random motion while waiting
            }
            
            // Loop to check for pigeons to eat
            for (int j = 0; j < pigeons.size (); j++)
            {
                Pigeon pigeon = pigeons.get (j);
                if (pigeon.isHomeless ()) // Only homeless pigeons are vulnerable
                {
                    if (p.getX () < pigeon.getX () + 5 && p.getX () + 10 > pigeon.getX ()
                        && p.getY () < pigeon.getY () + 5 && p.getY () + 10 > pigeon.getY ()) // Check boundaries
                    {
                        pigeons.remove (j); // Pigeon is now eaten up
                        j--;
                    }
                }
            }
        }
        
        // Loop through pairs of pigeons to simulate attacks
        for (int i = 0; i < pigeons.size (); i++)
        {
            if (pigeons.get (i).isDesperate ()) // Only desperate pigeons would attack other pigeons
            {
                for (int j = 0; j < pigeons.size (); j++)
                {
                    try {// Check for fighting conditions
                        if ((!pigeons.get(j).isDesperate() || j > i) // Avoid double-counting attacks
                                && pigeons.get(i).isHomeless() && pigeons.get(j).isHomeless()
                                && Math.abs(pigeons.get(i).getX() - pigeons.get(j).getX()) < 5
                                && Math.abs(pigeons.get(i).getY() - pigeons.get(j).getY()) < 5) {
                            pigeons.get(i).attack(pigeons.get(j)); // The pigeons attack each other
                        }

                        // Check for dead pigeons
                        if (pigeons.get(j).isDead()) {
                            pigeons.remove(j);
                            j--;
                        } else if (pigeons.get(i).isDead()) {
                            pigeons.remove(i);
                            i--;
                            j = pigeons.size(); // Break out of loop
                        }
                    }catch(Exception e){

                    }
                }
            }
        }
        
        // Loop to move pigeons
        for (int i = 0; i < pigeons.size (); i++)
        {
            Pigeon pigeon = pigeons.get (i); // The current pigeon
            boolean danger = !pigeon.isSafe (predators); // Whether or not the pigeon is in danger from predators

            // Very confusing motion algorithm
            double dx = 0, dy = 0;

            if (danger) // Stop everything else and fly away from danger
            {
                Predator near = null;
                double minDist = 1e99;
                
                // Loop to search for closest predator to fly away from
                for (int j = 0; j < predators.size (); j++)
                {
                    Predator p = predators.get (j);
                    double dist = Math.sqrt ((pigeon.getX () - p.getX ()) * (pigeon.getX () - p.getX ())
                                            + (pigeon.getY () - p.getY ()) * (pigeon.getY () - p.getY ()));
                    
                    if (dist < minDist)
                    {
                        minDist = dist;
                        near = p;
                    }
                }
                
                // Try to move away from predator
                double deltaX = pigeon.getX () - near.getX ();
                double deltaY = pigeon.getY () - near.getY ();
                double weight = Math.sqrt (3600 - deltaX * deltaX - deltaY * deltaY); // Scale reaction based on proximity
                dx = deltaX * weight;
                dy = deltaY * weight;
            }

            if (pigeon.getFull () < 0.5 && !pigeon.isBreeding ()) // Pigeon is hungry and is not busy breeding
            {
                if (pigeon.isSafe (predators, pigeon.getX (), pigeon.getY ()))
                {
                    pigeon.exitHole (); // Should leave hole to search for food and safe to do so
                }
                else if (pigeon.getFull () < 0.2)
                {
                    pigeon.exitHole (); // Pigeon is just too hungry and will die if it doesn't leave the hole
                }
                
                if (pigeon.isHomeless ()) // Pigeon can move around to look for food
                {
                    // Declare variables to search for closest food
                    double minDist = 1e99;
                    Food closeFood = null;
                    double pigeonDist = 1e99;
                    Pigeon closePigeon = null;
                    
                    // Loop to search for nearby food
                    for (int j = 0; j < foods.size (); j++)
                    {
                        Food food = foods.get (j);
                        double dist = Math.sqrt ((pigeon.getX () - food.getX ()) * (pigeon.getX () - food.getX ())
                                                + (pigeon.getY () - food.getY ()) * (pigeon.getY () - food.getY ()));
                        if (dist < minDist && Pigeon.isSafe (predators, food.getX (), food.getY ())) // Closer food that is safe
                        {
                            minDist = dist;
                            closeFood = food;
                        }
                    }
                    
                    // If desperate, try to eat the nearest pigeon if closer than food
                    if (pigeon.isDesperate ())
                    {
                        // Loop to determine closest pigeon
                        for (int j = 0; j < pigeons.size (); j++)
                        {
                            if (i != j && pigeons.get (j).isHomeless ()) // Can only target shelterless pigeons
                            {
                                Pigeon target = pigeons.get (j);
                                double dist = Math.sqrt ((pigeon.getX () - target.getX ()) * (pigeon.getX () - target.getX ())
                                                    + (pigeon.getY () - target.getY ()) * (pigeon.getY () - target.getY ()));
                                if (dist < pigeonDist && Pigeon.isSafe (predators, target.getX (), target.getY ()))
                                {
                                    pigeonDist = dist;
                                    closePigeon = target;
                                }
                            }
                        }
                    }
                    try
                    {
                        // Declare variables to help with moving towards food
                        double mx;
                        double my;
                        
                        // Move toward closest food if closer than closest pigeon
                        if (minDist <= pigeonDist)
                        {
                            mx = closeFood.getX () - pigeon.getX ();
                            my = closeFood.getY () - pigeon.getY ();
                        }
                        else // Move toward closest pigeon if closer than closest food
                        {
                            mx = closePigeon.getX () - pigeon.getX ();
                            my = closePigeon.getY () - pigeon.getY ();
                            if (!pigeon.isDesperate () && PigeonholeSimulation.DEBUG)
                            {
                                System.out.println ("FIGHTING ERROR!");
                            }
                        }
                        
                        pigeon.move (mx + dx, my + dy); // Adjust motion to go toward food
                    }
                    catch (NullPointerException ex) // No food to go to
                    {
                        pigeon.move (0, 1e99); // Move down to wait for food
                    }
                }
            }
            else
            {
                if (pigeon.isHomeless ()) // Pigeon should search for a pigeonhole
                {
                    double minDist = 1e99;
                    Pigeonhole closeHole = null;
    
                    double ax = dy, ay = -dx; // The pigeon isn't allowed to travel in a direction contained by these vectors
                    double bx = -dy, by = dx; // as it would mean the pigeon is moving closer to a predator
    
                    // Loop to search for nearby breeding or hiding opportunities
                    for (int j = 0; j < holes.size (); j++)
                    {
                        Pigeonhole hole = holes.get (j);
                        
                        // Check for breeding or hiding opportunity
                        if (hole.canBreed (pigeon.getGender ()) || (danger && !hole.getFilled (pigeon.getGender ())))
                        {
                            double cx = hole.getX (pigeon) - pigeon.getX ();
                            double cy = hole.getY (pigeon) - pigeon.getY ();
                            
                            // Check if moving in this direction would make the pigeon move closer to a predator
                            if (cross (ax, ay, cx, cy) * cross (ax, ay, bx, by) >= 0
                                && cross (bx, by, cx, cy) * cross(bx, by, ax, ay) >= 0)
                            {
                                continue; // don't consider this pigeonhole
                            }
                            
                            double dist = Math.sqrt ((pigeon.getX () - hole.getX (pigeon)) * (pigeon.getX () - hole.getX (pigeon))
                                                + (pigeon.getY () - hole.getY (pigeon)) * (pigeon.getY () - hole.getY (pigeon)));
                            if (dist < minDist)
                            {
                                minDist = dist;
                                closeHole = hole;
                            }
                        }
                    }
                    if (closeHole == null && !danger) // No breeding opportunities
                    {
                        // Loop to search for nearby pigeonholes
                        for (int j = 0; j < holes.size (); j++)
                        {
                            Pigeonhole hole = holes.get (j);
                            if (!hole.getFilled (pigeon.getGender ())) // Check for unfilled pigeonhole
                            {
                                double dist = Math.sqrt ((pigeon.getX () - hole.getX (pigeon)) * (pigeon.getX () - hole.getX (pigeon))
                                                    + (pigeon.getY () - hole.getY (pigeon)) * (pigeon.getY () - hole.getY (pigeon)));
                                if (dist < minDist)
                                {
                                    minDist = dist;
                                    closeHole = hole;
                                }
                            }
                        }
                    }
    
                    try
                    {
                        // Move toward closest pigeonhole
                        pigeon.move (closeHole.getX (pigeon) - pigeon.getX (), closeHole.getY (pigeon) - pigeon.getY ());
                    }
                    catch (NullPointerException ex) // No pigeonhole to go to
                    {
                        pigeon.move (dx, dy); // Random motion while waiting
                    }
                }
            }
            
            if (pigeon.isHomeless ()) // Actions for pigeons outside of pigeonholes
            {
                if (pigeon.getFull () < 0.5 && pigeon.getY () >= 555) // Pigeon is allowed to eat and is in food region
                {
                    // Check for nearby food to eat
                    for (int j = 0; j < foods.size (); j++)
                    {
                        Food food = foods.get (j);
                        
                        // Check if pigeon coincides with food
                        if (pigeon.getX () < food.getX () + 10 && pigeon.getX () + 5 > food.getX ())
                        {
                            if (pigeon.getY () < food.getY () + 10 && pigeon.getY () + 5 > food.getY ())
                            {
                                pigeon.eat (); // Pigeon is close enough to eat food
                                
                                foods.remove (j); // Remove eaten food
                                
                                j = foods.size (); // Break out of loop
                            }
                        }
                    }
                }
            
                // Check for nearby pigeonholes to enter
                for (int j = 0; j < holes.size (); j++)
                {
                    Pigeonhole hole = holes.get (j);
                    
                    // Check if pigeon coincides with pigeonhole
                    if (Math.floor (pigeon.getX ()) > hole.getX (pigeon)) // Pigeonhole is too far to the left
                    {
                        j += 13; // Skip an entire column of pigeonholes
                    }
                    else if (Math.floor (pigeon.getX ()) < hole.getX (pigeon)) // Remaining pigeonholes are too far to the right
                    {
                        j = holes.size (); // Skip all the pigeonholes
                    }
                    else if (Math.floor (pigeon.getY ()) < hole.getY (pigeon)) // Remaining pigeonholes are too far down
                    {
                        j = holes.size (); // Skip all the pigeonholes
                    }
                    else if (Math.floor (pigeon.getY ()) == hole.getY (pigeon)) // Pigeon coincides with pigeonhole
                    {
                        // Check if pigeon can enter hole
                        if (!hole.getFilled (pigeon.getGender ()))
                        {
                            pigeon.enterHole (hole); // Enter the pigeonhole
                        }
                        
                        j = holes.size (); // Skip the remaining pigeonholes
                    }
                }
            }
            
            pigeons.get (i).checkHealth (); // Check health
            
            if (pigeons.get (i).isDead ()) // Need to remove dead pigeons
            {
                pigeons.remove (i);
                i--;
            }
        }
            
        // Loop through pigeonholes to check for breeding
        for (int j = 0; j < holes.size (); j++)
        {
            holes.get (j).checkBreeding ();
        }
        
        // Loop to randomly place/remove food in the food section seasonally
        double lowSeason = (620.0 - PigeonholeSimulation.minSlideLoc) / 200;
        double highSeason = (620.0 - PigeonholeSimulation.maxSlideLoc) / 200;
        
        // Model the seasons with sinusoidal curve
        double numFood = (lowSeason + highSeason) / 2
                        + (highSeason - lowSeason) / 2 * Math.sin (Math.PI * age / 3600);
                        
        // Loop to place required amount of food
        for (int i = 0; i < (int) numFood; i++)
        {
            foods.add (new Food ((int) (Math.random () * 630), 560 + (int) (70 * Math.random ()))); // Select a random location
        }
        if (Math.random () < numFood % 1) // Express remaining food as a probability
        {
            foods.add (new Food ((int) (Math.random () * 630), 560 + (int) (70 * Math.random ()))); // Select a random location
        }
        
        double numRem = 0.005 * foods.size (); // Remove food based on current amount of food
        
        // Loop to remove required amount of food
        for (int i = 0; i < (int) numRem; i++)
        {
            foods.remove (0); // Remove the oldest food
        }
        if (Math.random () < numRem % 1) // Express remaining removals as a probability
        {
            foods.remove (0); // Remove the oldest food
        }
                                        
        // Modify speed of predators every 1000 frames to promote evolution
        if (age % 1000 == 0)
        { 
            for (int j = 0; j < predators.size (); j++)
            {
                try
                {
                    // Set predator to around average speed to eliminate slower-than-average pigeons
                    predators.get (j).setSpeed (getAvgSpeed () * (1.05 + Math.random () * 0.1));
                }
                catch (ArithmeticException ex)
                {
                    predators.get (j).setSpeed (0); // Predators don't have to move if every pigeon is already dead
                }
            }
        }
        
        if (PigeonholeSimulation.DEBUG && age % 30 == 0)
        {
            System.out.println ("Alive: " + pigeons.size ()); // For detecting too much/not enough death
        }
        
        GraphGUI.addStats (); // Add new stats to be displayed in graph

        age++; // Increment age
    } // move() method
} // Colony class
