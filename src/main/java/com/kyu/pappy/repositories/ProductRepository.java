package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional <Product> findByProductName(String ProductName);
}