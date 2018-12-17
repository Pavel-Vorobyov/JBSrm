package com.pavel.jbsrm.product.service;

import com.pavel.jbsrm.common.exception.ResourceNotFoundException;
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
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public ProductDto create(@Valid CreateProductDto createProductDto) {
        Product product = ObjectMapperUtills.mapTo(createProductDto, Product.class);
        product.setProductState(ProductState.ACCEPTED);
        return ObjectMapperUtills.mapTo(productRepository.save(product), ProductDto.class);
    }

    @Override
    public ProductDto update(long id, @Valid UpdateProductDto updateProductDto) {
        Optional<Product> product = Optional.of(productRepository.getOne(id));
        product.ifPresent(pr -> ObjectMapperUtills.mapTo(updateProductDto, pr));

        return ObjectMapperUtills.mapTo(
                product.map(pr -> productRepository.save(pr)).orElseThrow(ResourceNotFoundException::new), ProductDto.class);
    }

    @Override
    public void updateDeleted(long id, boolean deleted) {
        Optional<Product> product = productRepository.findById(id);
        product.orElseThrow(ResourceNotFoundException::new)
                .setDeleted(deleted);
        productRepository.save(product.get());
    }

    @Override
    public ProductDto find(long id) {
        return ObjectMapperUtills.mapTo(productRepository.findById(id), ProductDto.class);
    }

    @Override
    public List<ProductDto> findAllByPropsMatch(String searchParams) {
        List<ProductDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.contains(""))
                    .collect(Collectors.toList());

            productRepository.findAllByPropsMatch(list)
                    .forEach(c -> result.add(ObjectMapperUtills.mapTo(c, ProductDto.class)));
        }
        return result;
    }

    @Override
    public Page<ProductDto> findAllPageByDeleted(boolean deleted, Pageable pageable) {
        return productRepository.findByDeleted(deleted, pageable)
                .map(product -> ObjectMapperUtills.mapTo(product, ProductDto.class));
    }
}
