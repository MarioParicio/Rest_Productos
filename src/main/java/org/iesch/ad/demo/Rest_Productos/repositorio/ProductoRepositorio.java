package org.iesch.ad.demo.Rest_Productos.repositorio;

import org.iesch.ad.demo.Rest_Productos.modelos.Producto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository<Producto, String> {


}