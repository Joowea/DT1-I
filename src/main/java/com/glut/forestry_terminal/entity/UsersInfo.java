package com.glut.forestry_terminal.entity;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class UsersInfo {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer userId;
    @Column(name = "name")
    private String userName;
    @Column(name = "idNumb")
    private String userIdNumb;
    @Column(name = "phone")
    private Double userPhone;
    @Column(name = "eMail")
    private String userEMail;
    @Column(name = "account")
    private String userAccount;
    @Column(name = "password")
    private String userPassword;
    @Column(name = "permission")
    private Boolean userPermission = false;
    @Column(name = "authorizedTime")
    private Integer userAuthorizedTimes = 0;
    @Column(name = "cutOffTime")
    private Long userCutOffTime = 0l;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserIdNumb() {
        return userIdNumb;
    }
    public void setUserIdNumb(String userIdNumb) {
        this.userIdNumb = userIdNumb;
    }
    public Double getUserPhone() {
        return userPhone;
    }
    public void setUserPhone(Double userPhone) {
        this.userPhone = userPhone;
    }
    public String getUserEMail() {
        return userEMail;
    }
    public void setUserEMail(String userEMail) {
        this.userEMail = userEMail;
    }
    public String getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public Boolean getUserPermission() {
        return userPermission;
    }
    public void setUserPermission(Boolean userPermission) {
        this.userPermission = userPermission;
    }
    public Integer getUserAuthorizedTimes() {
        return userAuthorizedTimes;
    }
    public void setUserAuthorizedTimes(Integer userAuthorizedTimes) {
        this.userAuthorizedTimes = userAuthorizedTimes;
    }
    public Long getUserCutOffTime() {
        return userCutOffTime;
    }
    public void setUserCutOffTime(Long userCutOffTime) {
        this.userCutOffTime = userCutOffTime;
    }
}
