package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    //service 의 등록 부분 테스트
    //엔티티로 변환한 결과를 얻고, DB에 해당 샘플 등록
    @Test
    public void testRegister() {

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user0")
                .build();

        System.out.println(service.register(guestbookDTO));
    }
}
