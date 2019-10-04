import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * A GUI that displays a graph that shows how stats change over time.
 *
 * @author Michael Li
 * @version January 20, 2019
 */
public class GraphGUI extends JFrame implements ActionListener
{
    /**
     * The window that will display the graph.
     */
    private static GraphGUI window;
    
    /**
     * The combo box that allows the user to choose which stat to display on the graph.
     */
    private static JComboBox displayCB;
    
    /**
     * The text field that allows the user to select a horizontal beginning point for the graph.
     */
    public static JTextField beginTF = new JTextField ("Auto", 5);
    
    /**
     * The text field that allows the user to select a horizontal zoom for the graph.
     */
    public static JTextField zoomTF = new JTextField ("Auto", 5);
    
    /**
     * The text area that prompts the user to choose which stat to display.
     * 
     * @see displayCB
     */
    public static JTextArea displayTA = new JTextArea ("Display stat: ");
    
    /**
     * The text area that warns the user that the graph features should not be adjusted when the simulation is running.
     */
    public static JTextArea warningTA;
    
    /**
     * The text area where all text output appears.
     */
    public static JTextArea output = new JTextArea ();
    
    /**
     * The text area that prompts the user to select the desired horizontal starting point.
     * 
     * @see beginTF
     */
    public static JTextArea beginLabel = new JTextArea ("Starting point (in frames): ");
    
    /**
     * The text area that prompts the user to select the desired horizontal zoom.
     * 
     * @see zoomTA
     */
    public static JTextArea zoomLabel = new JTextArea ("Zoom (in frames per pixel): ");
    
    /**
     * The list of population stats that should be displayed.
     */
    private static ArrayList<Double> populations = new ArrayList<> ();
    
    /**
     * The list of disease stats that should be displayed.
     */
    private static ArrayList<Double> diseases = new ArrayList<> ();
    
    /**
     * The list of old pigeon stats that should be displayed.
     */
    private static ArrayList<Double> olds = new ArrayList<> ();
    
    /**
     * The list of average speed stats that should be displayed.
     */
    private static ArrayList<Double> speeds = new ArrayList<> ();
    
    /**
     * The list of average max HP stats that should be displayed.
     */
    private static ArrayList<Double> maxHPs = new ArrayList<> ();
    
    /**
     * The list of average power stats that should be displayed.
     */
    private static ArrayList<Double> powers = new ArrayList<> ();
    
    /**
     * The list of average mutation level stats that should be displayed.
     */
    private static ArrayList<Double> muts = new ArrayList<> ();
    
    /**
     * The list of average fullness stats that should be displayed.
     */
    private static ArrayList<Double> fulls = new ArrayList<> ();

    
    /**
     * This constructor method creates the GUI that will display the graph.
     */
    public GraphGUI ()
    {
        // 1... Create/initialize components
        displayCB = new JComboBox ();
        displayCB.addItem ("Population");
        displayCB.addItem ("Amount of disease");
        displayCB.addItem ("Amount of old pigeons");
        displayCB.addItem ("Average speed");
        displayCB.addItem ("Average max HP");
        displayCB.addItem ("Average power");
        displayCB.addItem ("Average mutation level");
        displayCB.addItem ("Average fullness");
        displayCB.addActionListener (this);
        warningTA = new JTextArea ("It is recommended that you pause the simulation before adjusting the features above.");
        
        // Make sure that the user does not mess around with the program
        beginLabel.setEditable (false);
        zoomLabel.setEditable (false);
        output.setEditable (false);
        displayCB.setEditable (false);
        displayTA.setEditable (false);
        warningTA.setEditable (false);
        
        // 2... Create content panel, set layouts
        JPanel content = new JPanel (); // The main content panel
        content.setLayout (new BorderLayout ());
        JPanel north = new JPanel (); // The upper area
        north.setLayout (new BorderLayout ());
        JPanel input = new JPanel (); // The main input area
        input.setLayout (new FlowLayout ());
        JPanel zoomInput = new JPanel (); // The zoom input area
        zoomInput.setLayout (new FlowLayout ());
        DrawArea draw = new DrawArea (550, 550); // The drawing area

        // 3... Add the components to content panels
        input.add (displayTA);
        input.add (displayCB);
        north.add (input, "North");
        zoomInput.add (beginLabel);
        zoomInput.add (beginTF);
        zoomInput.add (zoomLabel);
        zoomInput.add (zoomTF);
        north.add (zoomInput, "Center");
        north.add (warningTA, "South");
        // Putting everything together
        content.add (north, "North"); // Input area
        content.add (output, "Center"); // Output area
        content.add (draw, "South"); // Drawing area

        // 4... Set this window's attributes
        setContentPane (content);
        pack ();
        setTitle ("Pigeonhole Graph");
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo (null); // Center window
        setVisible (true); // Make the window usable
        setResizable (false); // So that user can't mess around with window
    } // GraphGUI() constructor
    
    
    /**
     * This method makes the GUI output a given piece of text.
     * 
     * @param text The given text to be outputted.
     */
    public static void setOutput (String text)
    {
        output.setText (text);
    } // setOutput(String)
    
    
    /**
     * This method adds more stats to the lists of stats to be displayed and then displays the new stats.
     */
    public static void addStats ()
    {
        populations.add ((double) PigeonholeSimulation.colony.size ());
        try
        {
            diseases.add (PigeonholeSimulation.colony.getDisease ());
        }
        catch (ArithmeticException ex) // Colony is empty
        {
            diseases.add (null);
        }
        try
        {
            olds.add (PigeonholeSimulation.colony.getOld ());
        }
        catch (ArithmeticException ex) // Colony is empty
        {
            olds.add (null);
        }
        try
        {
            speeds.add (PigeonholeSimulation.colony.getAvgSpeed ());
        }
        catch (ArithmeticException ex) // Colony is empty
        {
            speeds.add (null);
        }
        try
        {
            powers.add (PigeonholeSimulation.colony.getAvgPower ());
        }
        catch (ArithmeticException ex) // Colony is empty
        {
            powers.add (null);
        }
        try
        {
            maxHPs.add (PigeonholeSimulation.colony.getAvgMaxHP ());
        }
        catch (ArithmeticException ex) // Colony is empty
        {
            maxHPs.add (null);
        }
        try
        {
            muts.add (PigeonholeSimulation.colony.getAvgMut ());
        }
        catch (ArithmeticException ex) // Colony is empty
        {
            muts.add (null);
        }
        try
        {
            fulls.add (PigeonholeSimulation.colony.getAvgFull ());
        }
        catch (ArithmeticException ex) // Colony is empty
        {
            fulls.add (null);
        }
        
        window.repaint (); // Redraw everything after stats are added
        window.pack (); // Resize window to make everything fit perfectly
    } // addStats() method
    
    
    /**
     * This method redraws the graph whenever the user interacts with the GUI.
     * 
     * @param e Something random that you can ignore.
     */
    public void actionPerformed (ActionEvent e)
    {
        window.repaint (); // Redraw everything after changes are made
        window.pack (); // Resize window to make everything fit perfectly
    } // actionPerformed(ActionEvent) method
    
    
    /**
     * This method initializes the graph window and makes it usable.
     * 
     * @param args Something random that you can ignore.
     */
    public static void main (String[] args)
    {
        window = new GraphGUI (); // Initialize the graph window
    } // main(String[]) method
    
    
    /**
     * A special type of panel that will draw the graph in the GUI.
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
            // Display the required stat as text
            if (displayCB.getSelectedItem ().equals ("Average speed"))
            {
                try
                {
                    setOutput ("Average speed: " + speeds.get (speeds.size () - 1) + " pixels per frame");
                }
                catch (NullPointerException ex) // Colony is empty
                {
                    setOutput ("Average speed: Undefined");
                }
                drawGraph (g, speeds);
            }
            else if (displayCB.getSelectedItem ().equals ("Average power"))
            {
                try
                {
                    setOutput ("Average power: " + powers.get (powers.size () - 1));
                }
                catch (NullPointerException ex) // Colony is empty
                {
                    setOutput ("Average power: Undefined");
                }
                drawGraph (g, powers);
            }
            else if (displayCB.getSelectedItem ().equals ("Average max HP"))
            {
                try
                {
                    setOutput ("Average max HP: " + maxHPs.get (maxHPs.size () - 1));
                }
                catch (NullPointerException ex) // Colony is empty
                {
                    setOutput ("Average max HP: Undefined");
                }
                drawGraph (g, maxHPs);
            }
            else if (displayCB.getSelectedItem ().equals ("Average mutation level"))
            {
                try
                {
                    setOutput ("Average mutation level: " + muts.get (muts.size () - 1));
                }
                catch (NullPointerException ex) // Colony is empty
                {
                    setOutput ("Average mutation level: Undefined");
                }
                drawGraph (g, muts);
            }
            else if (displayCB.getSelectedItem ().equals ("Average fullness"))
            {
                try
                {
                    setOutput ("Average fullness: " + fulls.get (fulls.size () - 1));
                }
                catch (NullPointerException ex) // Colony is empty
                {
                    setOutput ("Average fullness: Undefined");
                }
                drawGraph (g, fulls);
            }
            else if (displayCB.getSelectedItem ().equals ("Population"))
            {
                setOutput ("Population: " + populations.get (populations.size () - 1) + " pigeons");
                drawGraph (g, populations);
            }
            else if (displayCB.getSelectedItem ().equals ("Amount of disease"))
            {
                try
                {
                    setOutput ("Amount of disease: " + diseases.get (diseases.size () - 1) + "% of pigeons");
                }
                catch (NullPointerException ex) // Colony is empty
                {
                    setOutput ("Amount of disease: Undefined");
                }
                drawGraph (g, diseases);
            }
            else if (displayCB.getSelectedItem ().equals ("Amount of old pigeons"))
            {
                try
                {
                    setOutput ("Amount of old pigeons: " + olds.get (olds.size () - 1) + "% of pigeons");
                }
                catch (NullPointerException ex) // Colony is empty
                {
                    setOutput ("Amount of old pigeons: Undefined");
                }
                drawGraph (g, olds);
            }
            
            window.pack (); // To make everything fit perfectly when displaying
        } // paintComponent(Graphics) method
        
        
        /**
         * This method displays a graph using a given Graphics and a given list of stats.
         */
        public void drawGraph (Graphics g, ArrayList<Double> stats)
        {
            // Declare variables
            int begin = 0;
            double max = -1e99;
            double min = 1e99;
            int zoom = 0;
            
            try
            {
                zoom = Integer.parseInt (zoomTF.getText ());
            }
            catch (NumberFormatException ex) // Not an integer
            {
                zoomTF.setText ("Auto");
            }
            try
            {
                begin = Integer.parseInt (beginTF.getText ());
            }
            catch (NumberFormatException ex) // Not an integer
            {
                beginTF.setText ("Auto");
            }
            if (zoomTF.getText ().equals ("Auto") && beginTF.getText ().equals ("Auto")) // Program should decide both variables
            {
                begin = 0; // Start from beginning
                zoom = (stats.size () - 1) / 500 + 1; // Fit the entire graph
            }
            else if (zoomTF.getText ().equals ("Auto")) // Program should decide zoom
            {
                zoom = (stats.size () - 1) / 500 + 1; // Fit the entire graph
            }
            else if (beginTF.getText ().equals ("Auto")) // Program should decide beginning point
            {
                begin = Math.max (0, stats.size () - 500 * zoom - 1); // Display as much as possible while displaying ending
            }
            
            // Loop to determine minimum and maximum values
            for (int i = begin; i < stats.size () && i <= begin + 500 * zoom; i++)
            {
                try
                {
                    max = Math.max (max, stats.get (i));
                    min = Math.min (min, stats.get (i));
                }
                catch (NullPointerException ex) // The stat doesn't exist
                {
                }
            }
            if (max == -1e99 && min == 1e99) // None of the stats exist
            {
                setOutput (output.getText () + "\nThere are no stats to be displayed."); // Display error message
                return; // No graph to be displayed
            }
            
            // Display axes and their labels
            g.drawLine (25, 25, 25, 526);
            g.drawLine (25, 526, 525, 526);
            g.drawString ("Number of frames", 230, 540);
            
            // Display ranges as text
            setOutput (output.getText () + "\nThe y-values on this graph range from " + min + " to " + max + ".");
            setOutput (output.getText () + "\nThis graph ranges from " + begin + " frames to "
                        + (begin + 500 * zoom) + " frames.");
                        
            // Loop to display actual data
            for (int i = begin + zoom; i < stats.size (); i += zoom)
            {
                try
                {
                    int y1, y2; // The y-coordinates where the data should be displayed
                    if (min == max) // y-values have no range
                    {
                        y1 = y2 = 275; // Draw a flat line at the middle
                    }
                    else // Regular graph
                    {
                        y1 = (int) (525 - 500 * (stats.get (i - zoom) - min) / (max - min));
                        y2 = (int) (525 - 500 * (stats.get (i) - min) / (max - min));
                    }
                    
                    g.drawLine ((i - begin) / zoom + 25, y1, (i - begin) / zoom + 26, y2);
                }
                catch (NullPointerException ex) // The stat doesn't exist and cannot be displayed
                {
                }
            }
        } // drawGraph(Graphics,ArrayList<Double>) method
    } // DrawArea class
} // GraphGUI class
