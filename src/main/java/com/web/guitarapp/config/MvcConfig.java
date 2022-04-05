package com.web.guitarapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      exposeDir("user-images", registry);
  }

  private void exposeDir(String directoryName, ResourceHandlerRegistry registry) {
    Path uploadDirectory = Paths.get(directoryName);
    String uploadPath = uploadDirectory.toFile().getAbsolutePath();

    if (directoryName.startsWith("../")) directoryName = directoryName.replace("../", "");

    registry
        .addResourceHandler("/" + directoryName + "/**")
        .addResourceLocations("file:/" + uploadPath + "/");
  }
}
