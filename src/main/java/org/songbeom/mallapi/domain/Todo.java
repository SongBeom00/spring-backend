package org.songbeom.mallapi.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@ToString
@Getter
@Builder //Builder 를 사용하면 세트로 생성자를 넣어주어야 한다.
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tbl_todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long tno; // pk를 비교할 때는 equals 랑 hashcode 를 사용한다 기본 자료형 지정 불가

    @Column(length = 500,nullable = false)
    private String title; //제목

    private String content; //내용

    private boolean complete; //완료 여부

    private LocalDate dueDate; //마감일

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

    /** 비즈니스 로직 */
    public void updateTodo(String title, String content, boolean complete) {
        this.title = title;
        this.content = content;
        this.complete = complete;
    }



}
