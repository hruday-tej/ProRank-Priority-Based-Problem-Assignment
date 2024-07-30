package dev.hruday.ProRank.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper returnModelMapper(){
        return new ModelMapper();
    }

//    @Bean
//    public re
}
