package com.glut.forestry_terminal.controller;

import com.glut.forestry_terminal.entity.UsersInfo;
import com.glut.forestry_terminal.repository.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InfoContro {
    @Autowired
    private InfoRepository infoRepository;
    /**
     * 获取全部列表
     * @return
     */
    @GetMapping(value = "/infoHello")
    public List<UsersInfo> GetList(){
        return infoRepository.findAll();
    }
    /**
     * 信息注册“增”
     * @param userAccount
     * @param password
     * @param passwordAgain
     * @param userName
     * @param userIdNumb
     * @param userPhone
     * @return
     */
    @PostMapping(value = "/register/{userAccount}&{password}&{passwordAgain}&{userName}&{userIdNumb}&{userPhone}&{userEMail}")
    public String Register(@PathVariable("userAccount") String userAccount,
                           @PathVariable("password") String password,
                           @PathVariable("passwordAgain") String passwordAgain,
                           @PathVariable("userName") String userName,
                           @PathVariable("userIdNumb") String userIdNumb,
                           @PathVariable("userPhone") Double userPhone,
                           @PathVariable("userEMail") String userEMail){
        UsersInfo accountProve = infoRepository.findAllByUserAccount(userAccount);
        if (userAccount!=null&&password!=null&&passwordAgain!=null&&userName!=null&&userIdNumb!=null&&userPhone!=null&&userEMail!=null){
            if (accountProve == null){
                if(password.equals(passwordAgain)){
                    UsersInfo usersInfo = new UsersInfo();
                    usersInfo.setUserName(userName);
                    usersInfo.setUserIdNumb(userIdNumb);
                    usersInfo.setUserPhone(userPhone);
                    usersInfo.setUserEMail(userEMail);
                    usersInfo.setUserAccount(userAccount);
                    usersInfo.setUserPassword(password);
                    infoRepository.save(usersInfo);
                    return "RegisterSuccess";
                }else {
                    return "DifferentPassword";
                }
            }else{
                return "TheSameAccount";
            }
        }else {
            return "SomeContextEmpty";
        }
    }

    /**
     * 更改授权时间 “改1”
     * @param userAccount 前端账号
     * @param extraTime
     * @return
     */
    @PutMapping(value = "/addClock/{userAccount}&{extraTime}")
    public String UsersAuthorizedTimeUpdate(@PathVariable("userAccount") String userAccount,
                                            @PathVariable("extraTime") Long extraTime) {
        UsersInfo usersInfo = infoRepository.findAllByUserAccount(userAccount);
        Integer authorizedTimes = usersInfo.getUserAuthorizedTimes();//获取之前充值次数
        //判断user是否有权限
        if (usersInfo.getUserPermission()) {
            //有权限：充值时间+截止时间
            Long cutOffTimeBefore = usersInfo.getUserCutOffTime(); //获取之前截止时间
            Long cutOffTime = cutOffTimeBefore + extraTime; //之前截止时间+前端传入的充值时间=充值后的截止时间
            usersInfo.setUserCutOffTime(cutOffTime);
        }else{ //无权限：充值时间+当前时间
            Long curTime = System.currentTimeMillis();
            usersInfo.setUserCutOffTime(curTime+extraTime);
            usersInfo.setUserPermission(true); //设置权限为true
        }
        usersInfo.setUserAuthorizedTimes(authorizedTimes + 1); //充值次数+1
        infoRepository.save(usersInfo);
        return "AuthorizedSuccess";
    }
    /**
     * 登陆验证 “查1”
     * @param userAccount
     * @param userPassword
     * @return
     */
    @GetMapping(value = "/login/{userAccount}&{userPassword}")
    public String Login(@PathVariable("userAccount") String userAccount,
                         @PathVariable("userPassword") String userPassword){
        UsersInfo accountProve = infoRepository.findAllByUserAccount(userAccount);
        List<UsersInfo> accAndPassProve = infoRepository.findAllByUserAccountAndUserPassword(userAccount,userPassword);
        if (accountProve!=null){
            if (!accAndPassProve.isEmpty()){
                return "LoginSuccess";
            } else {
                return "WrongPassword";
            }
        }else {
            return "Non-existentAccount";
        }
    }
    /**
     * 查询剩余时间 “查2”
     * @param userAccount
     * @return
     */
    @GetMapping(value = "/checkTime/{userAccount}")
    public Long CheckCutOffTime (@PathVariable("userAccount") String userAccount){
        UsersInfo usersInfo = infoRepository.findAllByUserAccount(userAccount);
        Long cutOffTime = usersInfo.getUserCutOffTime();
        return cutOffTime;
    }
    /**
     * 根据cutOffTime与当前按时间的差值 60秒循环更新新用户权限
     *  ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！-----临时方法-----！！！！！！！！！！！！！！！！！！
     */
    @Scheduled(fixedDelay = 1000*60)
    public void ScheduledCutOffTime(){
        Integer i = 1;  //id序号从1开始
        while(i < 20){
            UsersInfo usersInfo = infoRepository.findAllByUserId(i);
            if(usersInfo != null) {  //空id序号跳过
                Long cutOffTime = usersInfo.getUserCutOffTime();  //获取当前id序号用户的授权截止时间
                Long curTime = System.currentTimeMillis();  //获取当前系统时间
                Long Res = cutOffTime - curTime;  //当前时间与系统时间时间差
                if (Res <= 0l) {
                    usersInfo.setUserPermission(false);
                    infoRepository.save(usersInfo);
                }
            }
            i++;
        }
    }
}
