package activitytest.example.com.mymusic.network;

import activitytest.example.com.mymusic.bean.UserInfo;
import activitytest.example.com.mymusic.bean.advice.Advice;
import activitytest.example.com.mymusic.bean.content.Content;
import activitytest.example.com.mymusic.bean.lyrics.Lyrisc;
import activitytest.example.com.mymusic.bean.musicLocation.MusicLocation;
import activitytest.example.com.mymusic.bean.music_Info.MusicInfo;
import activitytest.example.com.mymusic.bean.music_list.MusicListRoot;
import activitytest.example.com.mymusic.bean.playList.Info;
import activitytest.example.com.mymusic.bean.playList.PlayListRoot;
import activitytest.example.com.mymusic.bean.playList.PlayList;
import activitytest.example.com.mymusic.ui.lore.login.bean.LoginBean;
import activitytest.example.com.mymusic.ui.lore.login.register.bean.RegisterBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * 登录...
     * 发送post并通过json格式来将用户登录信息发送给服务器
     * @param loginBean 登录的账号和密码
     * @return 用户的个人信息
     */
    @Headers("Content-Type: application/json")
    @POST("CheckUser")

    Call<UserInfo> loginIngByJson(@Body LoginBean loginBean);

    /**
     * 注册...
     * @param registerBean 注册的信息
     * @return 用户的个人信息
     */
    @Headers ( "Content-Type: application/json" )
    @POST("Registered")
    Call<UserInfo> registerIngByJson(@Body RegisterBean registerBean);



    @POST("openMembership")
    Call<Boolean> postVipTag(@Body String phone);

    /**
     * 获取模糊查询
     * @param key 查询的内容
     * @return 模糊查询的内容
     */
    @GET("http://iecoxe.top:5000/v1/kugou/suggestSearch")
    Call<Advice> listAdvice(@Query ( "key" ) String key);


    /**
     * 获取精确查询内容
     * @param key 要查询的内容
     * @return 精确查询的内容
     */
    @GET("Search")
    Call<Content> listContent(@Query ( "key" ) String key);

    /**
     * 获取音乐详情
     * @param rid 音乐id
     * @return 音乐的详细信息
     */
    @GET("musicInfo")
    Call<MusicInfo> getMusicInfo(@Query ( "rid" ) long rid);


    /**
     * 获取音乐的播放地址
     * @param rid 音乐id
     * @return 音乐的播放地址
     */
    @GET("http://iecoxe.top:5000/v1/kuwo/song")
    Call<MusicLocation> getMusicLocation(@Query ( "rid" ) long rid);

    @POST("addSong")
    @Headers ( "Content-Type: application/json" )
    Call<PlayList> postPlayList(@Body PlayList playList);


    @GET("searchSong")
    Call<PlayListRoot> getPlayList(@Query ( "phone" ) String phone);

    @GET("http://iecoxe.top:5000/v1/kuwo/topCategory")
    Call<MusicListRoot> getMusicList();

    @GET("http://iecoxe.top:5000/v1/kuwo/lyric")
    Call<Lyrisc> getMusicLyric(@Query ( "rid" ) long rid);

    @GET("getVipSong")
    Call<PlayListRoot> getVipList();
}
