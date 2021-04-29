/**
  * Copyright 2021 bejson.com 
  */
package activitytest.example.com.mymusic.bean.advice;


public class RecordDatas {

    private String HintInfo;
    private int MatchCount;
    private int IsRadio;
    private long Hot;
    public void setHintInfo(String HintInfo) {
         this.HintInfo = HintInfo;
     }
     public String getHintInfo() {
         return HintInfo;
     }

    public void setMatchCount(int MatchCount) {
         this.MatchCount = MatchCount;
     }
     public int getMatchCount() {
         return MatchCount;
     }

    public void setIsRadio(int IsRadio) {
         this.IsRadio = IsRadio;
     }
     public int getIsRadio() {
         return IsRadio;
     }

    public void setHot(long Hot) {
         this.Hot = Hot;
     }
     public long getHot() {
         return Hot;
     }

}