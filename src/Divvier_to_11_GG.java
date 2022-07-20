/* _im41 (GoldGrab app intermediate 41)

-- Now calling sound method from new Sound object rather than class
to match change in Sound class code

*/

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Divvier_to_11_GG
{
    public static void process(ArrayList<Integer> arrSet)    
    {
        // calculate the sum of arrSet elements...
        int sum = 0;
        for(int elm : arrSet)
            sum += elm;        
                
        ArrayList<Integer> arrSubSet = new ArrayList<Integer>(); // // ArrayList to hold subset that gives min diff
    
        ArrayList<Integer> arrSubSetR = new ArrayList<Integer>(); // ArrayList to hold reciprocal set to arrSubSet        
                
        
        // find the two groupings with the smallest difference between them, 
        //     arrSubSet and its reciprocal subset, arrSubSetR...
        
        int minDiff, newDiff; // variables to hold values of smallest and newest difference value
        
        // initialise minDiff before loop so can begin with newDiff with a value that
        //     will be bigger than any subsequently-calculated actual diff 
        minDiff = sum; 
        
        // note re expressions below calculating differences:
        // arrSet.get(0) * 2 - sum   ...is equivalent to...  arrSet.get(0) - (sum - arrSet.get(0))  ...etc        
                
        // loop to repeat a randomised finding process based on shuffling arrSet
        // and comparing sum of first elements to that of the rest 
        // I do not know the number of repeats needed for reasonable probability of 
        // sampling all possibilities...may change it, base on expected set size
        // or code to allow user to input desired number repetitions based on  
        // requirements and available computing resources
        for(int repeat = 0; repeat < 1000; repeat++)
        {    
            // shuffle arrSet
            shuffleALm(arrSet);        

            newDiff = Math.abs( arrSet.get(0) * 2 - sum ); // first element shuffled array vs the sum of the rest 
            if(newDiff  <  minDiff)
            {
                arrSubSet.clear();
                arrSubSet.add(arrSet.get(0));
                minDiff = newDiff;
            }            

            newDiff = Math.abs(  2* (arrSet.get(0) + arrSet.get(1)) - sum ); // first 2 vs sum of rest
            if(newDiff  <  minDiff)
            {
                arrSubSet.clear();
                arrSubSet.add(arrSet.get(0));
                arrSubSet.add(arrSet.get(1));
                minDiff = newDiff;
            }
      
            if(arrSet.size() > 5)
            {    
                newDiff = Math.abs( 2* (arrSet.get(0) + arrSet.get(1) + arrSet.get(2)) - sum ); // first 3 vs sum of rest
                if(newDiff  <  minDiff)
                {
                    arrSubSet.clear();
                    arrSubSet.add(arrSet.get(0));
                    arrSubSet.add(arrSet.get(1));
                    arrSubSet.add(arrSet.get(2));
                    minDiff = newDiff;
                }
            }
            
            if(arrSet.size() > 7)
            {    
                newDiff = Math.abs( 2* (arrSet.get(0) + arrSet.get(1) + arrSet.get(2) + arrSet.get(3)) - sum ); // first 4 vs sum of rest
                if(newDiff  <  minDiff)
                {
                    arrSubSet.clear();
                    arrSubSet.add(arrSet.get(0));
                    arrSubSet.add(arrSet.get(1));
                    arrSubSet.add(arrSet.get(2));
                    arrSubSet.add(arrSet.get(3));
                    minDiff = newDiff;
                }
            }

            if(arrSet.size() > 9)
            {    
                newDiff = Math.abs( 2* (arrSet.get(0) + arrSet.get(1) + arrSet.get(2) + arrSet.get(3) + arrSet.get(4)) - sum ); // first 4 vs sum of rest
                if(newDiff  <  minDiff)
                {
                    arrSubSet.clear();
                    arrSubSet.add(arrSet.get(0));
                    arrSubSet.add(arrSet.get(1));
                    arrSubSet.add(arrSet.get(2));
                    arrSubSet.add(arrSet.get(3));
                    arrSubSet.add(arrSet.get(4));
                    minDiff = newDiff;
                }
            }
        }
        
                
        Collections.sort(arrSubSet); // arrange in ascending order before sending to GUI, for clearer user interpretation
        
        int arrSubSet_total = 0;
        for(int elm : arrSubSet)
            arrSubSet_total += elm; 
        
        
        // determine the reciprocal subset, arrSubSet:
        // first make a deep copy of arrSet (prefer to preserve arrSet in case want to access later)
        for(int i = 0; i < arrSet.size(); i++)
            arrSubSetR.add(arrSet.get(i));        
        // then remove arrSubSet elements
        for(int i = 0; i < arrSubSet.size(); i++)
            arrSubSetR.remove(arrSubSet.get(i));        
        
        Collections.sort(arrSubSetR); // arrange in ascending order before sending to GUI, for clearer user interpretation
      
        
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
            
            
            
            // _im41: now calling from an object rather than class...
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

            
            
            
            // _im41: now calling from an object rather than class...
            new Sound().playIt("Sheep-SoundBible.com-1847990075.wav");

            
            
            // may put something like the following here or elsewhere as appropriate later
            //    (note: not yet using the dedicated 4 and 5 processing algorithms amyway)
//            GoldGrab.getFateArea().append(String.format("There may be other combinations that give the same split\n"
//                    + "- program in its current form only stores first example it encounters\n"));
//
//            GoldGrab.getFateArea().append(String.format("Alogrithms for input sets of >5 numbers use random sampling,\n"
//                    + "but method for 6-11 numbers, as here, is quite reliable:\n"
//                    + "test running a set of 10 numbers 100 times gave same minimum difference each time.\n"));

        }
        
    } // end method process
    
    
    // Method based on my ShuffleAL_2 class; see code comments therin (and in precursor ShuffleAL)
    // Could/should probably try replacing with a shuffle method from the API - just used ny own as I did not know of alternatives at the tme
    public static ArrayList<Integer> shuffleALm( ArrayList<Integer> arrL )  
    {
        Random rnd = new Random(); 
                
        int indexA = 0;
        
        for(int repeat = 0; repeat < (arrL.size() + rnd.nextInt(2)); repeat++) // see Swop2 for comments re loop body code
        {
            int temp = arrL.get(indexA); 
            arrL.remove(indexA);  
            int indexB = rnd.nextInt(arrL.size());  
            arrL.add(indexA, arrL.get(indexB));
            arrL.remove(indexB < indexA ? indexB : indexB + 1); 
            arrL.add((indexB < indexA ? indexB : indexB + 1), temp); 
            if(indexA < arrL.size() - 1)
                ++indexA;
        }        
        
        return arrL;
        
    } // end method ShuffleALm
    
}


/*

Would be nice to have an estimate of the number of time the outer loop needs to 
run for a given level of confidence that all combinations have been sampled.

If more than one sample subset gives the same minimum difference value, only
one is given in the final report per run of the program. (Might be possible to 
'remember' and report all equivalent values/sets at the end as in Divvier_of_4 
and Divvier_of_5 (which have all hard-coded combinations)?)


*/