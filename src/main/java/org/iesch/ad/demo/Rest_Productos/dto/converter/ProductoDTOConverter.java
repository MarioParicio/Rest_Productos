package org.iesch.ad.demo.Rest_Productos.dto.converter;

import lombok.RequiredArgsConstructor;
import org.iesch.ad.demo.Rest_Productos.dto.CreateProductoDTO;
import org.iesch.ad.demo.Rest_Productos.dto.ProductoDTO;
import org.iesch.ad.demo.Rest_Productos.modelos.Producto;
import org.iesch.ad.demo.Rest_Productos.repositorio.CategoriaRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor //tiene que ser final
public class ProductoDTOConverter {

    @Autowired
    CategoriaRepositorio categoriaRepositorio;

    private final ModelMapper modelMapper;

    public ProductoDTO converToDTO(Producto producto){
        return modelMapper.map(producto, ProductoDTO.class);
    }
    public Producto convertDesdeCreateProductoDTO(CreateProductoDTO createProductoDTO){
        Producto producto = modelMapper.map(createProductoDTO, Producto.class);
        producto.setCategoria(categoriaRepositorio.findById(producto.getCategoria().getCodigo()).orElse(null));
        return producto;


    }
}
