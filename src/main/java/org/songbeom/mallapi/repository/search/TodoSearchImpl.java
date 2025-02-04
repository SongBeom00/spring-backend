package org.songbeom.mallapi.repository.search;

import com.querydsl.jpa.JPQLOps;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.songbeom.mallapi.domain.QTodo;
import org.songbeom.mallapi.domain.Todo;
import org.songbeom.mallapi.dto.PageRequestDTO;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;


@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    public TodoSearchImpl(){
        super(Todo.class);
    } // Todo class 를 이용해서 QuerydslRepositorySupport 생성자 호출


    @Override
    public Page<Todo> search1(PageRequestDTO pageRequestDTO) {

        log.info("search1..........................................");

        QTodo todo = QTodo.todo;

        // QueryDSL 의 JPQLQuery 객체 생성 (FROM 절)
        JPQLQuery<Todo> query = from(todo);


        // 페이징 정보 설정
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,     // 페이지 번호 (0부터 시작)
                pageRequestDTO.getSize(),         // 페이지 크기
                Sort.by("tno").descending()       // 정렬 기준
        );

        // 페이징 적용 (LIMIT, OFFSET 추가)
        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable, query);

        // 데이터 가져오기
        List<Todo> list = query.fetch();       // 페이징된 데이터 목록

        long total = query.fetchCount();       // 총 데이터 수

        // Page 객체로 반환
        return new PageImpl<>(list, pageable, total);
    }

    // 예시 조회 메서드
    //PageRequestDTO pageRequestDTO = new PageRequestDTO(2, 10);  // 2번째 페이지, 페이지 크기 10
//    SELECT *
//    FROM todo
//    ORDER BY tno DESC
//    LIMIT 10
//    OFFSET 10;


}
