package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdateTranCodeByProdAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String tranCode = request.getParameter("tranCode");
		
		//ProductService serviceProduct = new ProductServiceImpl();
		
		
		
		
		PurchaseService service= new PurchaseServiceImpl();
		//getPurchase2는 prodNo으로 purchaseVO을 가져온다
		PurchaseVO vo = service.getPurchase2(prodNo);
		service.updateTranCode(vo,tranCode);
		
		return "forward:/listProduct.do?menu=manage";
	}

}
