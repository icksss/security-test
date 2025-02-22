package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public RoleHierarchy roleHierarchy() {
        //버전업으로 변경됨 (6.3.x 부터 사용안됨)
//        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
//        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER\n" +
//                "ROLE_MANAGER > ROLE_USER");
//        return hierarchy;
        //withDefaultRolePrefix 자동으로 ROLE_ 붙혀줌
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("MANAGER")
                .role("MANAGER").implies("USER")
                .build();

        /**
         * 또는 이렇게
         * return RoleHierarchyImpl.fromHierarchy("""
         *             ROLE_C > ROLE_B
         *             ROLE_B > ROLE_A
         *             """);
         */



    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll()  //순서가 중요 큰범위에서 작은 범위로 줄여나가야함
                        .requestMatchers("/").hasAnyRole("USER")
                        .requestMatchers("/manager").hasAnyRole("MANAGER")
                        .requestMatchers("/admin").hasAnyRole("ADMIN")
//                        .requestMatchers("/admin").hasRole("ADMIN")
//                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                        .anyRequest().authenticated()
                );

//        http
//                .httpBasic(Customizer.withDefaults());

        //http basic을 위해 formLogin 주석
        http
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );

        http
                .csrf((auth) -> auth.disable()); //lgoin page 의 form 에 csrf token이 필요하기 때문에 잠시 닫음
//rest api 의 경우 disable 해도됨
        http
                .sessionManagement((auth) -> auth
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(true)
                       );
        /*
        * maximumSession(정수) : 하나의 아이디에 대한 다중 로그인 허용 개수
          maxSessionPreventsLogin(불린) : 다중 로그인 개수를 초과하였을 경우 처리 방법
            true : 초과시 새로운 로그인 차단
            false : 초과시 기존 세션 하나 삭제
        * */

        //세션 탈취 방지를 위해 세션id를 변경해주는 설정 changeSessionId 로 보통 보호
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId()
                );
        http
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
