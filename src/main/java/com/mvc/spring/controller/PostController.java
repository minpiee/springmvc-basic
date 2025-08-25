package com.mvc.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mvc.spring.dto.PostCreateRequestDTO;
import com.mvc.spring.service.PostService;

@Controller
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	// 생성자가 하나뿐이면 @Autowired 생략 가능 (Spring 4.3+)
	public PostController(PostService postService) {
		this.postService = postService;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String showCreateForm(Model model) {

		// 글 등록 페이지는 post 객체가 필요하다
		// 페이지에 처음 접속했을 때는 RedirectAttributes로 부터 전달된 데이터가 없으므로, 빈 DTO를 모델에 담는다
		// 글 등록 실패 후 리다이렉트된 경우에는 RedirectAttributes를 통해 전달된 DTO가 있으므로 새로 만들지 않는다
		if (!model.containsAttribute("post")) {
			model.addAttribute("post", new PostCreateRequestDTO());
		}

		return "post/create";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createPost(@ModelAttribute PostCreateRequestDTO postCreateRequestDTO, RedirectAttributes redirectAttributes) {
		try {
			Long postId = postService.createPost(postCreateRequestDTO);
			redirectAttributes.addFlashAttribute("message", "게시글이 등록되었습니다.");
			return "redirect:/posts/" + postId;
		} catch (Exception e) {
			// 사용자가 입력했던 내용을 리다이렉트되는 페이지로 전달한다
			redirectAttributes.addFlashAttribute("post", postCreateRequestDTO);
			redirectAttributes.addFlashAttribute("message", "게시글 등록에 실패했습니다.");
			return "redirect:/posts/new";
		}
	}
}