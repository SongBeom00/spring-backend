package org.songbeom.mallapi.repository;

import org.songbeom.mallapi.domain.Todo;
import org.songbeom.mallapi.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> , TodoSearch {


}
