
package activitytest.example.com.mymusic.bean.music_list;


public class Music_list {

    private long id;
    private String name;
    private String pic;
    private String album_name;
    private String artist_name;
    private int mv_status;
    private Mv_pay_info mv_pay_info;
    private String pay;
    private int online;
    private Pay_info pay_info;
    public void setId(long id) {
         this.id = id;
     }
     public long getId() {
         return id;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setPic(String pic) {
         this.pic = pic;
     }
     public String getPic() {
         return pic;
     }

    public void setAlbum_name(String album_name) {
         this.album_name = album_name;
     }
     public String getAlbum_name() {
         return album_name;
     }

    public void setArtist_name(String artist_name) {
         this.artist_name = artist_name;
     }
     public String getArtist_name() {
         return artist_name;
     }

    public void setMv_status(int mv_status) {
         this.mv_status = mv_status;
     }
     public int getMv_status() {
         return mv_status;
     }

    public void setMv_pay_info(Mv_pay_info mv_pay_info) {
         this.mv_pay_info = mv_pay_info;
     }
     public Mv_pay_info getMv_pay_info() {
         return mv_pay_info;
     }

    public void setPay(String pay) {
         this.pay = pay;
     }
     public String getPay() {
         return pay;
     }

    public void setOnline(int online) {
         this.online = online;
     }
     public int getOnline() {
         return online;
     }

    public void setPay_info(Pay_info pay_info) {
         this.pay_info = pay_info;
     }
     public Pay_info getPay_info() {
         return pay_info;
     }

}