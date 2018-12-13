package com.pavel.jbsrm.product.service;

import com.pavel.jbsrm.client.Client;
import com.pavel.jbsrm.client.dto.ClientDto;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.product.Product;
import com.pavel.jbsrm.product.dto.CreateProductDto;
import com.pavel.jbsrm.product.dto.ProductDto;
import com.pavel.jbsrm.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public ProductDto createProduct(@Valid CreateProductDto createProductDto) {
        Product product = ObjectMapperUtills.mapTo(createProductDto, Product.class);
        product.setDeleted(true);
        return ObjectMapperUtills.mapTo(productRepository.save(product), ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(long id, @Valid CreateProductDto createProductDto) {
        return null;
    }

    @Override
    public void deleteProduct(long id) {

    }

    @Override
    public void restoreProduct(long id) {

    }

    @Override
    public ProductDto find(long id) {
        return null;
    }

    @Override
    public List<ProductDto> findAllClients() {
        return null;
    }

    @Override
    public List<ProductDto> findAllByPropsMatch(String searchParams) {
        return null;
    }

    @Override
    public Page<ProductDto> findAllPageByActive(boolean isActive, Pageable pageable) {
        return null;
    }
}
