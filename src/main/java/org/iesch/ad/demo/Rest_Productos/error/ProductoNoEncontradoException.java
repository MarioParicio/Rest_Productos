package org.iesch.ad.demo.Rest_Productos.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductoNoEncontradoException extends RuntimeException {
    private static final long serialVersionUID = 12342L;

    public ProductoNoEncontradoException(Long id) {
        super("No se ha encontrado el producto con id: " + id);
    }
}
