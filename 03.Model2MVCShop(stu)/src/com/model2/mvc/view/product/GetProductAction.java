package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class GetProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String tranCode = request.getParameter("tranCode");
		request.setAttribute("tranCode", tranCode);
		ProductService service = new ProductServiceImpl();
		ProductVO vo = service.getProduct(prodNo);
		//
		System.out.println(vo.getManuDate());
		//
		vo.setManuDate(vo.getManuDate());
		request.setAttribute("vo", vo);
		
		String role = request.getParameter("menu");
		System.out.println(role);

		//Cookie
		
		String history = String.valueOf(vo.getProdNo())+",";
		
		Cookie[] cookie = request.getCookies();
		
		for(Cookie c : cookie) {
			if(c.getName().equals("history")) {
				String temp = c.getValue();
				c.setValue(history+temp);
				response.addCookie(c);
			}else {
				response.addCookie(new Cookie("history", history));
			}
		}//cookie 끝
			
		
		
		
		
		
		
		if(role == null) {
			System.out.println("go to getProduct.jsp");
			return "forward:/product/getProduct.jsp";
		}
		
		if(role.equals("manage") && (request.getParameter("tranCode").equals("1") || request.getParameter("tranCode")=="")) {
			//수정하는것
			System.out.println("go to manager ===> updateProductView.jsp");
			return "forward:/product/updateProductView.jsp";
		}else{			
			//상세보기하는것
			System.out.println("go to getProduct.jsp");
			return "forward:/product/getProduct.jsp";			
		}
		
		
		
		
	}

}
