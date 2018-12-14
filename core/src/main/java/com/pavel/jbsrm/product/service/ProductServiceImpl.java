package com.pavel.jbsrm.product.service;

import com.pavel.jbsrm.client.Client;
import com.pavel.jbsrm.client.dto.ClientDto;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.product.Product;
import com.pavel.jbsrm.product.ProductState;
import com.pavel.jbsrm.product.dto.CreateProductDto;
import com.pavel.jbsrm.product.dto.ProductDto;
import com.pavel.jbsrm.product.dto.UpdateProductDto;
import com.pavel.jbsrm.product.repository.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public ProductDto createProduct(@Valid CreateProductDto createProductDto) {
        Product product = ObjectMapperUtills.mapTo(createProductDto, Product.class);
        product.setProductState(ProductState.ACCEPTED);
        return ObjectMapperUtills.mapTo(productRepository.save(product), ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(long id, @Valid UpdateProductDto updateProductDto) {
        Product product = ObjectMapperUtills.mapTo(updateProductDto, Product.class);
        return ObjectMapperUtills.mapTo(productRepository.save(product), ProductDto.class);
    }

    @Override
    public void deleteProduct(long id) {
        updateDeletedStatus(id, true);
    }

    @Override
    public void restoreProduct(long id) {
        updateDeletedStatus(id, false);
    }

    private void updateDeletedStatus(long id, boolean isDeleted) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(p -> {
            p.setDeleted(isDeleted);
            productRepository.save(product.get());
        });
    }

    @Override
    public ProductDto find(long id) {
        return ObjectMapperUtills.mapTo(productRepository.findById(id), ProductDto.class);
    }

    @Override
    public List<ProductDto> findAllByPropsMatch(String searchParams) {
        List<ProductDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            String[] parsedSearchParams = searchParams.trim().split(" ");
            List<String> list = new ArrayList<>(Arrays.asList(parsedSearchParams));
            list.removeAll(Arrays.asList("", null));

            productRepository.findAllByPropsMatch(list)
                    .forEach(c -> result.add(ObjectMapperUtills.mapTo(c, ProductDto.class)));
        }
        return result;
    }

    @Override
    public Page<ProductDto> findAllPageByDeleted(boolean isDeleted, Pageable pageable) {
        return productRepository.findByIsDeleted(isDeleted, pageable)
                .map(product -> ObjectMapperUtills.mapTo(product, ProductDto.class));
    }
}
