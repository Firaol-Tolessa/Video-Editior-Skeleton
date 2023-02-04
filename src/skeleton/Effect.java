
package skeleton;

import javafx.scene.Node;


public class Effect {
   private String startTime;
   private String endTime;
   private Node node;
   
   public Effect(Node node, String startTime,String endTime ){
       this.node = node;
       this.startTime = startTime;
       this.endTime = endTime;
   }
   public int getEndTime(){
       return Integer.parseInt(endTime);
   }
}
