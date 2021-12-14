package com.com.com.approval;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApprovalController {
	
	@Inject
	private SqlSessionTemplate sqlSession;
	
	@RequestMapping("login")
	public String loginView(@RequestParam(value="logId", required=false) String logId
						  , @RequestParam(value="logPass", required=false) String logPass
						  , Model model
						  , HttpSession session
						  , HttpServletResponse response) throws IOException {
		
		if(session.getAttribute("memInfo") != null) {
			return "redirect:list";
		}
		
		if(logId == null) {
			return "login";
		}else {
			Map<String, Object> map = sqlSession.selectOne("mapper.loginChk", logId);
			if(map == null) {
				model.addAttribute("loginMsg", "idFail");
				return "login";
			}else if (!logPass.equals(map.get("memPass").toString())) {
				model.addAttribute("loginMsg", "passFail");
				return "login";
			}else {
				session.setAttribute("memInfo", map);
				session.setAttribute("memInfo", map);
				
//				PrintWriter writer = response.getWriter();
//				writer.print("<script>alert('로그인 성공')</script>");
//				writer.close();
				return "redirect:list";
			}
		}
	}
	
	@RequestMapping("logOut")
	public String logOut(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
	
	@RequestMapping("list")
	public String list(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		
		map.put("memInfo", session.getAttribute("memInfo"));
		if(map.get("memInfo") != null) {
			List<Map<String, Object>> list = sqlSession.selectList("mapper.apprList", map);
			model.addAttribute("apprList", list);
		}
		return "list";
	}
	
	@RequestMapping("searchList")
	public String searchList(@RequestParam Map<String, Object> map, Model model, HttpSession session) {
		map.put("memInfo", session.getAttribute("memInfo"));
		if(map.get("memInfo") != null) {
			List<Map<String, Object>> list = sqlSession.selectList("mapper.apprList", map);
			model.addAttribute("apprList", list);
		}
		return "searchList";
	}
	
	@RequestMapping("write")
	public String writeView(Model model) {
		
		int seq = sqlSession.selectOne("mapper.writeSeq");
		
		
		model.addAttribute("listSeq", seq);
		model.addAttribute("mode", "add");
		
		
		return "writeView";
	}
	
	@RequestMapping("writeProc")
	public String writeProc(@RequestParam Map<String, Object> map, HttpSession session) {
			
			
		
			int seq = sqlSession.selectOne("mapper.dataChk", map.get("seq").toString());
			
			Map<String, Object> memInfo = (Map<String, Object>)session.getAttribute("memInfo");
			
			map.put("memInfo", memInfo);
			String rank = memInfo.get("memRank").toString();
			String aChk = "Y";
			if((!"tmp".equals(map.get("status").toString())) && ("ga".equals(rank) || "ba".equals(rank))){
				map.put("appChk", aChk);
			}
			
			
			
			if(seq == 0) {
				sqlSession.insert("mapper.insert", map);
			}else {
				sqlSession.update("mapper.update", map);
			}
				sqlSession.insert("mapper.history", map);
			return "redirect:list";
	}
	
	@RequestMapping("detail")
	public String detail(Model model, @RequestParam(required=false, defaultValue = "0") int seq) {
		
		
		Map<String, Object> detailMap = sqlSession.selectOne("mapper.detail", seq);
		
		List<Map<String, Object>> hisMap = sqlSession.selectList("mapper.histList", seq);
		
		model.addAttribute("appHistory", hisMap);
		model.addAttribute("mode", "mofy");
		model.addAttribute("detailMap", detailMap);
		
		return "writeView";
	}

}
