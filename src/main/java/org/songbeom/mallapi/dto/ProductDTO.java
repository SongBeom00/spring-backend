package org.songbeom.mallapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long pno;

    private String pname; // 상품 이름

    private int price; // 상품 가격

    private String pdesc; // 상품 설명

    private boolean delFlag; // 삭제가 되었는지 여부

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>(); // 업로드 파일들은 List 로 관리

    @Builder.Default
    private List<String> uploadedFileNames = new ArrayList<>(); // 이미 업로드된 파일들의 이름을 List 로 관리



}
