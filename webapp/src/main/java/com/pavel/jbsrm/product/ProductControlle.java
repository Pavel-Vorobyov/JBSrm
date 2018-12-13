package com.pavel.jbsrm.product;

import com.pavel.jbsrm.client.dto.ClientDto;
import com.pavel.jbsrm.client.dto.CreateClientDto;
import com.pavel.jbsrm.client.dto.UpdateClientDto;
import com.pavel.jbsrm.product.dto.ProductDto;
import com.pavel.jbsrm.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.http.ResponseEntity;
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
    public Page<ProductDto> findAllClients(
            @Nullable @RequestParam Integer page,
            @Nullable @RequestParam Integer rowsPerPage,
            @Nullable @RequestParam Boolean isActive) {
        page = page == null ? 0 : page;
        rowsPerPage = rowsPerPage == null ? 5 : rowsPerPage;
        isActive = isActive == null || isActive;

        QSort qSort = new QSort();
        Pageable pageable = new QPageRequest(page, rowsPerPage, qSort);
        return productService.findAllPageByActive(isActive, pageable);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> updateClient(@PathVariable long id, @RequestBody UpdateClientDto updateDto) {//todo
        productService.updateClient(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/new-client")
    public ResponseEntity<ProductDto> createClient(@RequestBody CreateClientDto createClientDto) {
        ClientDto clientDto = productService.createClient(createClientDto);
        return ResponseEntity.ok().body(clientDto);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable long id) {
        productService.deleteClient(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restore/{id}")
    public ResponseEntity<String> restoreClient(@PathVariable long id) {
        productService.restoreClient(id);
        return ResponseEntity.ok().build();
    }
}
