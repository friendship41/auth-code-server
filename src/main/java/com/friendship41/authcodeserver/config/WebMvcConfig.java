package com.friendship41.authcodeserver.config;

import com.friendship41.authcodeserver.config.log.ReqResLoggingInterceptor;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Autowired
  private ReqResLoggingInterceptor reqResLoggingInterceptor;

  @Override
  public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
    converters.stream()
        .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
        .findFirst()
        .ifPresent(converter -> ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(
            StandardCharsets.UTF_8));
  }

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry
        .addInterceptor(this.reqResLoggingInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/error");
  }
}
