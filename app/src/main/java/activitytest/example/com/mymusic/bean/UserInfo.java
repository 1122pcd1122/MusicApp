package activitytest.example.com.mymusic.bean;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {

        private String phone;
        private String pwd;
        private int status;
        private String name;
        private String sex;
        private String birthday;
        private String area;
        private String IdCard;
        private String permissions;
        private String photo;
        private String describe;
        public void setPhone(String phone) {
            this.phone = phone;
        }
        public String getPhone() {
            return phone;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
        public String getPwd() {
            return pwd;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
        public String getSex() {
            return sex;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
        public String getBirthday() {
            return birthday;
        }

        public void setArea(String area) {
            this.area = area;
        }
        public String getArea() {
            return area;
        }

        public void setIdCard(String IdCard) {
            this.IdCard = IdCard;
        }
        public String getIdCard() {
            return IdCard;
        }

        public void setPermissions(String permissions) {
            this.permissions = permissions;
        }
        public String getPermissions() {
            return permissions;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
        public String getPhoto() {
            return photo;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }
        public String getDescribe() {
            return describe;
        }
}
