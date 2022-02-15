package org.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // 의존성 자동 주입
public class GuestbookServiceImpl implements GuestbookService{

    // 반드시 final로 선언
    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO-----------------------");
        log.info(dto);

        Guestbook entity = dtoEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }

    //페이지 리스트 받아오고 DTO로 변환하는 기능
    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDTO(entity));

        return new PageResultDTO<>(result, fn);
    }

    //방명록 읽어오는 기능 구현
    @Override
    public GuestbookDTO read(Long gno) {
        
        Optional<Guestbook> result = repository.findById(gno);
        
        //gno 로 찾은 결과가 있을 경우 entity 객체를 DTO로 변환해 반환. 없을 경우 null 반환
        return result.isPresent()? entityToDTO(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto){
        //제목, 내용을 업데이트

        Optional<Guestbook> result = repository.findById(dto.getGno());

        if(result.isPresent()) {
            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }
}
