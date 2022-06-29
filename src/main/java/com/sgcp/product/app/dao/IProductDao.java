package com.sgcp.product.app.dao;

import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepository;

import com.sgcp.product.app.dto.ProductDTO;

public interface IProductDao extends CrudRepository<ProductDTO, Integer> {

    @Query("SELECT p FROM Products p WHERE p.nombre LIKE %?1%"
            + " OR p.precio LIKE %?1%")
    public List<ProductDTO> findByNameOrPrice(String criterio);

    
}
