/* _im36 (GoldGrab app intermediate 36)

(No changeds to this class since _im31)

 
*/

import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.util.Random;
import javax.swing.JPanel;


public class Launch extends JFrame
{    
    public static int playWidth = 1000; // width of playPanel (and gameFrame) 
    public static int playHeight; // height of playPanel 
    
    public static int howMany; // nunber of GoldGrab objects to be created 
    // this is given a user-input value in the Input class 
    
    static JPanel fatePanel;
        
    public Launch()
    {
        super("GoldGrab"); // set frame title
       
        setLayout(null);
        
        /* gold bars will be stacked at the left border initially, and want empty space of two bar heights 
           above and below the stack in the display area (2x 2x 20 = 80), 
           plus want extra 80px below for  the bottom componenst with the textfields and button */
        playHeight = 20 * howMany + 160;         
        
        JPanel playPanel= new JPanel(); // panel to hold gold bars and report area
        playPanel.setBounds(0, 0, playWidth, playHeight);   
        playPanel.setLayout(null);
        add(playPanel); // add to JFrame

        // call GoldGrab no-arg constructor to make repore area component at bottom, add it to playPanel
        playPanel.add(new GoldGrab()); 
    
        // generate set of 'howMany' random integers between 30 and 300 to represent the GoldGrab object (gold bar) widths
        int[] widths = new int[howMany];
        Random rnd = new Random();
        for(int i = 0; i < howMany; i++)
            widths[i] = 30 + rnd.nextInt(271);

        for(int i = 0; i < howMany; i++)
        {    
            // make GoldGrab objects, passing identity number and width to constructor
            GoldGrab mc = new GoldGrab(i + 1, widths[i]); 
            playPanel.add(mc); // then add to playPanel       
        }
        
        
        // panel to show outcome (comparison of results with
        //     optimal split of gold bars as calculated by divvier etc)
        fatePanel = new JPanel(new BorderLayout());
        fatePanel.setBounds(0, playHeight, playWidth, 200);      
        add(fatePanel);  // add to JFrame

    }
  
    
    public static void setup()
    {
        Launch gameFrame = new Launch();

        gameFrame.setSize(playWidth + 6, playHeight + 200 + 32); // 6a nd 32 px extra for frame borders
        gameFrame.setResizable(false); // prevent JFrame from being resized, at least for now (see previous notes)
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null); // put frame in middle of screen (optional)
        gameFrame.setVisible(true);
        
        
        // FLOTSAM - might have some useful code 'phrases' - discard later
//        gameFrame.getContentPane().setBackground(Color.WHITE); 
//        gameFrame.add(fatePanel, BorderLayout.SOUTH); 
        
    }

}

