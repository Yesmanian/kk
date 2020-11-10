package com.model2.mvc.view.product;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class AddProductAction extends Action {
	//아직 null값 리턴!!
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ProductVO productVO = new ProductVO();
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		
		String originalManuDate = request.getParameter("manuDate");
		String[] splitManuData = originalManuDate.split("-");
		String manuDate = String.join("", splitManuData);
		productVO.setManuDate(manuDate);
		
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setFileName(request.getParameter("fileName"));
	
		System.out.println(productVO);
		
		ProductService service = new ProductServiceImpl();
		service.addProduct(productVO);
		
		HttpSession session = request.getSession();
		session.setAttribute("vo", productVO);
		
		
		return "redirect:/product/addProduct.jsp";
		//return "redirect:/product/addProductView.jsp";
	}

}
