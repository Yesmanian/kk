package com.model2.mvc.view.purchase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class ListPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		Search searchVO = new Search();
		
		HttpSession session = request.getSession();
		if(session.getAttribute("user") != null){	
			String buyerId=((UserVO)session.getAttribute("user")).getUserId();
			
			int currentPage = 1;
			
			if(request.getParameter("currentPage") != null){
				currentPage=Integer.parseInt(request.getParameter("currentPage"));
			}	
			searchVO.setCurrentPage(currentPage);
			searchVO.setSearchCondition(request.getParameter("searchCondition"));
			searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
			
			int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
			int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
			searchVO.setPageSize(pageSize);
			
			PurchaseService service = new PurchaseServiceImpl();
			Map<String,Object> map=service.getPurchaseList(searchVO,buyerId);
			
			Page resultPage	= 
					new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
			System.out.println("ListUserAction ::"+resultPage);
			
			
			request.setAttribute("list", map.get("list"));
			request.setAttribute("resultPage", resultPage);
			request.setAttribute("searchVO", searchVO);
			
			String role = request.getParameter("menu");
			request.setAttribute("menu", role);
			System.out.println(role);

			// πŸ≤„¡÷¿⁄
				return "forward:/purchase/listPurchase.jsp";
			
		}else {
			return "forward:/user/loginView.jsp";
		}
		
		
		
	}

}
