package org.songbeom.mallapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder //부모클래스의 필드를 사용할 수 있게 해줌
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1; //기본값 지정

    @Builder.Default
    private int size = 10;





}
