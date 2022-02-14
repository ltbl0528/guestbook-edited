package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


//page, size 파라미터를 수집하는 역할
//진짜 목적은 JPA 쪽에서 사용하는 Pageable 타입의 객체를 생성하는 것
@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10; // 10 단위로 페이지 나눔
    }

    public Pageable getPageable(Sort sort) {
        //JPA 이용하는 경우 페이지 번호가 0부터 시작하므로, -1을 해줌
        return PageRequest.of(page -1, size, sort);
    }
}
