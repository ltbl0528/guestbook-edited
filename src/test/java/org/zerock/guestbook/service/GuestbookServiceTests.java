package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    //service 의 등록 부분 테스트
    //엔티티로 변환한 결과를 얻고, DB에 해당 샘플 등록
//    @Test
//    public void testRegister() {
//
//        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
//                .title("Sample Title...")
//                .content("Sample Content...")
//                .writer("user0")
//                .build();
//
//        System.out.println(service.register(guestbookDTO));
//    }

    @Test
    public void testSearch() {
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("테스트")
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(requestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("-----------------------------------------------");

        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }

        System.out.println("========================================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}
