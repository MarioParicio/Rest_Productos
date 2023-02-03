package org.iesch.ad.demo.Rest_Productos.controladores;


import org.iesch.ad.demo.Rest_Productos.dto.CategoriaDTO;
import org.iesch.ad.demo.Rest_Productos.dto.converter.CategoriaDTOConverter;
import org.iesch.ad.demo.Rest_Productos.error.ProductoNoEncontradoException;
import org.iesch.ad.demo.Rest_Productos.modelos.Categoria;
import org.iesch.ad.demo.Rest_Productos.repositorio.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;




@RestController
@RequestMapping("/api/categoria/")
public class CategoriaControlador {
    @Autowired
    CategoriaRepositorio categoriaRepositorio;

    @Autowired
    CategoriaDTOConverter categoriaDTOConverter;

    @GetMapping("all")
    public List<Categoria> obtenerTodos() {
        return categoriaRepositorio.findAll();
    }

    @GetMapping("obternerCategoria/{id}")
    public ResponseEntity<?> obtenerCategoria(@PathVariable Long id) {

        return categoriaRepositorio.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());


//        return categoriaRepositorio.findById(id).isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoriaRepositorio.findById(id));

    }

    @GetMapping("obternerTodosCategorias")
    public ResponseEntity<?> obtenerTodosCategorias(@Param("id") Long id) {
/*        List<Categoria> result = categoriaRepositorio.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }*/
        //Manera más corta
        return categoriaRepositorio.findAll().isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoriaRepositorio.findAll());




        //Devulve todas las categorias con una funcion lambda
/*        List<Categoria> result = categoriaRepositorio.findAll();
        return result.isEmpty() ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(result);*/

    }

    @GetMapping("obternerCategoriasConProductos")
    public ResponseEntity<?> obtenerCategoriasConProductos() {

        return categoriaRepositorio.findAll().isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoriaRepositorio.findAll().stream().map(categoriaDTOConverter::convertToDTO));

    }


    @PostMapping("añadirCategoria")
    public ResponseEntity<?> insertarCategoria(@RequestBody Categoria Categoria) {

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepositorio.save(Categoria));

    }

    @PostMapping("añadirCategorias")
    public ResponseEntity<?> insertarCategorias(@RequestBody List<Categoria> Categorias) {

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepositorio.saveAll(Categorias));

    }

    @DeleteMapping("borrarCategoria")
    public ResponseEntity<?> deletearCategoria(@RequestParam("id") Long idCategoria) {
        categoriaRepositorio.deleteById(idCategoria);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("Categoria/{id}")
    public ResponseEntity<?> editarCategoria(@RequestBody Categoria editar, @PathVariable Long id) {
        return categoriaRepositorio.findById(id).map(Categoria -> {
            Categoria.setNombre(editar.getNombre());

            return ResponseEntity.ok(categoriaRepositorio.save(Categoria));
        }).orElseGet(() -> ResponseEntity.badRequest().build());


    }
}