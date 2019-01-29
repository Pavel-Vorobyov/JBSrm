package com.pavel.jbsrm.product.product.service;

import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.product.details.repository.ProductDetailsRepository;
import com.pavel.jbsrm.product.product.Product;
import com.pavel.jbsrm.product.product.ProductFilter;
import com.pavel.jbsrm.product.product.ProductState;
import com.pavel.jbsrm.product.product.QProduct;
import com.pavel.jbsrm.product.product.dto.CreateProductDto;
import com.pavel.jbsrm.product.product.dto.ProductDto;
import com.pavel.jbsrm.product.product.dto.ProductQuickSearchDto;
import com.pavel.jbsrm.product.product.dto.UpdateProductDto;
import com.pavel.jbsrm.product.product.repository.ProductRepository;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductDetailsRepository productDetailsRepository) {
        this.productRepository = productRepository;
        this.productDetailsRepository = productDetailsRepository;
    }

    @Override
    @Transactional
    public ProductDto create(@Valid CreateProductDto createProductDto) {
        Product product = ObjectMapperUtills.mapTo(createProductDto, Product.class);
        product.setProductDetails(productDetailsRepository.getOne(createProductDto.getProductDetailsId()));

        if (createProductDto.getProductState() != null) {
            product.setProductState(createProductDto.getProductState());
        } else {
            product.setProductState(ProductState.ACCEPTED);
        }

        return ObjectMapperUtills.mapTo(product, ProductDto.class);
    }

    @Override
    @Transactional
    public Optional<ProductDto> update(long id, @Valid UpdateProductDto updateProductDto) {
        Product product = productRepository.getOne(id);
        ObjectMapperUtills.mapTo(updateProductDto, product);
        product.setProductDetails(productDetailsRepository.getOne(updateProductDto.getProductDetailsId()));

        return Optional.of(ObjectMapperUtills.mapTo(product, ProductDto.class));
    }

    @Override
    @Transactional
    public void updateDeleted(long id, boolean deleted) {
        Product product = productRepository.getOne(id);
        product.setDeleted(deleted);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDto> find(long id) {
        return productRepository.findById(id)
                .map(product -> ObjectMapperUtills.mapTo(product, ProductDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductQuickSearchDto> findAllByPropsMatch(String searchParams) {
        List<ProductQuickSearchDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.equals(""))
                    .collect(Collectors.toList());

            result = productRepository.findAllByPropsMatch(list);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> findAllPageByFilter(ProductFilter filter, Pageable pageable) {
        return productRepository.findAll(buildFilter(filter), pageable)
                .map(product -> ObjectMapperUtills.mapTo(product, ProductDto.class));
    }

    private BooleanBuilder buildFilter(ProductFilter filter) {
        BooleanBuilder whereBuilder = new BooleanBuilder();

        if (filter != null) {
            if (filter.getDeleted() != null) {
                whereBuilder.and(QProduct.product.deleted.eq(filter.getDeleted()));
            }
        }
        return whereBuilder;
    }
}
