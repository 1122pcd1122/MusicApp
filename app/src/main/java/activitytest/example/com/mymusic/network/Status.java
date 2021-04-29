package activitytest.example.com.mymusic.network;

public enum Status {
    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 无网络
     */
    ERROR(404),
    /**
     * 加载中 出现错误
     */
    LOADING(403);

    private int code;

    Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
