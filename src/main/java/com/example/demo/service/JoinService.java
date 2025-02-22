package com.example.demo.service;

import com.example.demo.dto.JoinDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.exceptions.JikimException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) throws JikimException {

        boolean exist = userRepository.existsByUsername(joinDTO.getUsername());
//        List<UserEntity> list = userRepository.findByUsername(joinDTO.getUsername());

        log.info("exist : {} ", exist);
        if(exist){
            log.info("이미 존재하는데?");
            throw new JikimException("기존 회원");
        } else {
            UserEntity user = new UserEntity();
            user.setUsername(joinDTO.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
//            user.setRole("ROLE_USER");
            user.setRole(joinDTO.getRole());
            userRepository.save(user);
        }

    }
}
