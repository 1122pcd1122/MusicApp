package activitytest.example.com.mymusic.bean.music_Info;
import java.util.Date;


public class Data {

    private String artist;
    private String pic;
    private long rid;
    private Date releaseDate;
    private long artistid;
    private String songTimeMinutes;
    private boolean isListenFee;
    private String pic120;
    private String name;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getArtistid() {
        return artistid;
    }

    public void setArtistid(long artistid) {
        this.artistid = artistid;
    }

    public String getSongTimeMinutes() {
        return songTimeMinutes;
    }

    public void setSongTimeMinutes(String songTimeMinutes) {
        this.songTimeMinutes = songTimeMinutes;
    }

    public boolean isListenFee() {
        return isListenFee;
    }

    public void setListenFee(boolean listenFee) {
        isListenFee = listenFee;
    }

    public String getPic120() {
        return pic120;
    }

    public void setPic120(String pic120) {
        this.pic120 = pic120;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}