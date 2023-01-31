package org.iesch.ad.demo.Rest_Productos.controladores;


import org.iesch.ad.demo.Rest_Productos.dto.CreateProductoDTO;
import org.iesch.ad.demo.Rest_Productos.dto.ProductoDTO;
import org.iesch.ad.demo.Rest_Productos.dto.converter.ProductoDTOConverter;
import org.iesch.ad.demo.Rest_Productos.modelos.Producto;
import org.iesch.ad.demo.Rest_Productos.repositorio.CategoriaRepositorio;
import org.iesch.ad.demo.Rest_Productos.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/producto/")
public class ProductoControlador {
    @Autowired
    ProductoRepositorio productoRepositorio;
    @Autowired
    ProductoDTOConverter ProductoDTOConverter;
    @Autowired
    CategoriaRepositorio categoriaRepositorio;

    @GetMapping("all")
    public List<Producto> obtenerTodos(){
        return productoRepositorio.findAll();
    }
    @GetMapping("obternerProducto")
    public ResponseEntity<?> obtenerProducto(@Param("id") Long id ){
        Producto result = productoRepositorio.findById(id).orElse(null);
        if (result == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("obternerTodosProductos")
    public ResponseEntity<?> obtenerTodosProductos(@Param("id") Long id ){
        List< Producto> result = productoRepositorio.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }


    }


    @PostMapping("añadirProducto")
    public ResponseEntity<?> insertarProducto(@RequestBody Producto producto){

        Producto salvdado = productoRepositorio.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);

    }
    //Nuevo inserta

    @DeleteMapping("borrarProducto")
    public ResponseEntity<?> deletearProducto(@RequestParam("id") Long  idProduct){
        productoRepositorio.deleteById(idProduct);
        return ResponseEntity.noContent().build();

    }

//    @PutMapping("producto/{id}")
//    public ResponseEntity<?> editarProducto(@RequestBody Producto editar, @PathVariable Long id){
//        return productoRepositorio.findById(id).map(producto -> {
//            producto.setProduct_name(editar.getProduct_name());
//            producto.setPrice(editar.getPrice());
//            return ResponseEntity.ok(productoRepositorio.save(producto));
//        }).orElseGet(() -> {
//            return ResponseEntity.badRequest().build();
//        });
//
//    }

    @GetMapping("productoDTO")
    public ResponseEntity<?> obtenerTodosATravesDeDTO(){
        List<Producto> result = productoRepositorio.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            //Return con dto
            return ResponseEntity.ok(result.stream().map(ProductoDTOConverter::converToDTO).collect(Collectors.toList()));
/*            //Con un foreach
            List<ProductoDTO> resultDTO = new ArrayList<>();
            result.forEach(producto -> {
                ProductoDTO productoDTO = ProductoDTOConverter.converToDTO(producto);
                resultDTO.add(productoDTO);
            });
            return ResponseEntity.ok(resultDTO);*/
        }

    }
    @PostMapping("productoDTO")
    public ResponseEntity<?> insertarProductoDTO(@RequestBody CreateProductoDTO createProductoDTO){

/*        Producto producto = new Producto();
        producto.setNombre(createProductoDTO.getNombre());
        producto.setPrecio(createProductoDTO.getPrecio());
        producto.setCategoria(categoriaRepositorio.findById(createProductoDTO.getCategoriaId()).orElse(null));
        return ResponseEntity.status(HttpStatus.CREATED).body(productoRepositorio.save(producto));*/

        Producto producto = ProductoDTOConverter.convertDesdeCreateProductoDTO(createProductoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoRepositorio.save(ProductoDTOConverter.convertDesdeCreateProductoDTO(createProductoDTO)));



    }





}
