package com.glut.forestry_terminal.repository;

import com.glut.forestry_terminal.entity.UsersInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoRepository extends JpaRepository<UsersInfo,String> {
    UsersInfo findAllByUserAccount(String userAccount);
    List<UsersInfo> findAllByUserAccountAndUserPassword(String userAccount, String userPassword);
    UsersInfo findAllByUserId(Integer userId);
}
