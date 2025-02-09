package org.songbeom.mallapi.repository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.songbeom.mallapi.domain.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
@Transactional //테스트 종료시 롤백
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;


    @Test
    public void test1() {

        Assertions.assertNotNull(todoRepository); //null 이 아니면 성공

        log.info(todoRepository.getClass().getName());

    }


    @Test
    public void testInsert(){

        for (int i = 0; i < 100; i++) {
            Todo todo = Todo.builder()
                    .title("Title.."+i)
                    .content("Content.."+i)
                    .dueDate(LocalDate.of(2025,1,1))
                    .build();
            Todo result = todoRepository.save(todo);

            log.info(result);
        }

    }


    @Test
    public void testRead(){
        Long tno = 1L;

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        log.info(todo);
    }

    @Test
    public void testUpdate(){

        //먼저 로딩 하고 엔티티 객체 변경 /setter
        Long tno = 1L;


        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();


        todo.updateTodo("Update title","updated content",true);

        todoRepository.save(todo);


    }

    @Test
    public void testPaging(){
        //페이지 번호는 0부터 시작
        Pageable pageable = PageRequest.of(0,10, Sort.by("tno").descending());

        Page<Todo> result = todoRepository.findAll(pageable); //페이징 처리

        log.info(result.getTotalElements());

        log.info(result.getContent());

    }


//    @Test
//    public void testSearch1(){
//        todoRepository.search1();
//
//    }


}
