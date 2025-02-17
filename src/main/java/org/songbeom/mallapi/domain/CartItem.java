package org.songbeom.mallapi.domain;


import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"product", "cart"})
@Table(name = "tbl_cart_item", indexes = {
        @Index(columnList = "cart_cno", name = "idx_cartitem_cart"),
        @Index(columnList = "product_pno, cart_cno", name = "idx_cartitem_pno_cart")
})
public class CartItem {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long cino;


    @ManyToOne
    @JoinColumn(name = "product_pno")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_cno")
    private Cart cart;

    private int qty;


    /** 비즈니스 메서드 * /
     *  수량 변경
     * @param qty
     */
    public void changeQty(int qty){
        this.qty = qty;
    }



}
