/* _im43 (GoldGrab app intermediate 43)

-- Now calling sound process method of new class, Divvier_of_5_GG,
if bar number is 5




ToDo:

(-- Might replace wArr with an AtrayList to avoid need for this in the future,
though that would need further code tweaks)


*/

// basic drag code adapted from http://stackoverflow.com/questions/874360/swing-creating-a-draggable-component/874424#874424

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.ArrayList;
import java.awt.event.*; // try to replace with specific imports but...
import javax.swing.*; // ...does not seem straightforward?.................


public class GoldGrab extends JComponent 
{ 
    // co-ordinates of mouse press on component relative to device screen
    private volatile int screenX; 
    private volatile int screenY; 
    
    // co-ordinates of upper left corner of component relative to JFrame
    private volatile int cX; 
    private volatile int cY;
    
    // (...'volatile' modifier is not essential - not sure how much difference it makes and in what circumstances)
    
    
    private int w = 0; // width of component and drawn rectangle
    private static int h = 20; // component height (invariant; could also be declared 'final')
    private int iD; // identification number assigned to the component (1 to number of component objects)
    public static int count = 0; // number of objects instantiated
    
    // static variables to keep track of dragged objects inside constructor drag code...
    static int[] wArr = new int[Launch.howMany]; // widths of components 
    static int[] cXarr = new int[Launch.howMany]; // current x positions of components
    static int[] cYarr = new int[Launch.howMany]; // current y positios of components 
    {
        for(int i = 0; i < Launch.howMany; i++)
            cYarr[i] = 40 + 20 * i;  
    }
    // do not remove these initialisations - otherwise system thinks all components at y=0 until dragged (see _im11)
    //     (might move from this initializer block to the constructor)
               
    boolean bar; // whether component is a (movable) gold bar
        
    static JTextPane reportFinal; // for some reason if this is instead a JTextField...
    static JTextField report; // ...and this JTextField is initially set invisible, it cannot be set visible later?!
    static JButton endJButton; // button for user to end game and see outcome  
    
    static int sum; // sum of of widths of objects (gold bars) 
    static int tSum; // sum of of widths of objects (gold bars) at top frame
    static int bSum; // sum of of widths of objects (gold bars) at bottom frame
    
    JTextField fateField;
    int timerCount;
    Timer t1;
    
    
    private static JTextArea fateArea;

    public static JTextArea getFateArea()
    {
        return fateArea;   
    }
        

    // constructor for single component at bottom frame to hold 'report' text area
    public GoldGrab() 
    {   
        setBounds(0, 80 + 20 * Launch.howMany, Launch.playWidth, 80); // (2nd arg same as Launch.playHeight - 80)
        setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));  
    
        setLayout(new BorderLayout(3, 3));
                                
        reportFinal = new JTextPane();
        reportFinal.setFont(new Font("SansSerif", Font.BOLD, 12));
        reportFinal.setEditable(false);
        add(reportFinal, BorderLayout.NORTH);           
        
        // calculate the sum of gold bars (wArr element values)
        for(int i = 0; i < Launch.howMany; i++)
            sum += wArr[i];
                
        report = new JTextField("");         
        // and this will not be visible until game ended (as set invisible next)                
        report.setVisible(false); // will be set visible when uses clicks endJButton
        // (...though could delete this if choose to have enabled for user during game)
        report.setEditable(false);
        add(report, BorderLayout.CENTER);    
        
        
        endJButton = new JButton("OK, I'm done");
        add(endJButton, BorderLayout.SOUTH);
        endJButton.addActionListener(
            new ActionListener() 
            { 
                public void actionPerformed(ActionEvent e) 
                {                    
                    endJButton.setEnabled(false); // score is final, so button should be inactivated                                    

                    reportFinal.setText("Your gold (top): " + tSum  + "   Mafia gold: " + (sum - tSum) +
                            "            "
                            + "(You can continue to move the bars and see the results that might "
                            + "have been below, but that won't change your score)");                     

                    if(tSum > sum - tSum) // (same thing)
                    {                                         
                        fateField = new JTextField("Greedy pig - you're dead");
                        fateField.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
                        fateField.setEditable(false);
                        fateField.setForeground(Color.red);    
                        fateField.setHorizontalAlignment(JTextField.CENTER);
                        Launch.fatePanel.add(fateField);
                        
                        // At half-second (500ms) intervals, add the 3 dots of an ellipsis to the message
                        //    one-by-one, then play gun shot sound after anoher half second delay 
                        t1 = new Timer
                        (
                            500, new ActionListener() 
                            {
                                public void actionPerformed(ActionEvent e) 
                                {
                                    if(timerCount < 3)
                                        fateField.setText(fateField.getText() + ".");   
                                    if(timerCount == 3)                                        
                                        new Sound().playIt("Gun_Shot-Marvin-1140816320.wav");
                                    
                                    timerCount++;

                                    if(timerCount < 4)
                                        startAgain();                                  
                                }
                            }
                        );
                        t1.setRepeats(false); //otherwise it repeats indefinitely!
                        t1.start();     
                    }
                    else
                    {
                        // make an ArrAylist<Double> version of wArr for passing to divviers
                        ArrayList<Integer> wArrL = new ArrayList<Integer>();
                        for(int i = 0; i < wArr.length; i++)
                            wArrL.add(wArr[i]);                        
                        
                        // textarea for showing outcome (comparison of results with
                        //     optimal split of gold bars as calculated by divvier etc)
                        fateArea = new JTextArea();
                        fateArea.setEditable(false);
                        fateArea.setMargin(new Insets(20, 100, 20, 100));
                        fateArea.setLineWrap(true);
                        fateArea.setWrapStyleWord(true);                    
                        Launch.fatePanel.add(fateArea);
                        
                        
                        
                        
                        
                        
                        if(wArr.length == 4)
                            Divvier_of_4_GG.process(wArrL);
                        
                        
                        else if(wArr.length == 5)
                            Divvier_of_5_GG.process(wArrL);                        
                        
                        
                        else
                            Divvier_to_11_GG.process(wArrL);
                        
                        
                        
                        
                        
                        
                        
                    }    
                                            
                    report.setVisible(true);

                } // end method actionPerformed for endJButton
                
                
                public void startAgain()
                {
                    t1.restart();
                }                 
                
            } // end ActionListener for endJButton
        ); // closing parenthesis for argument of method addActionListener for endJButton  
        
    }
    
    
    // constructor for gold bar components
    public GoldGrab(int idCPt, int wCpt) 
    {    
        bar = true;
        
        iD = idCPt;
        w = wCpt; // note: cannot use 'w' as parameter with this.w if want to access w in inner class below as does not recognise this!! 
        
        setBounds(0, 40 + count * 20, w, h); // component (rectangle) position (x,y), dimensions (w,h) in wich ingot is drawn 
        
        addMouseListener(
            new MouseListener() 
            {
                public void mouseClicked(MouseEvent e) { } 

                public void mousePressed(MouseEvent e) {     
                    screenX = e.getXOnScreen(); 
                    screenY = e.getYOnScreen(); 
//                    System.out.println("screenX in mousePressed: " + screenX); // temporary
                    
                    cX = getX(); // x co-ord of upper left of component (in JFrame)
                    cY = getY(); // x co-ord of upper left of component (in JFrame)
                } // end method mousePressed

                public void mouseReleased(MouseEvent e) { } 
                public void mouseEntered(MouseEvent e) { }
                public void mouseExited(MouseEvent e) { } 
            } // end MouseListener
        ); // end closing parenthesis for argument of first call to method addMouseListener
        

        addMouseMotionListener(
            new MouseMotionListener() 
            { 
                public void mouseDragged(MouseEvent e) 
                { 
                    // difference between current position and position when mouse was pressed before drag
                    int deltaX = e.getXOnScreen() - screenX; 
                    int deltaY = e.getYOnScreen() - screenY;
//                    System.out.println("e.getXOnScreen() in md: " + e.getXOnScreen()); // temporary
                    
                    
                    // conditions to constrain horozontal movement of objects...            

                    int moveX = 0; // distance to move horozontally on drag

                    // top and bottom 'blocking statements': if specified object (1/2/3...) is already 
                    //    at top/bottom frame (cY1/cY2/cY3... == 0 or Launch.playHeight - 52)
                    //    prevent left edge of any currently-dragged object also at top/bottom (cY == 0 or Launch.playHeight - 52)
                    //    from moving past right edge of blocking object - i.e. stop flush (precice apposition)
                    //    (remember that you have to drop and re-drag at top to activate 'blocking sensitivity' (Note2 below)
                    //    See also Note3 and Note4 for possible tweaks to coding and function

                    int[] block = new int[Launch.howMany];

                    // variables to specify 'blocking' position
                    for(int i = 0; i < Launch.howMany; i++)
                        block[i] = ((cYarr[i] == 0 || cYarr[i] == Launch.playHeight - 100) ? cXarr[i] + wArr[i] : 0);
                    // (the 100 is for the bottom textfield- and button-bearing component (80) + bar height (20)
                    
                    for(int i = 0; i < Launch.howMany; i++)
                        if(iD != i + 1 && cY == cYarr[i] && cX > block[i] && - deltaX > cX - block[i]) 
                            moveX = block[i] - cX; 

                    if(moveX == 0)
                    {
                    // conditions to prevent objects moving over vertical frame borders...

                        if(-deltaX > cX) 
                            moveX = - cX;  // do not move over left frame border (stop flush with)
                        
                        else if(deltaX > Launch.playWidth - (cX + w))
                            moveX = Launch.playWidth - (cX + w);  // do not move over right frame border                                        
                        
                        // if no constraints, just make the same horozontal move as the mouse
                        else                                
                            moveX = deltaX;               
                    } 

                    // conditions to constrain vertical movement of objects...

                    int moveY; // distance to move vertically on drag

                    // conditions to prevent objects moving over horozontal frame borders
                    if(-deltaY > cY)
                        moveY = - cY; // do not move over top frame border 
                    else if(deltaY > Launch.playHeight - (cY + h + 80))
                        moveY = Launch.playHeight - (cY + h + 80); // do not move over bottom frame border
          
                    // if no constraints, just make the same vertical move as the mouse
                    else
                        moveY = deltaY;                

                    setLocation(cX + moveX, cY + moveY); // move the object based on cursor movement


                    // update/track upper left x coord of components (  getX() same as   cX + moveX   )
                    for(int i = 0; i < Launch.howMany; i++)
                        if(iD == i + 1)
                            cXarr[i] = getX();                

                    // update/track upper left y coord of components (  getY() same as   cY + moveY   )
                    for(int i = 0; i < Launch.howMany; i++)
                        if(iD == i + 1)
                            cYarr[i] = getY();                  


                    // console display of w,x,y values for each object (id 1-11 from left) 
                    //    ...just eeping in case it might prove temporarily useful in future
//                    for(int w : wArr)
//                        System.out.printf("%6d", w);
//                    System.out.println();                
//                    for(int x : cXarr)
//                        System.out.printf("%6d", x); 
//                    System.out.println();
//                    for(int y : cYarr)
//                        System.out.printf("%6d", y); 
//                    System.out.println("\n");

                    
                    // calculate sum of of widths of objects at top frame
                    tSum = 0;
                    for(int i = 0; i < Launch.howMany; i++)
                        tSum += (cYarr[i] == 0 ? wArr[i] : 0);

                    // calculate sum of of widths of objects at bottom frame            
                    bSum = 0;
                    for(int i = 0; i < Launch.howMany; i++)
                        bSum += (cYarr[i] == Launch.playHeight - 100 ? wArr[i] : 0);
                    // (the 100 is for the bottom textfield- and button-bearing component (80) + bar height (20)
                  
                          
                    report.setText("Gold currently at top: " + tSum  + "   middle: " + (sum - tSum - bSum)
                        + "   bottom: " + bSum);                       
       
                } // end method mouseDragged 

                public void mouseMoved(MouseEvent e) { }

            } // end MouseMotionListener
        ); // end closing parenthesis for argument of first call to method addMouseListener
        
        // (...Note: think could implement MouseInputListener in place of seperate MouseListener,MouseMotionListener
        //    or extend MouseAdapter + MouseMotionAdapter adapter classes)
        	

        // polulate wArr and calculate sum of widths
        for(int i = 0; i < Launch.howMany; i++)
            if(count == i)
            {    
                wArr[i] = w;   
                sum += w;
            } 
			
        ++count;
        
    } // end constructor
    
   
    // draw 'gold bars' on components, except for the bottom component that holds the end button and reports
    public void paintComponent( Graphics g )
    { 
        if (bar == true)
        {    
            super.paintComponent( g );    

            g.setColor( new Color(180, 180, 0) ); // a dark yellow fill for slanted sides gold ingots
            g.fillRoundRect(0, 0, w - 1, 19, 5, 5); // (height does not vary (20 chosen))  

            g.setColor( new Color(255, 220, 40) ); // a yellow fill for top gold ingots
            g.fillRoundRect(0, 0, w - 5, 15, 5, 5); // (height does not vary)          

            g.setColor( Color.DARK_GRAY ); // colour for border around rectangles (ingots; also number string, below)
            g.drawRoundRect( 0, 0, w - 1, 19, 5, 5 ); // (height does not vary (20 chosen))   

            g.setFont(new Font("SANS_SERIF", Font.BOLD, 10));
            String numStr = String.format("%d", w); // string representation of width 
            g.drawString(numStr, 0 + ( w - g.getFontMetrics().stringWidth(numStr) ) / 2, 0 + 13); // (number string at centre ingot)                
        }
    } // end methocd paintComponent
    
  
} // end class GoldGrab

/* Notes and further work:

(Note1 gone)

-- (Note2: Possible to get rid of need to 'drop-and-redrag' at top to initiate 'blocking sensitivity'
    and similar need to drop-and-redrag to release the block on moving off top?)


-- Note3: Removing the   (cY1 == 0 || cY1 == 200)  condition would allow blocking anywhere in the dept of the freme, 
   e.g. object1 as blocker:
        if(iD != 1 && (cY1 == 0 || cY1 == 200) && cY == cY1 && cX > (cX1 + w1) && -deltaX > cX - (cX1 + w1)) 
            moveX = (cX1 + w1) - cX; 
   if blocker and blockee are precicely vertically aligned
   Or could have 'more natural' blocking along full object heights, i.e. e 2x20px windows, e.g.
        if(iD != 1 && (cY > cY1 - 20 || cY < cY1 + 20) && cX > (cX1 + w1) && -deltaX > cX - (cX1 + w1)) 
            moveX = (cX1 + w1) - cX; 


-- Note4: In _im8 I replaced single 'blocking' statements with 2-part coding (use of block1/2/3... variables)
   for hopefully increased readability. Can reverse if there turns out disadvantageous.


TODO: 
-- When game ended, display an evaluation that includes
   the results of divvier evaluation of the optimal split and visual/sound messages, 
-- Maybe limit on time for player to manipluate objects etc.


-- TODO: Good practise and tidying up: inc.:
---- Consider encapsulating any instance variables I have made public, if any? 
     (Would add extra code, so not a priority until everything is working)
---- Could put back some of comments I have removed for expediency; look through all intermediates,
     as some have been removed or changed along the way - want most informative updated versions each

*/