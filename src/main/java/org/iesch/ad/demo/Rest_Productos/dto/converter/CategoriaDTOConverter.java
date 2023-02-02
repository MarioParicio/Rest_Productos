package org.iesch.ad.demo.Rest_Productos.dto.converter;


import lombok.RequiredArgsConstructor;
import org.iesch.ad.demo.Rest_Productos.dto.CategoriaDTO;
import org.iesch.ad.demo.Rest_Productos.dto.ProductoDTO;
import org.iesch.ad.demo.Rest_Productos.modelos.Categoria;
import org.iesch.ad.demo.Rest_Productos.modelos.Producto;
import org.iesch.ad.demo.Rest_Productos.repositorio.ProductoRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoriaDTOConverter
{
    private final ModelMapper modelMapper;
    private final ProductoRepositorio productoRepositorio;

    public CategoriaDTO convertToDTO(Categoria categoria)
    {
        CategoriaDTO categoriaDTO = modelMapper.map(categoria, CategoriaDTO.class);
        List<Producto> productos = new ArrayList<>();
        productoRepositorio.findAll().forEach(producto -> {
            if(producto.getCategoria().getId() == categoria.getId()){
                productos.add(producto);
            }
        });
        categoriaDTO.setProductos(productos);
        return categoriaDTO;
    }

}
