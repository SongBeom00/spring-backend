package org.songbeom.mallapi.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.songbeom.mallapi.dto.PageRequestDTO;
import org.songbeom.mallapi.dto.PageResponseDTO;
import org.songbeom.mallapi.dto.TodoDTO;
import org.songbeom.mallapi.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {


    private final TodoService todoService;

    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable("tno") Long tno){

        return todoService.get(tno);

    }


    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO){
        log.info("list......."+pageRequestDTO);


        return todoService.getList(pageRequestDTO);
    }


    @PostMapping
    public Map<String,Long> register(@RequestBody TodoDTO dto){
        log.info("todoDTO....... "+dto);
        Long tno = todoService.register(dto);

        return Map.of("TNO",tno);
    }


    @PutMapping("/{tno}")
    public Map<String,String> modify(@PathVariable("tno") Long tno,@RequestBody TodoDTO todoDTO){
        todoDTO.setTno(tno);
        todoService.modify(todoDTO);
        return Map.of("result","success");

    }


    @DeleteMapping("/{tno}")
    public Map<String,String> delete(@PathVariable Long tno){

        todoService.remove(tno);
        return Map.of("result","success");

    }



}
