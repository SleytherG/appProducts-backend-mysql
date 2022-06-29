package com.sgcp.product.app.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgcp.product.app.dto.ProductDTO;
import com.sgcp.product.app.models.Product;
import com.sgcp.product.app.services.impl.ProductServiceImpl;

@RestController
@RequestMapping(value = "product")
public class ProductoController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping(value = "/listarProductos")
    public List<ProductDTO> listProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductDTO buscarPorId(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @PostMapping(value = "/buscarProducto")
    public List<ProductDTO> buscarProducto(@RequestBody Product product) {
        return productService.findByNameOrPrice(product.getCriterio());
    }

    @PostMapping(value = "/crearProducto")
    public ResponseEntity<?> crearProducto(@RequestBody ProductDTO productDTO) {
        ProductDTO newProduct = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            newProduct = productService.save(productDTO);
            response.put("ok", true);
            response.put("mensaje", "El producto ha sido creado con exito!");
            response.put("newProduct", newProduct);
        } catch (DataAccessException e) {
            response.put("ok", false);
            response.put("mensaje", "Error al realizar el INSERT en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> actualizarProducto(@RequestBody ProductDTO productDTO, @PathVariable Integer id) {
        ProductDTO currentProduct = productService.findById(id);
        ProductDTO updatedProduct = null;
        Map<String, Object> response = new HashMap<String, Object>();

        if ( currentProduct == null) {
            response.put("ok", false);
            response.put("mensaje", "Error: No se pudo editar, El cliente ID: "
                    .concat(id.toString().concat(" no existe en la base de datos.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            currentProduct.setNombre(productDTO.getNombre());
            currentProduct.setPrecio(productDTO.getPrecio());
            if ( productDTO.getCreateAt() == null ) {
                currentProduct.setCreateAt(new Date());
            } else {
                currentProduct.setCreateAt(productDTO.getCreateAt());
            }

            updatedProduct = productService.save(currentProduct);
            response.put("ok", true);
            response.put("mensaje", "El producto ha sido actualizado exitosamente!");
            response.put("updatedProduct", updatedProduct);
        } catch (DataAccessException e) {
            response.put("ok", false);
            response.put("mensaje", "Error al actualizar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {

        Map<String, Object> response = new HashMap<String, Object>();
        try {
            productService.delete(id);
            response.put("ok", true);
            response.put("mensaje", "El producto ha sido eliminado exitosamente.");
        } catch (DataAccessException e) {
            response.put("ok", false);
            response.put("mensaje", "Error al eliminar al producto de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    
}
