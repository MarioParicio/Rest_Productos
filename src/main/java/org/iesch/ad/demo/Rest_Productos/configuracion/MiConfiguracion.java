package org.iesch.ad.demo.Rest_Productos.configuracion;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class MiConfiguracion {


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
