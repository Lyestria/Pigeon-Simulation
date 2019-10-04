import java.awt.*;
import java.awt.event.*;  // Needed for ActionListener
import java.util.*;
import javax.swing.*;

/**
 * This program simulates a world where pigeons live in finitely many pigeonholes.
 * 
 * @author Michael Li
 * @author Kevin Wan
 * @author George Chen
 * @version January 9, 2019
 */
public class PigeonholeSimulation extends JFrame implements ActionListener
{
    /**
     * Whether or not the program is currently being debugged.
     */
    public static final boolean DEBUG = true;
    
    /**
     * The window that will be used to display the world.
     */
    public static PigeonholeSimulation window;
    
    /**
     * The button that allows the user to start or stop the simulation.
     */
    public static JButton simulateBtn;
    
    /**
     * The combo box that allows the user to choose which individual stat the pigeons should display.
     */
    public static JComboBox displayCB;
    
    /**
     * The slider that allows the user to select a speed for the simulation.
     */
    public static JSlider speedSlider = new JSlider (1, 100);
    
    /**
     * The text area that prompts the user to select the desired speed.
     * 
     * @see speedSlider
     */
    public static JTextArea sliderLabel = new JTextArea ("Control speed (up to 100 fps): ");
    
    /**
     * The text area that prompts the user to choose which stats to display.
     * 
     * @see displayCB
     */
    public static JTextArea displayTA = new JTextArea ("Display stat: ");
    
    /**
     * The list of all pigeonholes in the world.
     */
    public static ArrayList<Pigeonhole> holes = new ArrayList<> ();
    
    /**
     * The colony containing all pigeons in the world.
     */
    public static Colony colony = new Colony ();
    
    /**
     * Helps to keep track of time to simulate at the required speed.
     */
    public static long time;
    
    /**
     * Whether or not the simulation is currently happening.
     */
    public static boolean simulating;

    /**
     * Where the minimum slider is located to indicate the minimum food generation speed.
     */
    public static int minSlideLoc = 520;

    /**
     * Where the maximum slider is located to indicate the maximum food generation speed.
     */
    public static int maxSlideLoc = 120;

    /**
     * Display the current framerate
     */
    public static JLabel frameRate = new JLabel("FPS:    ");

    /**
     * Display the current food generation statistics
     */
    public static JLabel foodRateLabel = new JLabel("<html>    Low Season: 0.50<br>    High Season: 2.50<br>    Current Rate: 1.50</html>");
    
    
    /**
     * This Constructor method puts stuff on the GUI.
     */
    public PigeonholeSimulation ()
    {
        // 1... Create/initialize components
        simulateBtn = new JButton ("Start");
        simulateBtn.addActionListener (this);
        displayCB = new JComboBox ();
        displayCB.addItem ("Individual fullness");
        displayCB.addItem ("Individual HP");
        displayCB.addItem ("Individual disease");
        displayCB.addActionListener (this);
        
        sliderLabel.setEditable (false);
        displayCB.setEditable (false);
        displayTA.setEditable (false);
        frameRate.setPreferredSize(new Dimension(50,10));
        // 2... Create content panel, set layouts
        JPanel content = new JPanel (); // The main content panel
        content.setLayout (new BorderLayout ());
        JPanel north = new JPanel (); // The upper area
        JPanel control=new JPanel();//controllables
        control.setLayout (new BorderLayout ());
        JPanel input = new JPanel (); // The main input area
        input.setLayout (new FlowLayout ());
        JPanel speedInput = new JPanel (); // The speed input area
        speedInput.setLayout (new FlowLayout ());
        DrawArea draw = new DrawArea (640, 640); // The drawing area
        
        // Create panels for the food generation speed slider
        DrawArea2 slider = new DrawArea2 ();
        JPanel combine = new JPanel ();
        combine.add (draw);
        combine.add (slider);
        Move m = new Move ();
        slider.addMouseListener (m);
        slider.addMouseMotionListener (m);

        // 3... Add the components to content panels
        input.add (simulateBtn);
        input.add (displayTA);
        input.add (displayCB);
        control.add (input, "North");
        speedInput.add (sliderLabel);
        speedInput.add (speedSlider);
        speedInput.add(frameRate);
        control.add (speedInput, "Center");
        north.add(control);
        north.add(foodRateLabel);
        // Putting everything together
        content.add (north, "North"); // Input area
        content.add (combine, "South"); // Drawing area

        // 4... Set this window's attributes
        simulating = false; // Should not be simulating when initialized
        setContentPane (content);
        pack ();
        setTitle ("Pigeonhole Simulation");
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo (null); // Center window
        setVisible (true); // Make the window usable
        setResizable (false); // So that user can't mess around with window
    } // PigeonholeSimulation() constructor
    

    /**
     * This method initializes the GUI and makes it usable, and then simulates the world.
     * 
     * @param args Something random that you can ignore.
     */
    public static void main (String[] args)
    {
        window = new PigeonholeSimulation (); // Initialize the window
        GraphGUI.main (new String[0]); // Initialize the graph GUI as well
        GraphGUI.addStats (); // Add initial stats that can be displayed
        
        // Loop to simulate world indefinitely
        long lastCheck=System.nanoTime();
        while (true) {
            time = System.nanoTime();
            if (simulating) {//while (!simulating) {} // Wait until the user wants to simulate
                colony.move(); // Simulate one frame of the world
                //get numbers
                double lowSeason = (620.0 - PigeonholeSimulation.minSlideLoc) / 200;
                double highSeason = (620.0 - PigeonholeSimulation.maxSlideLoc) / 200;
                // Model seasons with a sinusoidal curve
                double numFood = (lowSeason + highSeason) / 2
                        + (highSeason - lowSeason) / 2 * Math.sin (Math.PI * colony.getAge () / 3600);

                window.repaint(); // Redraw entire world after modifying it
                foodRateLabel.setText(String.format("<html>    Low Season: %.2f<br>    High Season: %.2f<br>    Current Rate: %.2f</html>",lowSeason,highSeason,numFood));
            }
            //Busy waiting for accuracy (using nanoTime)
            long frameTime=1000000000L/speedSlider.getValue();
            if (DEBUG) {
                if (System.nanoTime()>time+frameTime) {
                    System.out.println("LAG!"); // For detecting lag
                }
            }
            while(System.nanoTime()<time+frameTime);// Enforce desired speed
            //take samples to avoid seizures
            if(colony.getAge()%10==9){
                int fps=(int)Math.round(1000000000.0*10/(System.nanoTime()-lastCheck));
                frameRate.setText("FPS: "+fps);//display the frame rate
                lastCheck=System.nanoTime();
            }
        }
    } // main(String[]) method
    
    
    /**
     * This method activates whenever the user interacts with the program.
     * 
     * @param e The event that activated this method.
     */
    public void actionPerformed (ActionEvent e)
    {
        if (e.getActionCommand ().equals ("Start"))
        {
            simulating = true; // Start simulating
            simulateBtn.setText ("Stop"); // The button changes its purpose
        }
        if (e.getActionCommand ().equals ("Stop"))
        {
            simulating = false; // Stop simulating
            simulateBtn.setText ("Start"); // The button changes its purpose
        }
        
        repaint (); // Redraw everything after event
        pack (); // Resize window to make everything fit perfectly
    } // actionPerformed(ActionEvent) method
    
    
    /**
     * An object that helps to move the slider when the user interacts with it with the mouse.
     */
    public class Move implements MouseListener, MouseMotionListener
    {
        /**
         * An integer representing the slider that is being selected by the mouse.
         * 0 - Neither slider
         * 1 - Minimum slider
         * 2 - Maximum slider
         */
        private int select = 0;
        
        
        /**
         * Some random method that you can ignore.
         * 
         * @param e Something random that you can ignore.
         */
        public void mouseClicked (MouseEvent e)
        {
        } // mouseCiicked(MouseEvent) method

        
        /**
         * This method moves the slider whenever the mouse is dragged.
         * 
         * @param e The event that activated this method.
         */
        public void mouseDragged (MouseEvent e)
        {
            if (select == 1)
            {
                minSlideLoc = Math.min (620, Math.max (e.getY (), maxSlideLoc + 5)); // Move the minimum slider
            }
            else if (select == 2)
            {
                maxSlideLoc = Math.max (20, Math.min (e.getY (), minSlideLoc - 5)); // Move the maximum slider
            }
            
            repaint (); // Redraw everything to show new location of slider
        } // mouseDragged(MouseEvent) method

        
        /**
         * Some random method that you can ignore.
         * 
         * @param e Something random that you can ignore.
         */
        public void mouseEntered (MouseEvent e)
        {
        } // mouseEntered(MouseEvent) method

        
        /**
         * Some random method that you can ignore.
         * 
         * @param e Something random that you can ignore.
         */
        public void mouseExited (MouseEvent e)
        {
        } // mouseExited(MouseEvent) method

        
        /**
         * Some random method that you can ignore.
         * 
         * @param e Something random that you can ignore.
         */
        public void mouseMoved (MouseEvent e)
        {
        } // mouseMoved(MouseEvent) method

        
        /**
         * This method determines which slider is being moved when the mouse is pressed.
         * 
         * @param e The event that activated this method.
         */
        public void mousePressed (MouseEvent e)
        {
            if (10 <= e.getX () && e.getX () <= 30 && minSlideLoc - 5 <= e.getY () && e.getY () <= minSlideLoc + 5)
            {
                select = 1; // Minimum slider is being moved
            }
            else if (10 <= e.getX () && e.getX () <= 30 && maxSlideLoc - 5 <= e.getY () && e.getY () <= maxSlideLoc + 5)
            {
                select = 2; // Maximum slider is being moved
            }
        } // mousePressed(MouseEvent) method

        
        /**
         * This method tells the program that neither slider is being moved when the mouse is released.
         * 
         * @param e Something random that you can ignore.
         */
        public void mouseReleased (MouseEvent e)
        {
            select = 0;
        } // mouseReleased(MouseEvent) method
    } // Move class
    
    
    /**
     * A special type of panel that will draw the world that is being simulated.
     */
    class DrawArea extends JPanel
    {
        /**
         * This Constructor method creates a new drawing area with a given size.
         * 
         * @param width The given width of the drawing area.
         * @param height The given height of the drawing area.
         */
        public DrawArea (int width, int height)
        {
            this.setPreferredSize (new Dimension (width, height)); // size
        } // DrawArea(int,int) constructor

        
        /**
         * This method redraws everything in the world.
         * 
         * @param g The Graphics that will draw stuff.
         */
        public void paintComponent (Graphics g)
        {
            // Green background
            g.setColor (Color.green);
            g.fillRect (0, 0, 640, 640);
            
            colony.display (g); // Display the objects in the world
            window.pack (); // To make everything fit perfectly when displaying
        } // paintComponent(Graphics) method
    } // DrawArea class
    

    /**
     * A special panel that will draw the food generation speed sliders.
     * Please note that the maximum slider location is above the minimum slider location,
     * so the value of <code>maxSlideLoc</code> is <b>smaller</b>.
     * 
     * @see maxSlideLoc
     * @see minSlideLoc
     */
    class DrawArea2 extends JPanel
    {
        /**
         * This constructor method creates a new slider drawing area with a fixed size of 50 pixels by 640 pixels.
         */
        public DrawArea2 ()
        {
            this.setPreferredSize (new Dimension (50, 640));
        } // DrawArea2() constructor
        
        
        /**
         * This method draws the food generation speed slider using a given Graphics.
         * 
         * @param g The Graphics that will draw stuff.
         */
        public void paintComponent (Graphics g)
        {
            g.setColor (Color.black); // Most of the slider is black
            g.fillRect (19, 20, 2, 600); // The main part of the slider
            g.fillRect (10, minSlideLoc - 5, 20, 10); // The minimum sliding part
            g.fillRect (10, maxSlideLoc - 5, 20, 10); // The maximum sliding part
            double lowSeason = (620.0 - PigeonholeSimulation.minSlideLoc) / 200;
            double highSeason = (620.0 - PigeonholeSimulation.maxSlideLoc) / 200;
            
            // Model seasons with a sinusoidal curve
            double numFood = (lowSeason + highSeason) / 2
                            + (highSeason - lowSeason) / 2 * Math.sin (Math.PI * colony.getAge () / 3600);
                            
            // Draw location of current food generation speed
            int pos = (int) (620 - numFood * 200);
            g.setColor (Color.green); // Green colour
            g.fillRect (10, pos - 2, 20, 4);
        } // paintComponent(Graphics) method
    } // DrawArea2 class
} // PigeonholeSimulation class
