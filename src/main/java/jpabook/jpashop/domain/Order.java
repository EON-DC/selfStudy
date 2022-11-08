package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime createdTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //== 양방향 관계 메서드 ==//
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setMember(Member member){
        member.getOrders().add(this);
        this.member = member;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 비즈니스 메서드 ==//

    // 총 주문 가격 조회
    public int getTotalOrderPrice(){
        // 각 주문들의 getTotalPrice를 호출한다.
        int sum = getOrderItems().stream().mapToInt(OrderItem::getTotalPrice).sum();
        return sum;
    }

    // 주문 생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        // 회원, 배송정보, 배송목록 조합
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        // 각각의 주문들을 order.orderItems List의 addOrderItem를 사용해해 담는다.
        Arrays.stream(orderItems).forEach(order::addOrderItem);
        order.setStatus(OrderStatus.READY);
        order.setCreatedTime(LocalDateTime.now());
        return order;
    }

    // 주문 취소
    public void cancelOrder(Order order){
        // 배송이 출발한 상태이면 IllegalStateException 발생
        if (order.getDelivery().getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료 상품으로 취소할 수 없습니다.");
        }
        // 배송이 아직 준비단계이면, 각각의 OrderItem 객체의 cancel 함수 호출
        order.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.cancel();
        }
    }

}
