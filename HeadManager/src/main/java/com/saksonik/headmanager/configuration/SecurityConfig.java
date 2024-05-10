package com.saksonik.headmanager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        // call-schedule
                        .requestMatchers(HttpMethod.GET, "/call-schedule")
                        .authenticated()
                        .requestMatchers(HttpMethod.POST, "/call-schedule")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/call-schedule")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/call-schedule/{id}")
                        .hasRole("ADMIN")
                        //class
                        .requestMatchers(HttpMethod.GET, "/class/{classId}")
                        .authenticated()
                        //classroom
                        .requestMatchers(HttpMethod.GET, "/classroom")
                        .authenticated()
                        //lesson-timetable
                        .requestMatchers(HttpMethod.GET, "/lesson-timetable/for-class/{classId}")
                        .hasAnyRole("STUDENT", "PARENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/lesson-timetable/for-teacher/{teacherId}")
                        .hasAnyRole("TEACHER", "ADMIN")
                        //marks
                        .requestMatchers(HttpMethod.GET, "/marks/for-student/{studentId}")
                        .hasAnyRole("STUDENT", "PARENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/marks/for-class/{classId}/{subjectId}")
                        .hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/marks")
                        .hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/marks/{id}")
                        .hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/marks/{id}")
                        .hasAnyRole("TEACHER", "ADMIN")
                        //meeting-schedule
                        .requestMatchers(HttpMethod.GET, "/meeting-schedule/list/{id}")
                        .hasAnyRole("STUDENT", "PARENT", "CLASSROM_TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/meeting-schedule/{id}")
                        .hasAnyRole("STUDENT", "PARENT", "CLASSROM_TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/meeting-schedule")
                        .hasAnyRole("CLASSROM_TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/meeting-schedule/{id}")
                        .hasAnyRole("CLASSROM_TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/meeting-schedule/{id}")
                        .hasAnyRole("CLASSROM_TEACHER", "ADMIN")
                        //profile
                        .requestMatchers(HttpMethod.GET, "/profile/{userId}")
                        .authenticated()
                        //subject
                        .requestMatchers(HttpMethod.GET, "/subject/{subject}")
                        .authenticated()
                        .requestMatchers(HttpMethod.GET, "/subject/for-class/{classId}")
                        .authenticated()
                        .requestMatchers(HttpMethod.GET, "/subject/for-student/{studentId}")
                        .authenticated()
                        //user
                        .requestMatchers(HttpMethod.GET, "/user/{userId}")
                        .authenticated()
                        .requestMatchers(HttpMethod.GET, "/user/students/{classId}")
                        .authenticated()
                        //userfeed
                        .requestMatchers(HttpMethod.GET, "/userfeed")
                        .authenticated()
                        .anyRequest().denyAll()
                )
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()))
                .build();
    }

}
