package org.songbeom.mallapi.repository;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.songbeom.mallapi.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Log4j2
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    @Rollback(value = false)
    void 상품추가() throws Exception {
        //given
        Product product = Product.builder()
                .pname("상품2")
                .price(10000)
                .pdesc("상품2 설명")
                .build();

        product.addImageString(UUID.randomUUID()+"_"+"test3.jpg");
        product.addImageString(UUID.randomUUID()+"_"+"test4.jpg");

        //when
        productRepository.save(product);

        //then
        
    }


    @Test
    void 상품조회1() throws Exception {
        //given
        Long pno = 2L;

        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        log.info(product);
        log.info(product.getImageList());
        //when

        //then
        Assertions.assertThat(pno).isEqualTo(product.getPno());

    }

    @Test
    void 상품조회2() throws Exception {
        //given
        Long pno = 2L;

        Optional<Product> result = productRepository.selectOne(pno);

        Product product = result.orElseThrow();

        log.info(product);
        log.info(product.getImageList());
        //when

        //then
        Assertions.assertThat(pno).isEqualTo(product.getPno());

    }


    @Test
    @Rollback(value = false)
    void 상품삭제() throws Exception {
        //given
        Long pno = 2L;
        //when
        productRepository.updateToDelete(pno, true);

        //then

    }

    @Test
    @Rollback(value = false)
    void 상품수정() throws Exception {
        //given
        Product product = productRepository.selectOne(3L).get();

        //when
        product.changePrice(20000);
        product.clearList();

        product.addImageString(UUID.randomUUID()+"_"+"PIMAGE1.jpg");

        product.addImageString(UUID.randomUUID()+"_"+"PIMAGE2.jpg");

        product.addImageString(UUID.randomUUID()+"_"+"PIMAGE3.jpg");

        productRepository.save(product);
        //then

    }

}