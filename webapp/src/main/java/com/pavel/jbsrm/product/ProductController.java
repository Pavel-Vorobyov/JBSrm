package com.pavel.jbsrm.product;

import com.pavel.jbsrm.product.product.ProductFilter;
import com.pavel.jbsrm.product.product.dto.CreateProductDto;
import com.pavel.jbsrm.product.product.dto.ProductDto;
import com.pavel.jbsrm.product.product.dto.ProductQuickSearchDto;
import com.pavel.jbsrm.product.product.dto.UpdateProductDto;
import com.pavel.jbsrm.product.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DISPATCHER') or hasRole('MANAGER')")
    public Optional<ProductDto> getById(@PathVariable long id) {
        return productService.find(id);
    }

    @GetMapping("/quickSearch/{searchParams}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DISPATCHER') or hasRole('MANAGER')")
    public List<ProductQuickSearchDto> findAllByPropMatch(@PathVariable String searchParams) {
        return productService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ProductDto> findAll(ProductFilter filter, Pageable pageable) {
        return productService.findAllPageByFilter(filter, pageable);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateProductDto updateDto) {
        productService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<ProductDto> create(@RequestBody CreateProductDto createDto) {
        return productService.create(createDto);
    }

    @PutMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateDeleted(@PathVariable long id, Boolean deleted) {
        productService.updateDeleted(id, deleted);
        return ResponseEntity.ok().build();
    }
}
