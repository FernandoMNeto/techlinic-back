package br.com.clinic.services;

import br.com.clinic.entities.models.Contact;
import br.com.clinic.entities.models.UserInfo;
import br.com.clinic.repositories.UserInfoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements UserDetailsService {
    private final UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public ResponseEntity<UserInfo> registerUserInfo(UserInfo userInfo) {
        return ResponseEntity.ok(userInfoRepository.save(userInfo));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        return new User(userInfo.getUsername(), userInfo.getPassword(), userInfo.getAuthorities());
    }
}
