package com.pavel.jbsrm.product;

import com.pavel.jbsrm.product.product.ProductFilter;
import com.pavel.jbsrm.product.product.dto.CreateProductDto;
import com.pavel.jbsrm.product.product.dto.ProductDto;
import com.pavel.jbsrm.product.product.dto.ProductQuickSearchDto;
import com.pavel.jbsrm.product.product.dto.UpdateProductDto;
import com.pavel.jbsrm.product.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public Optional<ProductDto> getById(@PathVariable long id) {
        return productService.find(id);
    }

    @GetMapping("/quickSearch/{searchParams}")
    public List<ProductQuickSearchDto> findAllByPropMatch(@PathVariable String searchParams) {
        return productService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    public Page<ProductDto> findAll(ProductFilter filter, Pageable pageable) {
        return productService.findAllPageByDeleted(filter, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateProductDto updateDto) {
        productService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public Optional<ProductDto> create(@RequestBody CreateProductDto createDto) {
        return productService.create(createDto);
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<String> updateDeleted(@PathVariable long id, Boolean deleted) {
        productService.updateDeleted(id, deleted);
        return ResponseEntity.ok().build();
    }
}
