package com.pavel.jbsrm.product;

import com.pavel.jbsrm.product.dto.ProductDto;
import com.pavel.jbsrm.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductControlle {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ProductDto getproductById(@PathVariable long id) {
        return productService.find(id);
    }

    @GetMapping("/quickSearch/{searchParams}")
    public List<ProductDto> findAllByPropMatch(@PathVariable String searchParams) {
        return productService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    public Page<ProductDto> findAllClients(@Nullable @RequestParam Boolean deleted, Pageable pageable) {
        deleted = deleted == null || deleted;
        return productService.findAllPageByDeleted(deleted, pageable);
    }

//    @PostMapping("/{id}")
//    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateClientDto updateDto) {//todo
//        productService.update(id, updateDto);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/new-client")
//    public ResponseEntity<ProductDto> create(@RequestBody CreateClientDto createClientDto) {
//        ClientDto clientDto = productService.create(createClientDto);
//        return ResponseEntity.ok().body(clientDto);
//    }
//
//    @PostMapping("/updateDeleted/{id}")
//    public ResponseEntity<String> updateDeleted(@PathVariable long id) {
//        productService.updateDeleted(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/restore/{id}")
//    public ResponseEntity<String> restore(@PathVariable long id) {
//        productService.restore(id);
//        return ResponseEntity.ok().build();
//    }
}
