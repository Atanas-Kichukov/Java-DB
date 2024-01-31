package org.example.config;

import org.example.model.dto.GameAddDto;
import org.example.model.entity.Game;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(GameAddDto.class, Game.class)
                .addMappings(mapper ->
                        mapper.map(GameAddDto::getThumbnailURL,
                                Game::setImageThumbnail));

        return modelMapper;
    }

}
