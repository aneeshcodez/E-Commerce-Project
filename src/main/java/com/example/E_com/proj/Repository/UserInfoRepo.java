package com.example.E_com.proj.Repository;

import com.example.E_com.proj.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepo extends JpaRepository<UserInfo,Integer> {

    Optional<UserInfo> findByName(String username);
}
