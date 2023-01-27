package org.iesch.ad.demo.Rest_Productos.controladores;


import org.iesch.ad.demo.Rest_Productos.modelos.Producto;
import org.iesch.ad.demo.Rest_Productos.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class ProductoControlador {
    @Autowired
    ProductoRepositorio repositorio;

    @GetMapping("all")
    public List<Producto> obtenerTodos(){
        return repositorio.findAll();
    }
    @GetMapping("obternerProducto")
    public ResponseEntity<?> obtenerProducto(@Param("id") Long id ){
        Producto result = repositorio.findById(id).orElse(null);
        if (result == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("obternerTodosProductos")
    public ResponseEntity<?> obtenerTodosProductos(@Param("id") Long id ){
        List< Producto> result = repositorio.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }


    }


    @PostMapping("añadirProducto")
    public ResponseEntity<?> insertarProducto(@RequestBody Producto producto){

        Producto salvdado = repositorio.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);

    }
    //Nuevo inserta

    @DeleteMapping("borrarProducto")
    public ResponseEntity<?> deletearProducto(@RequestParam("id") Long  idProduct){
        repositorio.findById(idProduct).map( e ->{
                repositorio.delete(e);
                return e;
        }).ifPresent(ResponseEntity::ok);
        return ResponseEntity.badRequest().build();

    }
    @PutMapping("producto/{id}")
    public ResponseEntity<?> editarProducto(@RequestBody Producto editar, @PathVariable Long id){
        return repositorio.findById(id).map(producto -> {
            producto.setProduct_name(editar.getProduct_name());
            producto.setPrice(editar.getPrice());
            return ResponseEntity.ok(repositorio.save(producto));
        }).orElseGet(() -> {
            return ResponseEntity.badRequest().build();
        });
        
    }




}
