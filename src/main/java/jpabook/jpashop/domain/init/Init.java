package jpabook.jpashop.domain.init;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.items.Book;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class Init {
    /**
     * 유닛 테스트용으로 Entity 를 만들기 위함
     */

    private final MemberRepository memberRepository;

    @PostConstruct
    public void initMethod(){
        Member test1 = createMember();
        Member test2 = createMember();
        test2.setName("qqqq");
        test2.setPassword("wwww");
        memberRepository.save(test1);
        memberRepository.save(test2);
    }


    // Member 객체 생성
    public static Member createMember() {
        Member member = new Member();
        member.setName(UUID.randomUUID().toString());
        member.setPassword("1111");
        log.info("{}", Thread.currentThread().getStackTrace()[1].getMethodName());
        Address address = createAddress();
        member.setAddress(address);

        return member;
    }
    // Delivery 객체 생성
    public static Delivery createDelivery(){

        Delivery delivery = new Delivery();
        delivery.setAddress(createAddress());
        log.info("{}", Thread.currentThread().getStackTrace()[1].getMethodName());
        delivery.setStatus(DeliveryStatus.READY);

        return delivery;
    }

    // Item 객체 생성
    public static Item createItem(){
        Book book = new Book();
        book.setName(getRandomStr(10));
        book.setAuthor(getRandomStr(3));
        book.setPrice(10000);
        book.setStockQuantity(10);
        book.setIsbn("1bdf-231s");

        return book;
    }

    private static Address createAddress() {
        Address address = new Address("seoul", "taeheran-ro", "22203");
        return address;
    }

    private static String getRandomStr(int size) {
        if(size > 0) {
            char[] tmp = new char[size];
            for(int i=0; i<tmp.length; i++) {
                int div = (int) Math.floor( Math.random() * 2 );

                if(div == 0) { // 0이면 숫자로
                    tmp[i] = (char) (Math.random() * 10 + '0') ;
                }else { //1이면 알파벳
                    tmp[i] = (char) (Math.random() * 26 + 'A') ;
                }
            }
            return new String(tmp);
        }
        return "ERROR : Size is required.";
    }



}
