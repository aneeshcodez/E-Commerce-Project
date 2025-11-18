package com.example.E_com.proj.Config;

import com.example.E_com.proj.Entity.UserInfo;
import com.example.E_com.proj.Repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component

public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    UserInfoRepo userInfoRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> optionalUserInfo = userInfoRepo.findByName(username);
        // we are returning an Obj of type UserDetails
        return  optionalUserInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }
}
