/* _m41 (GoldGrab app intermediate 41) 

-- Made change mentioned as option in last iteration:
As getResource() is a non-static method had added non-static 
method 'playIt2' called and passed argument indirectly via original static method name.
Now making new Sound object in calling classes (GoldGrab, Divvier_to_11_GG)
i.e. new Sound.playit("name of file") and have deleted the static method here
and renamed the non-static method to 'playit'



Genaral background notes (keep these):

Got the audio play code* from
http://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java

(Still have not found a simple way to play ~10x smaller mp3 files instead (/as well)
...JavaFX gets mentioned a lot, but I could not get to work yet)

Note that to allow audio files to play from JAR (as well as NetBeans)
they should be put in source code folder and accessed via getResource() 
(as below), not referenced as File

Attributions for audio files (wav) played by this code:
royalty-free 
-- gunshot http://soundbible.com/2004-Gun-Shot.html Recorded by Marvin 
-- TaDah http://soundbible.com/1003-Ta-Da.html Recorded by Mike Koenig 
-- sheep http://soundbible.com/1012-Sheep.html recorded by Caroline Ford


*/


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
 
public class Sound
{
    
    
    
    // now directly accessed as a non-static method...
    public void playIt(String filename) 
    { 
        try 
        {
            AudioInputStream aIS = AudioSystem.getAudioInputStream(getClass().getResource(filename));            
            Clip clip = AudioSystem.getClip();
            clip.open(aIS);
            clip.start();
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }    
    }
}