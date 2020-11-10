package com.model2.mvc.view.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class ListUserAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		Search searchVO=new Search();
		HttpSession session = request.getSession();
		
		int page=1;
		if(request.getParameter("page") != null)
			page=Integer.parseInt(request.getParameter("page"));
		
		searchVO.setPage(page);
		
		
//		if((SearchVO)session.getAttribute("searchVO") != null) {
//			SearchVO VO = (SearchVO)session.getAttribute("searchVO");
//			VO.setSearchCondition(VO.getSearchCondition());
//		}else {
//			searchVO.setSearchCondition(request.getParameter("searchCondition"));			
//		}
//		
//		if((SearchVO)session.getAttribute("searchVO") != null) {
//			SearchVO VO = (SearchVO)session.getAttribute("searchVO");
//			VO.setSearchKeyword(VO.getSearchKeyword());
//		}else {
//			searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
//		}
//		
		
		
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		String pageUnit=getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		
		UserService service=new UserServiceImpl();
		HashMap<String,Object> map=service.getUserList(searchVO);
		
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		
		
		return "forward:/user/listUser.jsp";
	}
}