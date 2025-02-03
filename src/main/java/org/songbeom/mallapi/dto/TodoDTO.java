package org.songbeom.mallapi.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

    private Long tno; // pk를 비교할 때는 equals 랑 hashcode 를 사용한다 기본 자료형 지정 불가


    private String title;

    private String content;

    private boolean complete;

    private LocalDate dueDate;





}
