/**
  * Copyright 2021 bejson.com 
  */
package activitytest.example.com.mymusic.bean.advice;
import java.util.List;

public class Advice {

    private int status;
    private int ErrorCode;
    private int error_code;
    private List<Data> data;
    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setErrorCode(int ErrorCode) {
         this.ErrorCode = ErrorCode;
     }
     public int getErrorCode() {
         return ErrorCode;
     }

    public void setError_code(int error_code) {
         this.error_code = error_code;
     }
     public int getError_code() {
         return error_code;
     }

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

}