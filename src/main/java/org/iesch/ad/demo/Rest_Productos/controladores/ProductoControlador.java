package org.iesch.ad.demo.Rest_Productos.controladores;


import org.iesch.ad.demo.Rest_Productos.dto.CreateProductoDTO;
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
import java.util.Optional;
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

        //Manera más corta
//        return productoRepositorio.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
    @GetMapping("obternerTodosProductos")
    public ResponseEntity<?> obtenerTodosProductos(){
/*        List< Producto> result = productoRepositorio.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }*/

        //Manera más corta
        return productoRepositorio.findAll().isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(productoRepositorio.findAll());




    }


    @PostMapping("añadirProducto")
    public ResponseEntity<?> insertarProducto(@RequestBody Producto producto){


        return ResponseEntity.status(HttpStatus.CREATED).body(productoRepositorio.save(producto));

    }
    @PostMapping("añadirProductos")
    public ResponseEntity<?> insertarProductos(@RequestBody List<Producto> productos){

        return ResponseEntity.status(HttpStatus.CREATED).body(productoRepositorio.saveAll(productos));
    }

    @DeleteMapping("borrarProducto")
    public ResponseEntity<?> deletearProducto(@RequestParam("id") Long  idProduct){
        productoRepositorio.deleteById(idProduct);
        return ResponseEntity.noContent().build();



    }

    @PutMapping("producto/{id}")
    public ResponseEntity<?> editarProducto(@RequestBody Producto editar, @PathVariable Long id){


        //Manera más corta
        return Optional.of(editar).map(producto -> {
            producto.setId(id);
            return ResponseEntity.ok(productoRepositorio.save(producto));
        }).orElseGet(() -> ResponseEntity.badRequest().build());

    }

    @GetMapping("productoDTO")
    public ResponseEntity<?> obtenerTodosATravesDeDTO(){
/*        List<Producto> result = productoRepositorio.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
//            No hace falta el collect la función map ya devuelve un stream
         return ResponseEntity.ok(result.stream().map(ProductoDTOConverter::converToDTO).collect(Collectors.toList()));*/

            //Manera más corta
            return productoRepositorio.findAll().isEmpty() ?  ResponseEntity.notFound().build() :
                    ResponseEntity.ok(productoRepositorio.findAll().stream().map(ProductoDTOConverter::converToDTO));



    }
    @PostMapping("productoDTO")
    public ResponseEntity<?> insertarProductoDTO(@RequestBody CreateProductoDTO createProductoDTO){

/*        Producto producto = new Producto();
        producto.setNombre(createProductoDTO.getNombre());
        producto.setPrecio(createProductoDTO.getPrecio());
        producto.setCategoria(categoriaRepositorio.findById(createProductoDTO.getCategoriaId()).orElse(null));
        return ResponseEntity.status(HttpStatus.CREATED).body(productoRepositorio.save(producto));*/


        return ResponseEntity.status(HttpStatus.CREATED).body(productoRepositorio.save(ProductoDTOConverter.convertDesdeCreateProductoDTO(createProductoDTO)));



    }

    @PostMapping("productosDTO")
    public ResponseEntity<?> insertarProductosDTO(@RequestBody List<CreateProductoDTO> createProductoDTO){

        List<Producto> productos = createProductoDTO.stream().map(ProductoDTOConverter::convertDesdeCreateProductoDTO).toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(productoRepositorio.saveAll(productos));

    }





}
