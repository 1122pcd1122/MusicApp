/**
  * Copyright 2021 bejson.com 
  */
package activitytest.example.com.mymusic.bean.content;
import java.util.List;


public class Content {

    private List<ContentInfo> list;
    public void setList(List<ContentInfo> list) {
         this.list = list;
     }
     public List<ContentInfo> getList() {
         return list;
     }

}