package activitytest.example.com.mymusic.bean.playList;


public class Info {

    private int id;
    private String song;
    private String name;
    private String artist;
    private String time;
    private int isListenFee;
    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setSong(String song) {
         this.song = song;
     }
     public String getSong() {
         return song;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setArtist(String artist) {
         this.artist = artist;
     }
     public String getArtist() {
         return artist;
     }

    public void setTime(String time) {
         this.time = time;
     }
     public String getTime() {
         return time;
     }

    public void setIsListenFee(int isListenFee) {
         this.isListenFee = isListenFee;
     }
     public int getIsListenFee() {
         return isListenFee;
     }

}