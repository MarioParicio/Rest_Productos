package org.iesch.ad.demo.Rest_Productos.dto;

import lombok.Data;
import org.iesch.ad.demo.Rest_Productos.modelos.Producto;

import java.util.List;

@Data
public class CategoriaDTO {
    private Long id;
    private String nombre;
    private List<Producto> productos;
}