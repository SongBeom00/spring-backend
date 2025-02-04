package org.songbeom.mallapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "tbl_product")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@ToString(exclude = "imageList")
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long pno;

    private String pname; // 상품 이름

    private int price; // 상품 가격

    private String pdesc; // 상품 설명


    private boolean delFlag; // 삭제가 되었는지 여부


    @Builder.Default
    @ElementCollection
    private List<ProductImage> imageList = new ArrayList<>();


    public void changeName(String name) {
        this.pname = name;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeDesc(String desc) {
        this.pdesc = desc;
    }

    public void changeDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }


    public void addImage(ProductImage image) {

        image.setOrd(imageList.size());
        imageList.add(image);

    }

    public void addImageString(String fileName) {
        ProductImage productImage = ProductImage.builder()
                .fileName(fileName)
                .build();
        addImage(productImage);
    }

    public void clearList(){
        imageList.clear();
    }
}
