package org.iesch.ad.demo.Rest_Productos.controladores;


import org.iesch.ad.demo.Rest_Productos.modelos.Producto;
import org.iesch.ad.demo.Rest_Productos.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Producto obtenerProducto(@Param("id") String id ){
        return repositorio.findById(id).orElse(null);
    }


    @PostMapping("añadirProducto")
    public Producto insertarProducto(@RequestBody Producto producto){
        return repositorio.save(producto);
    }
    @DeleteMapping("borrarProducto")
    public void deletearProducto(@RequestParam("id") String  idProduct){

        repositorio.findById(idProduct).ifPresent(repositorio::delete);
    }
    @PutMapping("api/producto/{id}")
    public Producto editarProducto(@RequestBody Producto editar, @PathVariable Long id){

        repositorio.findById(id).ifPresentOrElse()


    }




}
