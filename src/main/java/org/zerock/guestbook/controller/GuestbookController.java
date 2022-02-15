package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor //의존성 자동 주입
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping("/")
    public String index() {

        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list...................." + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    // 등록 작업을 처리하는 메소드
    @GetMapping("/register")
    public void register() {
        log.info("register get....");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto..." + dto);

        //새로 추가된 엔티티 번호
        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    //GET 방식으로 gno 값을 받아 Model에 GuestbookDTO 객체를 담아서 전달하도록 작성
    //후에 목록 페이지로 돌아가야 하므로 PageRequestDTO를 파라미터로 사용
    //modify도 읽어올 때 기능은 read와 같으므로 같은 메소드를 사용(
    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {

        log.info("gno: "+ gno);

        GuestbookDTO dto = service.read(gno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes) {

        log.info("gno: "+ gno);

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO
    , RedirectAttributes redirectAttributes) {

        log.info("post modify..........................................");
        log.info("dto: "+ dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/guestbook/read";
    }
}
