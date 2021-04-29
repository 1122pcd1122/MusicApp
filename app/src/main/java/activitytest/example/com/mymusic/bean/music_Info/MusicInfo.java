package activitytest.example.com.mymusic.bean.music_Info;


import java.io.Serializable;

public class MusicInfo implements Serializable {

    private int code;
    private Data data;
    private String msg;
    private String profileId;
    private String reqId;
    private String tId;

    public void setCode(int code) {
         this.code = code;
     }
    public int getCode() {
         return code;
     }



    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

    public void setProfileId(String profileId) {
         this.profileId = profileId;
     }
     public String getProfileId() {
         return profileId;
     }

    public void setReqId(String reqId) {
         this.reqId = reqId;
     }
     public String getReqId() {
         return reqId;
     }

    public void setTId(String tId) {
         this.tId = tId;
     }
     public String getTId() {
         return tId;
     }

}