package nh.graphql.beeradvisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import nh.graphql.beeradvisor.login.DumbAuthenticationFilter;

@SpringBootApplication
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BeeradvisorApplication extends WebSecurityConfigurerAdapter {

  /**
   * Keeps the session open until the end of a request. Allows us to use
   * lazy-loading with Hibernate.
   */
  @Bean
  public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
    return new OpenEntityManagerInViewFilter();
  }

  // @Bean
  // public WebMvcConfigurer corsConfigurer() {
  // return new WebMvcConfigurer() {
  // @Override
  // public void addCorsMappings(CorsRegistry registry) {
  // registry.addMapping("/graphql"); // .allowedOrigins("http://localhost:9080");
  // }
  // };
  // }

  @Bean
  public FilterRegistrationBean<CorsFilter> corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("http://localhost:9080");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
    bean.setOrder(0);
    return bean;
  }

  @Bean
  public DumbAuthenticationFilter authenticationTokenFilter() throws Exception {
    return new DumbAuthenticationFilter();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().antMatchers("/").permitAll();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(authenticationTokenFilter(), BasicAuthenticationFilter.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(BeeradvisorApplication.class, args);
  }
}
