package org.songbeom.mallapi.service;

import org.songbeom.mallapi.dto.PageRequestDTO;
import org.songbeom.mallapi.dto.PageResponseDTO;
import org.songbeom.mallapi.dto.ProductDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductService {

    PageResponseDTO<ProductDTO> searchProductList(PageRequestDTO pageRequestDTO);

    Long register(ProductDTO productDTO); // 상품 등록 -> pno 반환

    ProductDTO get(Long pno); // 상품 상세 정보를 가져온다.

    void modify(ProductDTO productDTO); // 상품 정보를 수정한다.


    void remove(Long pno); // 상품을 삭제한다.



}
