package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		ProductVO productVO = (ProductVO)session.getAttribute("productVO");
		UserVO userVO=(UserVO)session.getAttribute("user");
			
		PurchaseVO vo = new PurchaseVO();
		vo.setBuyer(userVO);
		vo.setDivyAddr(request.getParameter("receiverAddr"));
		vo.setDivyDate(request.getParameter("receiverDate"));
		vo.setDivyRequest(request.getParameter("receiverRequest"));
		//vo.getOrderDate();
		vo.setPaymentOption(request.getParameter("paymentOption"));
		vo.setPurchaseProd(productVO);
		vo.setReceiverName(request.getParameter("receiverName"));
		vo.setReceiverPhone(request.getParameter("receiverPhone"));
		
		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(vo);
		System.out.println("addPurchaseAction¿¡¼­ÀÇ purchaseVO :"+vo);
		
		request.setAttribute("vo", vo);
		
		return "forward:/purchase/addPurchase.jsp";
	}
	
}
