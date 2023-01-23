
package skeleton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;



public class Trim {
    public void Cut(Media media, File file , double trimStart , double trimEnd){
        int mediaEnd = (int)media.getDuration().toSeconds();
        String check[] = {"cmd" , "/c", "start", "ffmpeg","-ss",trimStart+"","-t",trimEnd+"","-i",file.getAbsolutePath(),"-acodec","copy"
        ,"-vcodec","copy","assets\\video\\cut.mp4"};
        if(trimStart == 0){
            String upper[] = {"cmd" , "/c", "start", "ffmpeg","-ss",trimEnd+"","-t",mediaEnd+"","-i",file.getAbsolutePath(),"-acodec","copy"
        ,"-vcodec","copy","assets\\video\\lower.mp4"};
        }else if(trimEnd == mediaEnd){
            String lower[] = {"cmd" , "/c", "start", "ffmpeg","-ss",0+"","-t",trimStart+"","-i",file.getAbsolutePath(),"-acodec","copy"
        ,"-vcodec","copy","assets\\video\\lower.mp4"};
        }else{
            String lower[] = {"cmd" , "/c", "start", "ffmpeg","-ss",0+"","-t",trimStart+"","-i",file.getAbsolutePath(),"-acodec","copy"
        ,"-vcodec","copy","assets\\video\\lower.mp4"};
            String upper[] = {"cmd" , "/c", "start", "ffmpeg","-ss",trimEnd+"","-t",mediaEnd+"","-i",file.getAbsolutePath(),"-acodec","copy"
        ,"-vcodec","copy","assets\\video\\upper.mp4"};
           try {
               Process cutUpper = Runtime.getRuntime().exec(upper);
               Process cut = Runtime.getRuntime().exec(check);
               Process cutLower = Runtime.getRuntime().exec(lower);
           } catch (IOException ex) {
               Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
           }
           
        }
        
    }
    
    public File Merge(String lower , String upper){
        try {
            File mergeFile = new File("merge.txt");
            BufferedWriter outStream = new BufferedWriter( new FileWriter(mergeFile));
            outStream.write("file assets/video/" + lower);
            outStream.newLine();
            outStream.write("file assets/video/" + upper);
            outStream.close();
        } catch (IOException ex) {
            Logger.getLogger(Trim.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String command[] = {"cmd" , "/c", "start", "ffmpeg","-f","concat","-i","merge.txt","-c","copy","assets/video/output.mp4"};
        try {
            Process merge = Runtime.getRuntime().exec(command);
            
        } catch (IOException ex) {
            Logger.getLogger(Trim.class.getName()).log(Level.SEVERE, null, ex);
        }
        File output = new File("assets\\video\\output.mp4");
        for (int i = 0; i < 1000; i++) {
            
        }
        return output;
    }
}
