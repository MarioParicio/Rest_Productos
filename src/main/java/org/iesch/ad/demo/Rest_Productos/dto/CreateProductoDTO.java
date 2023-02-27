package org.iesch.ad.demo.Rest_Productos.dto;

import lombok.Data;

@Data
public class CreateProductoDTO {

    private String nombre;
    private double precio;
    private Long categoriaCodigo;
}
