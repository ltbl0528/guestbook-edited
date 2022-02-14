package org.zerock.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//제네릭 타입을 이용해 DTO와 EN(entity)이라는 타입을 지정.
//Function<EN, DTO>는 엔티티 객체들을 DTO로 변환해주는 기능을 함
@Data
public class PageResultDTO<DTO, EN> {

    //dto 리스트
    private List<DTO> dtoList;

    //총 페이지 번호
    private int totalPage;

    //현재 페이지 번호
    private int page;

    //목록 사이즈
    private int size;

    //시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    //이전, 다음 페이지 존재 여부
    private boolean prev, next;

    //페이지 번호 목록
    private List<Integer> pageList;


    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1; //0부터 시작하므로 1 추가
        this.size = pageable.getPageSize();

        //temp end page
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;

        start = tempEnd - 9;

        prev = start > 1;

        next = totalPage > tempEnd;

        end = totalPage > tempEnd ? tempEnd : totalPage;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }
}
