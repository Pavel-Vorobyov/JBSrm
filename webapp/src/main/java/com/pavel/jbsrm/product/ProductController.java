package com.pavel.jbsrm.product;

import com.pavel.jbsrm.product.dto.CreateProductDto;
import com.pavel.jbsrm.product.dto.ProductDto;
import com.pavel.jbsrm.product.dto.UpdateProductDto;
import com.pavel.jbsrm.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable long id) {
        return productService.find(id);
    }

    @GetMapping("/quickSearch/{searchParams}")
    public List<ProductDto> findAllByPropMatch(@PathVariable String searchParams) {
        return productService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    public Page<ProductDto> findAll(@Nullable @RequestParam Boolean deleted, Pageable pageable) {
        deleted = deleted == null ? false : deleted;
        return productService.findAllPageByDeleted(deleted, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateProductDto updateDto) {
        productService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ProductDto create(@RequestBody CreateProductDto createDto) {
        return productService.create(createDto);
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<String> updateDeleted(@PathVariable long id, Boolean deleted) {
        productService.updateDeleted(id, deleted);
        return ResponseEntity.ok().build();
    }
}
