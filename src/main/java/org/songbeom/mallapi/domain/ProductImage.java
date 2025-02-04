package org.songbeom.mallapi.domain;


import jakarta.persistence.Embeddable;
import lombok.*;

@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ProductImage {

    private String fileName;

    @Setter
    private int ord;

}