package org.songbeom.mallapi.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@ToString
@Getter
@Builder //Builder를 사용하면 세트로 생성자를 넣어주어야 한다.
@AllArgsConstructor
@NoArgsConstructor


@Table(name = "tbl_todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno; // pk를 비교할 때는 equlas 랑 hashcode 를 사용한다 기본 자료형 지정 불가


    @Column(length = 500,nullable = false)
    private String title;

    private String content;

    private boolean complete;

    private LocalDate dueDate;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeComplete(boolean complete) {
        this.complete = complete;
    }

    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }



}
