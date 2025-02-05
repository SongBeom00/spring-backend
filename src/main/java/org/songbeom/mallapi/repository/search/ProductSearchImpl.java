package org.songbeom.mallapi.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.songbeom.mallapi.domain.Product;
import org.songbeom.mallapi.domain.QProduct;
import org.songbeom.mallapi.domain.QProductImage;
import org.songbeom.mallapi.dto.PageRequestDTO;
import org.songbeom.mallapi.dto.PageResponseDTO;
import org.songbeom.mallapi.dto.ProductDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {


    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public PageResponseDTO<ProductDTO> searchProductList(PageRequestDTO pageRequestDTO) {

        log.info("searchProductList..........................................");
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        // QuerydslRepositorySupport 를 이용해서 Querydsl 처리
        QProduct qProduct = QProduct.product;
        QProductImage qProductImage = QProductImage.productImage;

        // Querydsl 처리
        JPQLQuery<Product> query = from(qProduct);
        query.leftJoin(qProduct.imageList, qProductImage);
        query.where(qProductImage.ord.eq(0));
        query.where(qProduct.delFlag.eq(false));

        // 페이징 처리
        Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query);

        // 결과 조회
        List<Product> productList = query.fetch(); // List 배열 형태로 조회
//        List<Tuple> productList = query.select(qProduct, qProductImage).fetch(); // List Tuple 형태로 조회

        // 결과 카운트 조회
        long totalCount = query.fetchCount();

        log.info("productList ==============================> {}",productList);

        return null;
    }






}
