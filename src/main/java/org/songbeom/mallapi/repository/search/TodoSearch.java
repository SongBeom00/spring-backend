package org.songbeom.mallapi.repository.search;

import org.songbeom.mallapi.domain.Todo;
import org.songbeom.mallapi.dto.PageRequestDTO;
import org.springframework.data.domain.Page;

public interface TodoSearch {

    Page<Todo> search1(PageRequestDTO pageRequestDTO);
}
