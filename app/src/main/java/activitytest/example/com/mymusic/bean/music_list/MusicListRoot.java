/**
  * Copyright 2021 bejson.com 
  */
package activitytest.example.com.mymusic.bean.music_list;
import java.util.List;

/**
 * Auto-generated: 2021-03-29 20:29:31
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class MusicListRoot {

    private boolean success;
    private int code;
    private List<Data> data;
    public void setSuccess(boolean success) {
         this.success = success;
     }
     public boolean getSuccess() {
         return success;
     }

    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

}