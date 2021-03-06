package com.boards.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class RetroBoardSecurityConfig extends WebSecurityConfigurerAdapter {

    private RetroBoardTokenAuthenticationEntryPoint retroBoardTokenAuthenticationEntryPoint;
    private RetroBoardTokenAuthenticationFilter retroBoardTokenAuthenticationFilter;

    @Autowired
    public RetroBoardSecurityConfig(RetroBoardTokenAuthenticationEntryPoint retroBoardTokenAuthenticationEntryPoint, RetroBoardTokenAuthenticationFilter retroBoardTokenAuthenticationFilter) {
        this.retroBoardTokenAuthenticationEntryPoint = retroBoardTokenAuthenticationEntryPoint;
        this.retroBoardTokenAuthenticationFilter = retroBoardTokenAuthenticationFilter;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/token/**",
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable().and()
                .cors()
                .and().csrf().disable()
                .authorizeRequests().antMatchers("/actuator/**", "/retro-websocket/**", "/login/**", "/h2-console/**").permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(retroBoardTokenAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(retroBoardTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
