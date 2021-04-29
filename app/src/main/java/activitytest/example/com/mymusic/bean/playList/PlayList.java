package activitytest.example.com.mymusic.bean.playList;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PlayList {
    private String phone;
    private String artist;
    private long rid;
    private String songTimeMinutes;
    private int isListenFee;
    private String name;



    public PlayList(String phone, String artist, long rid, String songTimeMinutes, int isListenFee, String name) {
        this.phone = phone;
        this.artist = artist;
        this.rid = rid;
        this.songTimeMinutes = songTimeMinutes;
        this.isListenFee = isListenFee;
        this.name = name;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public String getSongTimeMinutes() {
        return songTimeMinutes;
    }

    public void setSongTimeMinutes(String songTimeMinutes) {
        this.songTimeMinutes = songTimeMinutes;
    }

    public int getIsListenFee() {
        return isListenFee;
    }

    public void setIsListenFee(int isListenFee) {
        this.isListenFee = isListenFee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
