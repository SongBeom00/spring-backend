package org.songbeom.mallapi.service;

import jakarta.transaction.Transactional;
import org.songbeom.mallapi.domain.Todo;
import org.songbeom.mallapi.dto.PageRequestDTO;
import org.songbeom.mallapi.dto.PageResponseDTO;
import org.songbeom.mallapi.dto.TodoDTO;

@Transactional
public interface TodoService {

    TodoDTO get(Long tno);

    Long register(TodoDTO dto); //pk 값을 return 아니면 데이터 자체를 return 해도 된다.

    void modify(TodoDTO dto); //예외를 처리하기 위해 void 반환타입

    void remove(Long tno); //pk 값으로 삭제

    PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO);


    default TodoDTO entityToDTO(Todo todo){

        return TodoDTO.builder()
                .tno(todo.getTno())
                .title(todo.getTitle())
                .content(todo.getContent())
                .complete(todo.isComplete())
                .dueDate(todo.getDueDate())
                .build();
    }



    default Todo dtoToEntity(TodoDTO todoDTO){

        return Todo.builder()
                .tno(todoDTO.getTno())
                .title(todoDTO.getTitle())
                .content(todoDTO.getContent())
                .complete(todoDTO.isComplete())
                .dueDate(todoDTO.getDueDate())
                .build();
    }


}
