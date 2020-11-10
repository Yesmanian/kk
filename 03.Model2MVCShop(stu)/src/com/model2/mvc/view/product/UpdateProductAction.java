package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class UpdateProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		ProductVO productVO = new ProductVO();
		productVO.setProdNo(prodNo);
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		
		String originalManuDate = request.getParameter("manuDate");
		String[] splitManuData = originalManuDate.split("-");
		String manuDate = String.join("", splitManuData);
		productVO.setManuDate(manuDate);
		System.out.println(manuDate);
		System.out.println(Integer.parseInt(request.getParameter("price")));
		
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setFileName(request.getParameter("fileName"));
		System.out.println(productVO);
		
		
		
		ProductService service = new ProductServiceImpl();
		service.updateProduct(productVO);
		
		HttpSession session = request.getSession();
		session.setAttribute("vo", productVO);
		
//		HttpSession session = request.getSession();
//		String sessionId = (ProductVO)session.getAttribute("product");
//		
//		if(sessionId.equals(prodId)) {
//			session.setAttribute("", value);
//		}
		
		
		
		
		
		return "redirect:/getProduct.do?prodNo="+prodNo;
	}

}
