package com.comc.om;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExamController {
	
	
	@RequestMapping("script")
	public String script() {
		return "study";
	}

	
}
