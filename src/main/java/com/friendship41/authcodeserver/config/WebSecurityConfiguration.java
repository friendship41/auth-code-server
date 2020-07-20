package com.friendship41.authcodeserver.config;

import com.friendship41.authcodeserver.common.MemberUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private MemberUserDetailsService memberUserDetailsService;

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers("/loginForm")
        .permitAll()
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/logout/result")
        .permitAll()
        .invalidateHttpSession(true)
        .and()
        .formLogin()
        .loginPage("/loginForm")
        .loginProcessingUrl("/login")
        .successHandler(this.kakaoAuthenticationSuccessHandler())
        .and()
        .csrf().disable();
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(memberUserDetailsService);
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public KakaoAuthenticationSuccessHandler kakaoAuthenticationSuccessHandler() {
    return new KakaoAuthenticationSuccessHandler();
  }
}
