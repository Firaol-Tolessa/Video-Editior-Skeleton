
package skeleton;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Trim {
    public Trim(){
         try {
            //String [] command = {"cmd /k start", "tree"};
             String videoPath = "C:\\Users\\SWL\\Downloads\\Documents\\output.mp4";
             String check = "cmd /k start ffmpeg -ss 00:00:00 -t 00:00:30 -i " + videoPath +" -acodec copy \\-v codec copy sth.mp4";
             System.out.println(check);
            Process stitch = Runtime.getRuntime().exec(check);
            
//              
        } catch (IOException ex) {
            Logger.getLogger(Trim.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Trim(int startTime , int endTime){
        try {
            String [] command = {"cmd.exe /k start", " tree"};
            Process stitch = Runtime.getRuntime().exec(command);
//              
        } catch (IOException ex) {
            Logger.getLogger(Trim.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
