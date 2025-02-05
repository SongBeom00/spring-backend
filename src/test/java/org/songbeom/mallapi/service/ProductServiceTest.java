package org.songbeom.mallapi.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.songbeom.mallapi.dto.PageRequestDTO;
import org.songbeom.mallapi.dto.PageResponseDTO;
import org.songbeom.mallapi.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
@Transactional
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    void 상품조회() throws Exception {
        //given
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();


        PageResponseDTO<ProductDTO> responseDTO = productService.searchProductList(pageRequestDTO);

        log.info(responseDTO.getDtoList());

        //when

        //then

    }

    @Test
    void 상품등록() throws Exception {
        //given
        ProductDTO productDTO = ProductDTO.builder()
                .pname("새로운 상품")
                .price(10000)
                .pdesc("신규 추가 상품입니다.")
                .build();
        //when
        productDTO.setUploadedFileNames(List.of(UUID.randomUUID()+"_"+"TEST2025.jpg",UUID.randomUUID()+"_"+"TEST2026.jpg"));
        productService.register(productDTO);


        //then

    }


}