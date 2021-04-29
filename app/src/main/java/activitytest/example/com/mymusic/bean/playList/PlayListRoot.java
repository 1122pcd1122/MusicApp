
package activitytest.example.com.mymusic.bean.playList;
import java.util.List;


public class PlayListRoot {

    private int status;
    private List<Info> info;
    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setInfo(List<Info> info) {
         this.info = info;
     }
     public List<Info> getInfo() {
         return info;
     }

}