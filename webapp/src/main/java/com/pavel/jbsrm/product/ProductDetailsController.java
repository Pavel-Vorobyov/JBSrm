package com.pavel.jbsrm.product;

import com.pavel.jbsrm.product.details.ProductDetailsFilter;
import com.pavel.jbsrm.product.details.dto.CreateProductDetailsDto;
import com.pavel.jbsrm.product.details.dto.ProductDetailsDto;
import com.pavel.jbsrm.product.details.dto.UpdateProductDetailsDto;
import com.pavel.jbsrm.product.details.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product-details")
public class ProductDetailsController {

    @Autowired
    private ProductDetailsService productDetailsService;

    @GetMapping("/{id}")
    public Optional<ProductDetailsDto> getById(@PathVariable long id) {
        return productDetailsService.find(id);
    }

    @GetMapping("/quickSearch/{searchParams}")
    public List<ProductDetailsDto> findAllByPropMatch(@PathVariable String searchParams) {
        return productDetailsService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    public Page<ProductDetailsDto> findAll(ProductDetailsFilter filter, Pageable pageable) {
        return productDetailsService.findAllPageByDeleted(filter, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateProductDetailsDto updateDto) {
        productDetailsService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public Optional<ProductDetailsDto> create(@RequestBody CreateProductDetailsDto createDto) {
        return productDetailsService.create(createDto);
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<String> updateDeleted(@PathVariable long id, Boolean deleted) {
        productDetailsService.updateDeleted(id, deleted);
        return ResponseEntity.ok().build();
    }
}
