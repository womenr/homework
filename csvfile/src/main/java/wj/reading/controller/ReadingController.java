package wj.reading.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import wj.base.controller.BaseController;
import wj.reading.dto.ReadingDto;
import wj.reading.utils.WordParserUtils;

@Controller
@RequestMapping("/reading")
public class ReadingController extends BaseController {

	@GetMapping("/jp")
	public String japanese(Model model) {
		model.addAttribute("readingDto", new ReadingDto());
		return "reading/jp";
	}

	@PostMapping("/analyze")
	public String analyze(@ModelAttribute ReadingDto readingDto, ModelMap model) {
		
		List<String> wordList = WordParserUtils.contentToWordList(readingDto.getContent());
		
		model.addAttribute("result", String.join(System.getProperty("line.separator"), wordList));
		model.addAttribute("row", wordList.size() == 0 ? 1 : wordList.size());
		
		return "reading/result";

	}
}
