package org.iesch.ad.demo.Rest_Productos.controladores;


import org.iesch.ad.demo.Rest_Productos.modelos.Producto;
import org.iesch.ad.demo.Rest_Productos.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto/")
public class ProductoControlador {
    @Autowired
    ProductoRepositorio productoRepositorio;

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

    @PutMapping("producto/{id}")
    public ResponseEntity<?> editarProducto(@RequestBody Producto editar, @PathVariable Long id){
        return productoRepositorio.findById(id).map(producto -> {
            producto.setProduct_name(editar.getProduct_name());
            producto.setPrice(editar.getPrice());
            return ResponseEntity.ok(productoRepositorio.save(producto));
        }).orElseGet(() -> {
            return ResponseEntity.badRequest().build();
        });

    }




}
