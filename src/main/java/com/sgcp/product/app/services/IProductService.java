package com.sgcp.product.app.services;

import java.util.List;

import com.sgcp.product.app.dto.ProductDTO;


public interface IProductService {
    
    public List<ProductDTO> findAll();
    
    public ProductDTO findById(Integer id);

    public ProductDTO save(ProductDTO productDTO);

    public void delete(Integer id);


    public List<ProductDTO> findByNameOrPrice(String criterio);

}
