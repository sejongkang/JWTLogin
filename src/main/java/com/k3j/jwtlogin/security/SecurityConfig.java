package com.k3j.jwtlogin.security;

import com.k3j.jwtlogin.domain.User;
import com.k3j.jwtlogin.filter.CustomAuthenticationFilter;
import com.k3j.jwtlogin.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 패스워드 인코더 설정
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    // WebSecurity 설정이 아닌, HttpSecurity로 설정해야함.
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        // 로그인 요청 url 정의. 스프링에서 컨트롤러 처리. 기본값은 /login.
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        // 로그인 요청은 인증없이 모두 허용.
        http.authorizeRequests().antMatchers("/api/login").permitAll();
        // user 관련 엔드포인트는 USER 권한만
        http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority("ROLE_USER");
        // user 관련 엔드포인트에서 save 관련은 ADMIN 권한만
        http.authorizeRequests().antMatchers(GET, "/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");
        // 나머지는 모두 인증 필요.
        http.authorizeRequests().anyRequest().authenticated();
        // filter에 authenticationManger 객체 주입.
        http.addFilter(customAuthenticationFilter);
        // UsernamePassword필터보다 먼저 실행 됨.
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
