import java.awt.*;
import java.util.ArrayList;

/**
 * An Object that represents a pigeon, the main creatures in the simulation.
 * Each pigeon is 5 pixels tall and 5 pixels wide.
 * 
 * @author Michael Li
 * @author Kevin Wan
 * @author George Chen
 * @version January 10, 2019
 */
public class Pigeon
{
    /**
     * The amount of health points that the pigeon has.
     * This value drops when the pigeon fights and increases when the pigeon heals.
     * The pigeon dies if it has no health points left.
     */
    private double hp;
    
    /**
     * The maximum possible HP that the pigeon could possibly have.
     * This value increases as pigeons develop and gain more potential strength.
     * 
     * @see hp
     */
    private double maxHP;
    
    /**
     * The maximum possible HP of the pigeon when it is born.
     * 
     * @see maxHP
     */
    private double baseMaxHP;
    
    /**
     * The age of the pigeon, in frames.
     * If pigeons get too old, they will start getting weaker.
     */
    private int age;
    
    /**
     * The power level of the pigeon.
     * This value increases as pigeons develop and gain more potential strength.
     */
    private double power;
    
    /**
     * The power level of the pigeon when it is born.
     * 
     * @see power
     */
    private double basePower;
    
    /**
     * The x-coordinate of the top left corner of the pigeon.
     */
    private double x;
    
    /**
     * The y-coordinate of the top left corner of the pigeon.
     */
    private double y;
    
    /**
     * The maximum possible speed of the pigeon, in pixels per frame.
     * This value increases as pigeons develop and gain more potential strength.
     */
    private double maxSpeed;
    
    /**
     * The flying speed of the pigeon when it is born.
     * 
     * @see maxSpeed
     */
    private double baseSpeed;
    
    /**
     * Whether or not the pigeon has a disease.
     * If a pigeon has a disease, its stats get lowered temporarily.
     */
    private boolean disease;
    
    /**
     * A real number from 0.02 to 0.2 representing how prone the pigeon is to mutation.
     */
    private double mut;
    
    /**
     * A real number from 0 to 1 representing how full the pigeon is.
     * If this field reaches zero, the pigeon starves to death.
     */
    private double full;
    
    /**
     * An integer representing the gender of the pigeon.
     * 0 - male, 1 - female.
     */
    private int gender;
    
    /**
     * The amount of breeding cooldown, in frames, that the pigeon has to wait for.
     */
    private int cooldown;

    /**
     * The distance at which a pigeon feels safe from a predator, in pixels.
     */
    static final double safety = 60;
    
    /**
     * The pigeonhole that this pigeon is currently occupying.
     * This value is equal to <code>null</code> if the pigeon is homeless.
     */
    private Pigeonhole home;
    
    
    /**
     * This constructor method creates a new completely random pigeon.
     */
    public Pigeon ()
    {
        // Randomize parameters
        x = Math.random () * 635;
        y = Math.random () * 555;
        gender = (int) (Math.random () * 2);
        baseMaxHP = Math.random () * 10 + 15;
        basePower = Math.random () * 2 + 2;
        baseSpeed = Math.random () * 0.6 + 1.2;
        mut = Math.random () * 0.18 + 0.02;
        disease = Math.random () < 0.01;
        full = Math.random () * 0.5 + 0.5; // Starts off full but not too full
        
        // Fixed initial parameters
        hp = maxHP = baseMaxHP;
        power = basePower;
        maxSpeed = baseSpeed;
        age = cooldown = 0;
        home = null;
    } // Pigeon() constructor
    
    
    /**
     * This constructor method creates a baby pigeon from two breeding pigeons.
     * The base stats of the baby pigeon are decided based on the base stats and mutation chances of the adult pigeons.
     * 
     * @param p1 The first adult pigeon.
     * @param p2 The second adult pigeon.
     * @throw IllegalArgumentException If the two pigeons are unable to breed together.
     */
    public Pigeon (Pigeon p1, Pigeon p2)
    {
        // Check for bad parents
        if (p1.gender == p2.gender || p1.age < 1800 || p1.age >= 9000 || p2.age < 1800 || p2.age >= 9000)
        {
            throw new IllegalArgumentException ("The two pigeons cannot breed with each other.");
        }
        
        // Take averages of base stats
        baseMaxHP = (p1.baseMaxHP + p2.baseMaxHP) / 2;
        basePower = (p1.basePower + p2.basePower) / 2;
        baseSpeed = (p1.baseSpeed + p2.baseSpeed) / 2;
        mut = (p1.mut + p2.mut) / 2;
        // Get disease if either parent is diseased, otherwise get 1% chance of disease
        disease = p1.disease || p2.disease || Math.random () < 0.01;
        // Baby is born between two parent pigeons
        x = (p1.x + p2.x) / 2;
        y = (p1.y + p2.y) / 2;
        
        // Mutations
        mut *= (Math.random () * 2 * mut + 1 - mut);
        mut = Math.max (Math.min (0.2, mut), 0.02); // Keep between 0.02 and 0.2
        baseMaxHP *= Math.random () * 2 * mut + 1 - mut;
        basePower *= Math.random () * 2 * mut + 1 - mut;
        baseSpeed *= Math.random () * 2 * mut + 1 - mut;
        
        full = Math.random () * 0.5 + 0.5; // Starts off full but not too full
        gender = (int) (Math.random () * 2); // Random gender
        
        // Fixed initial parameters
        hp = maxHP = baseMaxHP;
        power = basePower;
        maxSpeed = baseSpeed;
        age = cooldown = 0;
        home = null;
    } // Pigeon(Pigeon,Pigeon) constructor
    
    
    /**
     * This accessor method returns the mutation level of the pigeon.
     * 
     * @return The value of the <code>mut</code> field.
     * @see mut
     */
    public double getMut ()
    {
        return mut;
    } // getMut() method
    
    
    /**
     * This accessor method returns the power level of the pigeon.
     * Note that the pigeon's power level gets cut by half if it has a disease.
     * 
     * @return The value of the <code>power</code> field, divided by 2 if the pigeon has a disease.
     * @see power
     */
    public double getPower ()
    {
        return power / (disease ? 2 : 1);
    } // getPower() method
    
    
    /**
     * This accessor method returns the base power level of the pigeon.
     * 
     * @return The value of the <code>basePower</code> field.
     * @see basePower
     */
    public double getBasePower ()
    {
        return basePower;
    } // getBasePower() method
    
    
    /**
     * This accessor method returns the maximum HP for the pigeon.
     * 
     * @return The value of the <code>maxHP</code> field.
     * @see maxHP
     */
    public double getMaxHP ()
    {
        return maxHP;
    } // getMaxHP() method
    
    
    /**
     * This accessor method returns the base maximum HP of the pigeon.
     * 
     * @return The value of the <code>baseMaxHP</code> field.
     * @see baseMaxHP
     */
    public double getBaseHP ()
    {
        return baseMaxHP;
    } // getBaseHP() method
    
    
    /**
     * This accessor method return the x-coordinate of the pigeon.
     * 
     * @see x
     */
    public double getX ()
    {
        return x;
    } // getX() method
    
    
    /**
     * This accessor method return the y-coordinate of the pigeon.
     * 
     * @see y
     */
    public double getY ()
    {
        return y;
    } // getY() method
    
    
    /**
     * This accessor method returns a number representing how full the pigeon is.
     * 
     * @return The value of the <code>full</code> field.
     * @see full
     */
    public double getFull ()
    {
        return full;
    } // getFull() method
    
    
    /**
     * This accessor method returns the maximum possible speed of the pigeon.
     * Note that the pigeon's speed gets cut by 75% if it has a disease.
     * 
     * @return The value of the <code>maxSpeed</code> field, multiplied by 0.25 if the pigeon has a disease.
     * @see maxSpeed
     */
    public double getSpeed ()
    {
        return maxSpeed * (disease ? 0.25 : 1);
    } // getSpeed() method
    
    
    /**
     * This accessor method returns the base speed of the pigeon.
     * 
     * @return The value of the <code>baseSpeed</code> field.
     * @see baseSpeed
     */
    public double getBaseSpeed ()
    {
        return baseSpeed;
    } // getBaseSpeed() method
    
    
    /**
     * This accessor method returns the age, in frames, of the pigeon.
     * 
     * @return The value of the <code>age</code> field.
     * @see age
     */
    public int getAge ()
    {
        return age;
    } // getAge() method
    
    
    /**
     * This accessor method invades the pigeon's privacy and returns an integer representing its gender.
     * 
     * @return The value of the <code>gender</code> field.
     */
    public int getGender ()
    {
        return gender;
    } // getGender() method
    
    
    /**
     * This method determines whether or not the pigeon is currently dead.
     * The pigeon dies by fighting other pigeons too much or by starving to death.
     * 
     * @return <code>true<code>, if and only if the pigeon is dead.
     * @see hp
     * @see full
     */
    public boolean isDead ()
    {
        return hp <= 0 || full <= 0;
    } // isDead() method
    
    
    /**
     * This method determines whether or not the pigeon is able to start breeding.
     * Pigeons cannot breed if they are too young (< 1800 frames) or too old (>= 9000 frames), or if they are cooling down.
     * 
     * @return <code>true</code> if and only if the pigeon is able to breed.
     */
    public boolean canBreed ()
    {
        return age >= 1800 && age < 8850 && cooldown <= 0; // Also need to account for 150 frame breeding time
    } // canBreed() method

    
    /**
     * This method determines whether or not the pigeon is desperate enough for food to attack other pigeons.
     * A pigeon is desperate if and only if it is less than 20% full.
     * 
     * @return <code>true</code> if and only if <code>full</code> is less than 0.20.
     * @see full
     */
    public boolean isDesperate ()
    {
        return full < 0.20;
    } // isDesperate() method
    
    
    /**
     * This method determines whether or not the pigeon is currently homeless.
     * 
     * @return <code>true</code> if and only if the pigeon is not currently inside a pigeonhole.
     * @see home
     */
    public boolean isHomeless ()
    {
        return home == null;
    } // isHomeless() method
    
    
    /**
     * This method determines whether or not the pigeon is busy creating a baby.
     * The pigeon is considered to be currently breeding if the cooldown time is at least 750.
     * 
     * @see cooldown
     */
    public boolean isBreeding ()
    {
        return cooldown >= 750;
    } // isBreeding() method
    
    
    /**
     * This method determines whether or not the pigeon currently has a disease.
     * 
     * @return <code>true</code> if and only if the pigeon currently has a disease.
     * @see disease
     */
    public boolean isSick ()
    {
        return disease;
    } // isSick() method
    
    
    /**
     * This method displays the pigeon using a given Graphics.
     * 
     * @param g The Graphics that will draw stuff.
     */
    public void display (Graphics g)
    {
        // Draw a small square to represent pigeon
        double scale = 0;
        if (PigeonholeSimulation.displayCB.getSelectedItem ().equals ("Individual fullness"))
        {
            scale = 2 * Math.min (0.5, full); // Darkness depends on fullness
        }
        else if (PigeonholeSimulation.displayCB.getSelectedItem ().equals ("Individual HP"))
        {
            scale = hp / maxHP; // Darkness depends on HP
        }
        else if (PigeonholeSimulation.displayCB.getSelectedItem ().equals ("Individual disease"))
        {
            scale = disease ? 0.5 : 1; // Darkness depends on disease
        }
        if (gender == 0) // Male pigeon
        {
            // Blue colour, darkness depends on fullness
            g.setColor (new Color ((int) (scale * 200), (int) (scale * 200), (int) (scale * 255))); // Blue colour
        }
        else // Female pigeon
        {
            // Pink colour, darkness depends on HP
            g.setColor (new Color ((int) (scale * 255), (int) (scale * 200), (int) (scale * 200))); // Pink colour
        }
        g.fillRect ((int) x, (int) y, 5, 5);
    } // display(Graphics) method
    
    
    /**
     * This method activates whenever the pigeon starts attacking another pigeon.
     * All attacks are two-way, and diseased pigeons can transmit diseases during attacks.
     * If one pigeon dies from the attack, the other pigeon eats the loser and also grows stronger.
     * 
     * @param other The other pigeon that is being attacked.
     */
    public void attack (Pigeon other)
    {
        this.hp -= other.getPower ();
        this.disease |= other.disease && (Math.random () < 0.1); // 10% chance of transmitting disease
        other.hp -= this.getPower ();
        other.disease |= this.disease && (Math.random () < 0.1); // 10% chance of transmitting disease
        
        if (other.hp <= 0 && this.hp / other.getPower () >= other.hp / this.getPower ())
        {
            // Gain stats from growing stronger
            this.power *= 1.2;
            this.maxSpeed *= 1.05;
            
            // Only desperate or almost dead pigeons should eat diseased pigeons
            if (this.isDesperate () || this.isDead () || !other.disease)
            {
                // Gain stats from eating pigeon
                this.full = 1.5;
                this.hp = Math.min (this.hp + this.maxHP / 2, this.maxHP);
                this.disease |= other.disease; // Eating diseased pigeons leads to disease transmission
            }
        }
        if (this.hp <= 0 && other.hp / this.getPower () >= this.hp / other.getPower ())
        {
            // Gain stats from growing stronger
            other.power *= 1.2;
            other.maxSpeed *= 1.05;
            
            // Only desperate or almost dead pigeonsshould eat diseased pigeons
            if (other.isDesperate () || other.isDead () || !this.disease)
            {
                // Gain stats from eating pigeon
                other.full = 1.5;
                other.hp = Math.min (other.hp + other.maxHP / 2, other.maxHP);
                other.disease |= this.disease; // Eating diseased pigeons leads to disease transmission
            }
        }
    } // attack(Pigeon) method
    
    
    /**
     * This method causes the pigeon to move in a given way, limited by its speed.
     * Note that pigeons inside pigeonholes cannot move.
     * 
     * @param dx The given change in x-coordinate.
     * @param dy The given change in y-coordinate.
     */
    public void move (double dx, double dy)
    {
        if (home != null) // Currently has a home
        {
            return; // Don't move at all
        }
        
        // Check boundaries
        if (x + dx < 0)
        {
            dx = -x;
        }
        if (x + dx > 635)
        {
            dx = 635 - x;
        }
        if (y + dy < 0)
        {
            dy = -y;
        }
        if (y + dy > 635)
        {
            dy = 635 - y;
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
    
    
    /**
     * This method checks the pigeon's health.
     */
    public void checkHealth ()
    {
        // Continuous processes
        cooldown = Math.max (cooldown - 1, 0);
        age++;
        if (!disease) // Regenerates health if and only if no disease
        {
            hp = Math.min (hp + maxHP / 1200, maxHP);
        }
        if (disease && Math.random () < 1.0 / 2000) // One in 2000 chance of clearing disease
        {
            disease = false;
        }
        full -= 0.001; // Need to eat approximately one food every 1000 frames
        
        // Age processes
        if (age >= 9000) // Old pigeon
        {
            power = Math.max (0, power - 1.0 / 1800);
            maxHP = Math.max (0, maxHP - 1.0 / 450);
            hp = Math.min (hp, maxHP);
            maxSpeed = Math.max (maxSpeed - 0.0001, 0);
            disease = disease || (Math.random () < (age - 9000) / 450.0 * 0.0001); // Chance of catching disease
        }
    } // checkHealth() method
    
    
    /**
     * This method helps the pigeon whenever the pigeon eats a piece of food.
     * In particular, the pigeon restores half of its fullness and 20% of its max HP is regenerated.
     * 
     * @see full
     */
    public void eat ()
    {
        full += 0.5;
        hp = Math.min (maxHP, hp + maxHP / 5);
    }
    
    
    /**
     * This method makes the pigeon enter a given pigeonhole.
     * This method does nothing if the pigeon is already inside a pigeonhole.
     * 
     * @param hole The pigeonhole that the pigeon will enter.
     * @throw IllegalArgumentException If a pigeon of the same gender already occupies the pigeonhole.
     */
    public void enterHole (Pigeonhole hole)
    {
        if (home != null) // Already has a home
        {
            return;
        }
        
        // Enter the hole
        home = hole;
        hole.fill (this);
    } // enterHole(Pigeonhole) method
    
    
    /**
     * This method makes the pigeon exit its pigeonhole.
     * This method does nothing if the pigeon is currently homeless.
     * 
     * @throw IllegalArgumentException If, for some sketchy reason, the pigeon's home does not contain the pigeon.
     */
    public void exitHole ()
    {
        if (home != null)
        {
            // Make the pigeon homeless
            home.release (this);
            home = null;
        }
    } // exitHole() method
    
    
    /**
     * This method activates whenever the pigeon attempts to start breeding.
     * 
     * @throw IllegalArgumentException If the pigeon is not able to breed.
     */
    public void startBreeding ()
    {
        if (!canBreed ())
        {
            throw new IllegalArgumentException ("This pigeon is not able to breed.");
        }
        
        cooldown = 900; // Activate cooldown
    } // startBreeding() method
    
    
    /**
     * This method determines whether or not the pigeon feels safe, given the list of predators in the world.
     * 
     * @param predators The given list of predators.
     * @return <code>true</code> if and only if none of the predators makes the pigeon feel unsafe.
     * @see safety
     */
    public boolean isSafe (ArrayList<Predator> predators)
    {
        if (home != null)
        {
            return true; // Pigeon is safe inside pigeonhole
        }
        for (int i = 0; i < predators.size (); i++)
        {
            Predator p = predators.get (i);
            double dist = Math.sqrt ((x - p.getX ()) * (x - p.getX ()) + (y - p.getY ()) * (y - p.getY ()));
            
            if (dist <= safety) // Check for unsafe distance
            {
                return false;
            }
        }
        return true; // All the predators are a safe distance away
    } // isSafe(ArrayList<Predator>) method

    
    /**
     * This method determines whether or not a pigeon would feel safe at a given location.
     * 
     * @param predators The given list of predators in the world.
     * @param X The x-coordinate of the given location.
     * @param Y The y-coordinate of the given location.
     * @return <code>true</code> if and only if none of the predators makes the given location unsafe.
     * @see safety
     */
    public static boolean isSafe (ArrayList<Predator> predators, double X, double Y)
    {
        for (Predator p : predators)
        {
            double dist = Math.sqrt ((X - p.getX ()) * (X - p.getX ()) + (Y - p.getY ()) * (Y - p.getY ()));
            
            if (dist <= safety) // Check for unsafe distance
            {
                return false;
            }
        }
        return true; // All the predators are a safe distance away
    } // isSafe(ArrayList<Predator>,double,double) method
} // Pigeon class
