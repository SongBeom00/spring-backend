package org.songbeom.mallapi.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.songbeom.mallapi.domain.Todo;
import org.songbeom.mallapi.dto.PageRequestDTO;
import org.songbeom.mallapi.dto.PageResponseDTO;
import org.songbeom.mallapi.dto.TodoDTO;
import org.songbeom.mallapi.repository.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Log4j2
@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;


    @Override
    public TodoDTO get(Long tno) {

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        return entityToDTO(todo);
    }

    @Override
    public Long register(TodoDTO dto) {

        Todo todo = dtoToEntity(dto);

        Todo result = todoRepository.save(todo);

        return result.getTno();
    }

    @Override
    public void modify(TodoDTO dto) {
        Optional<Todo> result = todoRepository.findById(dto.getTno());
        // result 값으로 pk값 Tno 가 있는 유저 정보를 들고온다.

        Todo todo = result.orElseThrow();
        //- **`orElseThrow`**:
        // 만약 조회 결과가 없을 경우 예외를 발생시켜
        // 잘못된 요청이나 없는 데이터 처리에 대비합니다.

        todo.changeTitle(dto.getTitle());
        todo.changeContent(dto.getContent());
        todo.changeComplete(dto.isComplete());
        todo.changeDueDate(dto.getDueDate());

        todoRepository.save(todo);

    }

    @Override
    public void remove(Long tno) {

        todoRepository.deleteById(tno);


    }

    @Override
    public PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO) {

        //JPA
        Page<Todo> result = todoRepository.search1(pageRequestDTO);

        List<TodoDTO> dtoList = result.get()// entity list
                .map(this::entityToDTO) // entity -> dto
                .collect(Collectors.toList()); // dto list

        return PageResponseDTO.<TodoDTO>withAll()
        .dtoList(dtoList)
        .pageRequestDTO(pageRequestDTO)
        .total(result.getTotalElements())
        .build();
    }
}
