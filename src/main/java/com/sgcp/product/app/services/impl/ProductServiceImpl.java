package com.sgcp.product.app.services.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgcp.product.app.dao.IProductDao;
import com.sgcp.product.app.dto.ProductDTO;
import com.sgcp.product.app.services.IProductService;

@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    private IProductDao productDao;

    @Override
    public List<ProductDTO> findAll() {
//         if ( criterio != null ) {
//             return productDao.findAll(criterio);
//         }
        return (List<ProductDTO>) productDao.findAll();
    }

    @Override
    public List<ProductDTO> findByNameOrPrice(String criterio) {
        return productDao.findByNameOrPrice(criterio);
    }
    @Override
    public ProductDTO findById(Integer id) {
        return productDao.findById(id).get();
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        return productDao.save(productDTO);
    }

    @Override
    public void delete(Integer id) {
        productDao.deleteById(id);
        
    }

//    @Override
//    public List<ProductDTO> findByName(String criterio) {
//        return productDao.findByName(criterio);
//    }

    // @Override
    // public List<ProductDTO> findByName(String criterio) {
    //     List<ProductDTO> products = productDao.findByName(criterio);
    //     return products;
        
    //     // SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    //     // Session session = sessionFactory.openSession();
    //     // session.beginTransaction();
    //     // // String hql = "SELECT p FROM products p WHERE p.nombre LIKE %?1%".concat("%?").concat(criterio) + ";
    //     // Query<?> query = session.createQuery(hql);
    //     // List<ProductDTO> products = (List<ProductDTO>) query.list();
    //     // session.getTransaction().commit();
    //     // session.close();
    //     // Connection conexion = DriverManager.getConnection(url)
    //     // Statement instruccion = 
    //     // String sql = "SELECT p FROM products p WHERE p.nombre LIKE '%" + criterio + "%'";
    //     // Query query = em.createQuery(sql);
    //     // return query.getResultList();
    // }
    
}
