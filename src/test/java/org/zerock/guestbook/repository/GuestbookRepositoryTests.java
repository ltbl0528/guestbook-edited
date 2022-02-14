package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    //dummy data 넣는 테스트
//    @Test
//    public void insertDummies() {
//        IntStream.rangeClosed(1, 300).forEach(i -> {
//
//            Guestbook guestbook = Guestbook.builder()
//                    .title("Title..." + i)
//                    .content("Content..." + i)
//                    .writer("user" + (i % 10))
//                    .build();
//            System.out.println(guestbookRepository.save(guestbook));
//        });
//    }

    //수정 시간 테스트
//    @Test
//    public void updateTest() {
//
//        //존재하는 번호로 테스트
//        Optional<Guestbook> result = guestbookRepository.findById(300L);
//
//
//        if(result.isPresent()) {
//            Guestbook guestbook = result.get();
//
//            guestbook.changeTitle("Change Title...");
//            guestbook.changeContent("Change Content...");
//
//            guestbookRepository.save(guestbook);
//        }
//    }

    //단일 항목 검색 테스트
//    @Test
//    public void testQuery1() {
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno"));
//
//        //1. 동적 처리를 위해 Q도메인 클래스를 얻어옴 (title, content를 변수로 사용 가능)
//        QGuestbook qGuestbook = QGuestbook.guestbook;
//
//        String keyword = "1";
//
//        //2. where문에 들어가는 조건들을 넣어주는 컨테이너
//        BooleanBuilder builder = new BooleanBuilder();
//
//        //3. 원하는 조건은 필드 값과 같이 결합해서 생성
//        //boolean builder에 들어가는 값은 querydsl의 Predicate 타입이어야 함
//        BooleanExpression expression = qGuestbook.title.contains(keyword);
//
//        //4. 만들어진 조건은 where문의 and, or과 같은 키워드와 결합
//        builder.and(expression);
//
//        //5. GuestbookRepository에 추가된 QuerydslPredicateExecutor 인터페이스의 findAll() 사용 가능
//        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
//
//        result.stream().forEach(guestbook -> {
//            System.out.println(guestbook);
//        });
//
//    }

    //다중 항목 검색 테스트
    @Test
    public void testQuery2() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);

        BooleanExpression exContent = qGuestbook.content.contains(keyword);

        //1. exTitle과 exContent 결합
        BooleanExpression exAll = exTitle.or(exContent);

        //2. 결합한 exAll을 BooleanBuilder에 추가
        builder.and(exAll);

        //3. gno가 0보다 크다는 조건 추가
        builder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
}
