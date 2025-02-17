package org.songbeom.mallapi.domain;


import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "owner")
@Table(
        name = "tbl_cart",
        indexes = { @Index(name = "idx_cart_email", columnList = "member_owner") }
)
public class Cart {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long cno;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name ="member_owner")
    private Member owner;


}
