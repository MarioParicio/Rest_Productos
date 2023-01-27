package org.iesch.ad.demo.Rest_Productos.controladores;


import org.iesch.ad.demo.Rest_Productos.modelos.Categoria;
import org.iesch.ad.demo.Rest_Productos.repositorio.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria/")
public class CategoriaControlador {
    @Autowired
    CategoriaRepositorio categoriaRepositorio;

    @GetMapping("all")
    public List<Categoria> obtenerTodos(){
        return categoriaRepositorio.findAll();
    }
    @GetMapping("obternerCategoria")
    public ResponseEntity<?> obtenerCategoria(@Param("id") Long id ){
        Categoria result = categoriaRepositorio.findById(id).orElse(null);
        if (result == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("obternerTodosCategorias")
    public ResponseEntity<?> obtenerTodosCategorias(@Param("id") Long id ){
        List<Categoria> result = categoriaRepositorio.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }


    }


    @PostMapping("añadirCategoria")
    public ResponseEntity<?> insertarCategoria(@RequestBody Categoria Categoria){

        Categoria salvdado = categoriaRepositorio.save(Categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(Categoria);

    }
    //Nuevo inserta

    @DeleteMapping("borrarCategoria")
    public ResponseEntity<?> deletearCategoria(@RequestParam("id") Long  idCategoria){
        categoriaRepositorio.deleteById(idCategoria);
        return ResponseEntity.noContent().build();

    }
    
    @PutMapping("Categoria/{id}")
    public ResponseEntity<?> editarCategoria(@RequestBody Categoria editar, @PathVariable Long id){
        return categoriaRepositorio.findById(id).map(Categoria -> {
            Categoria.setNombre(editar.getNombre());

            return ResponseEntity.ok(categoriaRepositorio.save(Categoria));
        }).orElseGet(() -> {
            return ResponseEntity.badRequest().build();
        });

    }




}
