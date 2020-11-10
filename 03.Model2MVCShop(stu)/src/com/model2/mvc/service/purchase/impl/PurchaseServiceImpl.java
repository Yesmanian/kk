package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseServiceImpl implements PurchaseService {
	
	private PurchaseDAO purchaseDAO;
	
	public PurchaseServiceImpl() {		
		purchaseDAO=new PurchaseDAO();
	}
	
	@Override
	public void addPurchase(PurchaseVO purchaseVO) throws Exception {
		//insertPurchase	
		purchaseDAO.insertPurchase(purchaseVO);		
	}

	@Override
	//구매이력조회 ->구매상세보기
	public PurchaseVO getPurchase(int tranNo) throws Exception {
		
		return purchaseDAO.findPurchase(tranNo);
	}

	@Override
	public PurchaseVO getPurchase2(int prodNo) throws Exception {
		
		return purchaseDAO.findPurchase2(prodNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search searchVO, String buyerId) throws Exception {
		
		return purchaseDAO.getPurchaseList(searchVO,buyerId);
	}

	@Override
	public HashMap<String, Object> getSaleList(Search searchVO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
		
			purchaseDAO.updatePurchase(purchaseVO);
	}

	@Override
	public void updateTranCode(PurchaseVO purchaseVO,String tranCode) throws Exception {
			purchaseDAO.updateTranCode(purchaseVO,tranCode);

	}

}
