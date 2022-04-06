package com.web.guitarapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer { //This class will implement the
  // WebMvcConfigurer which will enable the call back functions

  @Override
  public void addViewControllers(ViewControllerRegistry registry) { //overriding a method with the object in the parameter of the mentioned class
    registry.addViewController("/login").setViewName("login"); // setting automated pre-configured controller which is the login page
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) { // overriding a method in order to handle
    // static informations like images, js and css.
      exposeDir("user-images", registry); // calling the function made belwo
  }

  private void exposeDir(String directoryName, ResourceHandlerRegistry registry) { // creating a function
    Path uploadDirectory = Paths.get(directoryName); // getting the name of the directory
    String uploadPath = uploadDirectory.toFile().getAbsolutePath(); // getting the absolute path where to save the file

    if (directoryName.startsWith("../")) directoryName = directoryName.replace("../", ""); // checking
    //condition and if matches then changing the name

    registry
        .addResourceHandler("/" + directoryName + "/**") // adding the resource handler
        .addResourceLocations("file:/" + uploadPath + "/"); // then setting the location.
  }
}
