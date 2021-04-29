package activitytest.example.com.mymusic.ui.lore.login.register.bean;

public class RegisterBean {

    private String phoneNumber;
    private String userName;
    private String passWord;
    private String confirmPassWord;

    public RegisterBean(String phoneNumber, String userName, String passWord, String confirmPassWord) {
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.passWord = passWord;
        this.confirmPassWord = confirmPassWord;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getConfirmPassWord() {
        return confirmPassWord;
    }

    public void setConfirmPassWord(String confirmPassWord) {
        this.confirmPassWord = confirmPassWord;
    }
}
