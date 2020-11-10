package com.model2.mvc.service.purchase;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.vo.PurchaseVO;


public interface PurchaseService {

	public void addPurchase(PurchaseVO purchaseVO) throws Exception;
	
	public PurchaseVO getPurchase(int tranNo) throws Exception;
	
	public PurchaseVO getPurchase2(int ProdNo) throws Exception;
	
	public Map<String,Object> getPurchaseList(Search searchVO,String buyerId) throws Exception;
	
	public Map<String,Object> getSaleList(Search searchVO) throws Exception;
	
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception;
	
	public void updateTranCode(PurchaseVO purchaseVO,String tranCode) throws Exception;
	
}