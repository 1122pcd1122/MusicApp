package activitytest.example.com.mymusic.network;

public class Resource<T> {

    /**
     * 状态
     */
    private Status status;

    /**
     * 数据
     */
    private T data;
    /**
     * 信息
     */
    private String message;

    public Resource() {
    }

    public Resource(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }


    /**
     * 网络连接成功并获取到数据
     * @param data 数据
     * @param message
     * @return 成功信息
     */
    public Resource<T> success(T data, String message){

        return new Resource<> ( Status.SUCCESS,data,message );
    }

    /**
     * 网络连接失败未获取到数据
     * @param message 失败信息
     * @return 失败信息
     */
    public Resource<T> error(String message){
        return new Resource<> ( Status.ERROR,data,message );
    }

    /**
     * 正在获取中
     * @param data 数据
     * @param message
     * @return 下载中的信息
     */
    public Resource<T> loading(T data, String message){
        return new Resource<> ( Status.LOADING,data,message );
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
