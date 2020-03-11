package board.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import board.bean.BoardDTO;
// 모든 Controller를 합침

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/board/boardList.do")
	public ModelAndView boardList(HttpServletRequest request, HttpServletResponse response) {
		// 1. 데이터
		int pg = 1;
		if(request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2. DB
		//페이지당 화면5개
		int endNum = pg * 5;
		int startNum = endNum - 4;

		//BoardDAO boardDAO = new BoardDAO();
		List<BoardDTO> list = boardService.boardList(startNum, endNum);
		
		int totalA = boardService.getTotalA(); // 총 글수
		int totalP = (totalA + 4) / 5; // 총 페이지수
		int startPage = (pg - 1) / 3 * 3 + 1;
		int endPage = startPage + 2;
		if (endPage > totalP)
			endPage = totalP;
		
		ModelAndView modelAndView = new ModelAndView();
		
		// 데이터 공유데이터 저장
		modelAndView.addObject("list", list);
		modelAndView.addObject("pg", pg);
		modelAndView.addObject("totalP", totalP);
		modelAndView.addObject("startPage", startPage);
		modelAndView.addObject("endPage", endPage);
		
		modelAndView.setViewName("../board/boardList.jsp");
		return modelAndView;
	}
	// JSON	
	@RequestMapping(value = "/board/boardListJson.do")
	public ModelAndView boardListJson(HttpServletRequest request) {
		// 1. 데이터
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2. DB
		int endNum = pg * 5;
		int startNum = endNum - 4;
		
		List<BoardDTO> list = boardService.boardList(startNum, endNum);
		
		// JSON으로 결과값 반환
		String rt = null;
		int total = list.size();
		
		if(total > 0) {
			rt = "LIST OK";
		} else {
			rt = "FAIL";
		}
		// JSON객체 생성
		JSONObject json = new JSONObject();
		json.put("rt", rt);
		json.put("total", total);
		
		if(total > 0) {
			JSONArray item = new JSONArray();
			for(int i = 0; i<total; i++) {
				BoardDTO boardDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("seq", boardDTO.getSeq());
				temp.put("id", boardDTO.getId());
				temp.put("name", boardDTO.getName());
				temp.put("subject", boardDTO.getSubject());
				temp.put("content", boardDTO.getContent());
				temp.put("hit", boardDTO.getHit());
				temp.put("logtime", boardDTO.getLogtime());
				// JSONArray에 저장
				item.put(i,temp);
			}
			
			// json 객체에 저장
			json.put("item", item);
		}
		
		
		// 검색결과를 서블릿으로 리턴
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("../board/boardListJson.jsp");
		return modelAndView;
	}

	
	@RequestMapping(value="/board/boardView.do")
	public ModelAndView boardView(HttpServletRequest request, HttpServletResponse response) {
		// 1. 데이터
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		
		// 2. DB
		//BoardDAO boardDAO = new BoardDAO();
	
		boardService.updateHit(seq); // 조회수 증가
		BoardDTO boardDTO = boardService.boardView(seq);// 상세 데이터 얻기
		
		// 3. 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		
		// 데이터 공유
		modelAndView.addObject("pg", pg);
		modelAndView.addObject("boardDTO", boardDTO);
		
		// view 처리 파일
		modelAndView.setViewName("boardView.jsp");
		return modelAndView;
	}
	
	@RequestMapping(value="/board/boardWriteForm.do")
	public ModelAndView boardWriteForm(HttpServletRequest request, HttpServletResponse response) {
		// 3. 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		
		// View처리파일
		modelAndView.setViewName("boardWriteForm.jsp");
		return modelAndView;

	}
	
	@RequestMapping(value="/board/boardWrite.do")
	public ModelAndView boardWrite(HttpServletRequest request, HttpServletResponse response) {
		
		// 1. 데이터
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String id = (String)session.getAttribute("memId");
		String name = (String)session.getAttribute("memName");
		
		// 2. DB
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setName(name);
		boardDTO.setId(id);
		boardDTO.setSubject(subject);
		boardDTO.setContent(content);
		
		//BoardDAO boardDAO = new BoardDAO();
		int su = boardService.boardWrite(boardDTO);
		
		// 3. 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		
		// 데이터 공유
		modelAndView.addObject("su", su);
		// view 처리 파일
		modelAndView.setViewName("boardWrite.jsp");
		return modelAndView;
		
	}
	
	@RequestMapping(value="/board/boardDelete.do")
	public ModelAndView boardDelete(HttpServletRequest request, HttpServletResponse response) {
		// 1. 데이터
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pg = Integer.parseInt(request.getParameter("pg"));
		
		// 2. DB
		//BoardDAO boardDAO = new BoardDAO();
		int su = boardService.boardDelete(seq);
		// 3. 화면 네비게이션
		ModelAndView modelAndView = new ModelAndView();
		// 데이터 공유데이터 저장
		modelAndView.addObject("su", su);
		modelAndView.addObject("pg", pg);

		// view 처리 파일 이름 저장
		modelAndView.setViewName("boardDelete.jsp");
		return modelAndView;
	}
}
