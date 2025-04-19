package org.zerock.myapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zerock.myapp.service.JwtProvider;

import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private LoginSuccessUrlHandler loginSuccessUrlHandler;

    @Bean
    public BCryptPasswordEncoder BcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtProvider jwtProvider) throws Exception {
        return http
                .csrf().disable()
                .cors()
                .and()
                

                
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                ) // 테스트 용.
                
//                .authorizeHttpRequests(auth -> auth
//                	    
//                	    .requestMatchers(
//                	        "/auth/login",
//                	        "/employee/{empno}",
//                	        "/board/Notice",
//                	        "/board/Feedback/register",
//                	        "/project/status"
//                	    ).permitAll()
//                	    .requestMatchers(HttpMethod.GET, "/board/Notice/{id}").permitAll()
//                	    .requestMatchers(HttpMethod.GET, "/board/Feedback/{id}").permitAll()
//                	    .requestMatchers(HttpMethod.PUT, "/board/Feedback/{id}").permitAll()
//                	    .requestMatchers(HttpMethod.DELETE, "/board/Feedback/{id}").permitAll()
//
//                	    // 인사담당자.
//                	    .requestMatchers(
//                	        "/employee",
//                	        "/employee/**"
//                	    ).hasRole("HireManager")
//
//                	    // 프로젝트.
//                	    .requestMatchers(
//                	        "/project",
//                	        "/project/**",
//                	        "/project/upComing"
//                	    ).hasAnyRole("DepartmentLeader", "TeamLeader")
//                	    .requestMatchers(HttpMethod.GET, "/project/{id}").hasAnyRole("DepartmentLeader", "TeamLeader")
//                	    .requestMatchers(HttpMethod.DELETE, "/work/{id}").hasAnyRole("DepartmentLeader", "TeamLeader")
//
//                	    // 업무.
//                	    .requestMatchers(
//                	        "/work",
//                	        "/work/**"
//                	    ).hasAnyRole("Employee", "TeamLeader", "DepartmentLeader")
//                	    .requestMatchers(HttpMethod.GET, "/work/{id}").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader")
//                	    .requestMatchers(HttpMethod.PUT, "/work/{id}").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader")
//                	    .requestMatchers(HttpMethod.DELETE, "/work/{id}").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader")
//
//                	    // 게시판.
//                	    .requestMatchers(HttpMethod.GET, "/board/Notice/register").hasAnyRole("Employee", "TeamLeader")
//                	    .requestMatchers(HttpMethod.PUT, "/board/Notice/{id}").hasAnyRole("Employee", "TeamLeader")
//                	    .requestMatchers(HttpMethod.DELETE, "/board/Notice/{id}").hasAnyRole("Employee", "TeamLeader")
//
//                	    // 채팅.
//                	    .requestMatchers(HttpMethod.POST, "/chat").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "CEO")
//                	    .requestMatchers(HttpMethod.GET, "/chat/{id}").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "CEO")
//                	    .requestMatchers(HttpMethod.PUT, "/chat/{id}").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "CEO")
//                	    .requestMatchers(HttpMethod.GET, "/list/{empno}").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "CEO")
//                	    .requestMatchers(HttpMethod.DELETE, "/chat/{id}").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "CEO")
//                	    .requestMatchers(HttpMethod.GET, "/message").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "CEO")
//                	    .requestMatchers(HttpMethod.POST, "/message/{id}/summarize").hasAnyRole("Employee", "TeamLeader", "DepartmentLeader", "CEO")
//
//                	    // CEO
//                	    .requestMatchers(HttpMethod.GET, "/board/Feedback").hasRole("CEO")
//
//                	    
//                	    .anyRequest().authenticated()
//                	)

//                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                )
                .build(); 
    }
}
