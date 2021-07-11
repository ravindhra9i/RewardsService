
package com.store.rewardservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${adminUserId}")
    private String adminUserId;

    @Value("${adminPassword}")
    private String adminPassword;

    @Value("${generalUserId}")
    private String generalUserId;

    @Value("${generalUserPassword}")
    private String generalUserPassword;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/rewards/getRewards/**").hasAnyRole("GENERAL_USER", "REWARDS_ADMIN")
                .antMatchers("/api/rewards/**").hasRole("REWARDS_ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authentication)
            throws Exception {
        authentication.inMemoryAuthentication()
                .withUser(adminUserId)
                .password(passwordEncoder().encode(adminPassword))
                .roles("REWARDS_ADMIN").and().withUser(generalUserId).password(passwordEncoder().encode(generalUserPassword)).roles("GENERAL_USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}