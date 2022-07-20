
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;

/* _im42 (GoldGrab app intermediate 42)

-- Added this new class, Divvier_of_4_GG, modified from Divvier_in_GUI class Divvier_of_4_IG _im27
in a similar way to how Divvier_to_11_IG was derived from equivalent Divvier_to_11_GG.
Calling this instead of Divvier_to_11_GG in GoldGrab when number bars is 4
as calculation is 'guarenteed' correct (cf Divvier_to_11_GG has high probability but not certain)
and processing will be more efficient.
(However coding below may not be optimal as I have just modified the 'Divvier' code
to generate output equivalent to Divvier_to_11_GG. May make less clunky rendition later.)

*/

public class Divvier_of_4_GG
{ 
    public static void process(ArrayList<Integer> arrSet)    
    {	        
        
        // calculate the sum of arrSet elements
        int sum = 0;
        for(int elm : arrSet)
            sum += elm;  
        
        // assign elements to short-named int variable for convenience in processing combinations
        int a = arrSet.get(0);
        int b = arrSet.get(1);
        int c = arrSet.get(2);
        int d = arrSet.get(3);     
        
        ArrayList<Integer> arrSubSet = new ArrayList<Integer>(); // ArrayList to hold subset that gives min diff
        ArrayList<Integer> arrSubSetR = new ArrayList<Integer>(); // ArrayList to hold reciprocal set to arrSubSet         
        
        
        // split the set of four into two subsets, exploring all possible combinations
        //     and determine the differences between the sums of each subset pair
        int[] diff = new int[7]; // array to store values of differences 
        diff[0] = Math.abs( a - (b + c + d) ); 
        diff[1] = Math.abs( b - (a + c + d) );
        diff[2] = Math.abs( c - (a + b + d) );
        diff[3] = Math.abs( d - (a + b + c) );
        diff[4] = Math.abs( (a + b) - (c + d) );
        diff[5] = Math.abs( (a + c) - (b + d) );
        diff[6] = Math.abs( (a + d) - (c + b) );
        
        // determine the minimum difference
        int minDiff = diff[0];
        for(int i = 1; i < 7; i++)
            if(diff[i] < minDiff)
                minDiff = diff[i];
               

        // just assign 'best split subsets' to first combination that satisfies 
	if (diff[0] == minDiff) 
        {    
            arrSubSet.add(a);
            arrSubSetR.add(b);
            arrSubSetR.add(c);
            arrSubSetR.add(d);
        }    
        else if (diff[1] == minDiff)
        {    
            arrSubSet.add(b);
            arrSubSetR.add(a);
            arrSubSetR.add(c);
            arrSubSetR.add(d);
        }                         
        else if (diff[2] == minDiff)
        {      
            arrSubSet.add(c);
            arrSubSetR.add(a);
            arrSubSetR.add(b);
            arrSubSetR.add(d);
        }                
        else if (diff[3] == minDiff)
        {            
            arrSubSet.add(d);
            arrSubSetR.add(a);
            arrSubSetR.add(b);
            arrSubSetR.add(c);
        }  
        else if (diff[4] == minDiff) 
        {         
            arrSubSet.add(a);
            arrSubSet.add(b);
            arrSubSetR.add(c);
            arrSubSetR.add(d);
        }  
        else if (diff[5] == minDiff) 
        {         
            arrSubSet.add(a);
            arrSubSet.add(c);
            arrSubSetR.add(b);
            arrSubSetR.add(d);
        }  
        else // if (diff[6] == minDiff)  // (I think this condition is redundant)
        {      
            arrSubSet.add(a);
            arrSubSet.add(d);
            arrSubSetR.add(b);
            arrSubSetR.add(c);
        } 
        
        // (arrange in ascending order before sending to GUI; not important, though)
        Collections.sort(arrSubSet); 
        Collections.sort(arrSubSetR); 
            
        // calculate total of element values in arrSubSet
        int arrSubSet_total = 0;
        for(int elm : arrSubSet)
            arrSubSet_total += elm; 


        // message for attaining 'optimal split'
        //    (GoldGrab.sum - 2 * GoldGrab.tSum) represents difference between Mafia and player gold
        //    (always >= 0 as GoldGrab class does not call this class method otherwise)
        if(GoldGrab.sum - 2 * GoldGrab.tSum == minDiff)
        {
            GoldGrab.getFateArea().setFont(new Font("SANS_SERIF", Font.BOLD, 19));
            
//            GoldGrab.getFateArea().setForeground(new Color(0, 255, 0)); // green
//            GoldGrab.getFateArea().setForeground(new Color(180, 180, 0)); // dark yellow (as sides gold bars)
//            GoldGrab.getFateArea().setForeground(new Color(255, 220, 40)); // lighter yellow (as top bars)
            GoldGrab.getFateArea().setForeground(new Color(255, 222, 0)); // another yellow
            
//            GoldGrab.getFateArea().setBackground(new Color(0, 0, 0)); // black
//            GoldGrab.getFateArea().setBackground(new Color(22, 22, 240)); // trying to get same shade as title bar
            GoldGrab.getFateArea().setBackground(new Color(0, 0, 160)); // dark blue

            GoldGrab.getFateArea().setText(String.format("\n"
                    + "Congatulations - getting within %d of "
                    + "the Mafia's share, you did as well as you could\n\n "
                    + "                                               "
                    + "and recieve a bonus!", minDiff));            
            
            new Sound().playIt("Ta Da-SoundBible.com-1884170640.wav");
        }
        else
        {                
            GoldGrab.getFateArea().setFont(new Font("SANS_SERIF", Font.BOLD, 14));
            GoldGrab.getFateArea().setForeground(new Color(0, 0, 255)); // another yellow
            
            GoldGrab.getFateArea().setText(String.format("You got within  %d  of the Mafia haul\n\n", 
                    GoldGrab.sum - 2 * GoldGrab.tSum));
        
            GoldGrab.getFateArea().append(String.format("But you could have taken it to  %d\n\n", minDiff));

            GoldGrab.getFateArea().append(String.format("E.g. by taking bars  %s  (totalling  %d)\n", 
                    arrSubSet_total <= sum - arrSubSet_total ? arrSubSet : arrSubSetR,
                    arrSubSet_total <= sum - arrSubSet_total ? arrSubSet_total : sum - arrSubSet_total));  
            
            GoldGrab.getFateArea().append(String.format("\nLeaving  %s  (totalling  %d)  for the Mafia\n\n", 
                    arrSubSet_total <= sum - arrSubSet_total ? arrSubSetR : arrSubSet,
                    arrSubSet_total <= sum - arrSubSet_total ? sum - arrSubSet_total : arrSubSet_total));                                        
                        
            new Sound().playIt("Sheep-SoundBible.com-1847990075.wav");
        }

    }
}
