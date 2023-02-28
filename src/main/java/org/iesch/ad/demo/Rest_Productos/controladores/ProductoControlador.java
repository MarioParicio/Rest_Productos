package org.iesch.ad.demo.Rest_Productos.controladores;


import org.iesch.ad.demo.Rest_Productos.dto.CreateProductoDTO;
import org.iesch.ad.demo.Rest_Productos.dto.converter.ProductoDTOConverter;
import org.iesch.ad.demo.Rest_Productos.modelos.Producto;
import org.iesch.ad.demo.Rest_Productos.repositorio.CategoriaRepositorio;
import org.iesch.ad.demo.Rest_Productos.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/producto/")
public class ProductoControlador {


    @Autowired
    ProductoRepositorio productoRepositorio;
    @Autowired
    ProductoDTOConverter productoDTOConverter;
    @Autowired
    CategoriaRepositorio categoriaRepositorio;

    @GetMapping("all")
    public List<Producto> obtenerTodos(){
        return productoRepositorio.findAll();
    }

    @GetMapping("obternerProducto/{id}")
    public ResponseEntity<?> obtenerProducto(@PathVariable Long id) {

        return productoRepositorio.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());



    }
    @GetMapping("obternerTodosProductos")
    public ResponseEntity<?> obtenerTodosProductos(){

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


        return productoRepositorio.findById(idProduct).map(producto -> {
            productoRepositorio.delete(producto);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());


    }

    @PutMapping("producto/{id}")
    public ResponseEntity<?> editarProducto(@RequestBody Producto editar, @PathVariable Long id){


        //Manera más corta
        return Optional.of(editar).map(producto -> {
            producto.setId(id);
            return ResponseEntity.ok(productoRepositorio.save(producto));
        }).orElseGet(() -> ResponseEntity.badRequest().build());

    }

    @CrossOrigin(origins = "http://localhost:9090")
    @GetMapping("productoDTO")
    public ResponseEntity<?> obtenerTodosATravesDeDTO(){


            //Manera más corta
            return productoRepositorio.findAll().isEmpty() ?  ResponseEntity.notFound().build() :
                    ResponseEntity.ok(productoRepositorio.findAll().stream().map(productoDTOConverter::converToDTO));



    }
    @PostMapping("productoDTO")
    public ResponseEntity<?> insertarProductoDTO(@RequestBody CreateProductoDTO createProductoDTO){




        return ResponseEntity.status(HttpStatus.CREATED).body(productoRepositorio.save(productoDTOConverter.convertDesdeCreateProductoDTO(createProductoDTO)));



    }


    @PostMapping("productosDTO")
    public ResponseEntity<?> insertarProductosDTO(@RequestBody List<CreateProductoDTO> createProductoDTO){


        return ResponseEntity.status(HttpStatus.CREATED).body(productoRepositorio.saveAll(createProductoDTO.stream().map
                (productoDTOConverter::convertDesdeCreateProductoDTO).toList()));

    }





}
