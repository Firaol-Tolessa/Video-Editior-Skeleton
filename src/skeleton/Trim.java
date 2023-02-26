
package skeleton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;


public class Trim {

    public File Cut(Media media, File file , double trimStart , double trimEnd){
//        File fileTobeCut = file;
        String output;
        Random number = new Random();
        int mediaEnd = (int)media.getDuration().toSeconds();
//        if (new File("assets/video/"+file.hashCode()+".mp4").exists()) {
//            fileTobeCut = new File("assets/video/"+file.hashCode()+".mp4");
//        }
        if(new File("assets/video/"+file+".mp4").exists()){
            output = number.nextInt()*0.00000125+".mp4";
        }else{
            output = number.nextInt()*0.0000036+".mp4";
        }
        System.out.println("Cutting : " + file);
        String check[] = {"cmd" , "/c", "start", "ffmpeg","-ss",trimStart+"","-t",trimEnd+"","-i",file.getAbsolutePath(),"-acodec","copy"
        ,"-vcodec","copy","assets\\video\\"+output};
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
              // Process cutUpper = Runtime.getRuntime().exec(upper);
    
               Process cut = Runtime.getRuntime().exec(check);
               //Process cutLower = Runtime.getRuntime().exec(lower);
              
               
           } catch (IOException ex) {
              // Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
               System.err.println(ex.getMessage());
           }
           
        }
        System.out.println("Ouput : " + output);
//        System.out.println("File to be cut  : " + fileTobeCut.toURI().toString());
        return new File("assets/video/" + output);
    }
   //For future project idea :) 
    
    
    public static File Merge(String lower , String upper){
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
      
        return output;
    }
    
     public static File Merge(String mergeFile){
         String command[] = {"cmd" , "/c", "start", "ffmpeg","-f","concat","-safe","0","-i",mergeFile,"-c","copy","assets/video/merged.mp4"};
       
      
         try {
            Process merge = Runtime.getRuntime().exec(command);
//            while(merge.isAlive()){
//                System.out.println("In progressss .....");
//            }
        } catch (IOException ex) {
            Logger.getLogger(Trim.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new File("assets\\video\\merged.mp4");
     }
    
}
