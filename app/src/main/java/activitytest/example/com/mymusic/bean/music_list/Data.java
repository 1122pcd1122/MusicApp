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
public class Data {

    private String name;
    private List<Music_item_List> list;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setList(List<Music_item_List> list) {
         this.list = list;
     }
     public List<Music_item_List> getList() {
         return list;
     }

}