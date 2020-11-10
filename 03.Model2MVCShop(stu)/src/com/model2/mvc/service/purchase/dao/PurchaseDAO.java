package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {
	
	public PurchaseDAO() {

	}
	
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		
		String sql ="insert into transaction values (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,1,sysdate,?)" ;
				
		PreparedStatement stmt = con.prepareStatement(sql);
		//stmt.setInt(1, productVO.getProdNo());
		System.out.println("insertPurchase에서의 purchaseVO :"+purchaseVO.getPurchaseProd());
		//ProductVO 가서 protranCode Setting
		stmt.setInt(1,purchaseVO.getPurchaseProd().getProdNo());
		
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		//stmt.setString(8, purchaseVO.getTranCode());
		//stmt.setDate(9,purchaseVO.getOrderDate());
		String temp = purchaseVO.getDivyDate();
		System.out.println(temp);
		stmt.setDate(8, Date.valueOf(purchaseVO.getDivyDate()));
	
		
		stmt.executeUpdate();
		System.out.println("Purchase insert 완료");
		con.close();
		
	}
	
	public Map<String,Object> getPurchaseList(Search searchVO,String buyerId) throws Exception {
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = null;
		
		String sql = "select * from transaction WHERE BUYER_ID = ?";
		
		sql += " order by ORDER_DATA";
		
		
		
		
		int totalCount = this.getTotalCount(sql,buyerId);
		
		
		
		
		
		
		
		
		sql = makeCurrentPageSql(sql, searchVO);
	
		 stmt = 
			con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		stmt.setString(1, buyerId);
		ResultSet rs = stmt.executeQuery();
	
		
	
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("totalCount", new Integer(totalCount));
	
		
		List<PurchaseVO> list = new ArrayList<PurchaseVO>();
		
		
			while(rs.next()) {
				PurchaseVO vo = new PurchaseVO();
				vo.setTranNo((rs.getInt("TRAN_NO")));
				
				//getProductVO
				ProductService service = new ProductServiceImpl();
				ProductVO productVO = new ProductVO();
				productVO = service.getProduct(rs.getInt("prod_no")); 				
				vo.setPurchaseProd(productVO);
				
				//getUserVO
				UserService serviceUser = new UserServiceImpl();
				UserVO userVO = new UserVO();
				userVO = serviceUser.getUser(buyerId);
				vo.setBuyer(userVO);
				
				
				vo.setTranCode(rs.getString("tran_status_code").trim());
				vo.setReceiverPhone(rs.getString("receiver_phone"));
				vo.setReceiverName(rs.getString("receiver_name"));
				vo.setPaymentOption(rs.getString("payment_option"));
				vo.setOrderDate(rs.getDate("order_data"));
				vo.setDivyRequest(rs.getString("dlvy_request"));
				vo.setDivyDate(rs.getString("dlvy_date"));
				vo.setDivyAddr(rs.getString("demailaddr"));
	
				list.add(vo);
				
			}
			
			
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());
	
		con.close();
		rs.close();
		stmt.close();
			
		return map;
	}
	public PurchaseVO findPurchase(int tranNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from transaction where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		PurchaseVO vo = null;
		while (rs.next()) {
			vo = new PurchaseVO();
			vo.setTranNo((rs.getInt("TRAN_NO")));
			
			//getProductVO
			ProductService service = new ProductServiceImpl();
			ProductVO productVO = new ProductVO();
			productVO = service.getProduct(rs.getInt("prod_no")); 				
			vo.setPurchaseProd(productVO);
			
			//getUserVO
			UserService serviceUser = new UserServiceImpl();
			UserVO userVO = new UserVO();
			userVO = serviceUser.getUser(rs.getString("buyer_id"));
			vo.setBuyer(userVO);
			
			
			vo.setTranCode(rs.getString("tran_status_code"));
			vo.setReceiverPhone(rs.getString("receiver_phone"));
			vo.setReceiverName(rs.getString("receiver_name"));
			vo.setPaymentOption(rs.getString("payment_option"));
			vo.setOrderDate(rs.getDate("order_data"));
			vo.setDivyRequest(rs.getString("dlvy_request"));
			vo.setDivyDate(rs.getString("dlvy_date"));
			vo.setDivyAddr(rs.getString("demailaddr"));
		}
		
		con.close();

		return vo;
		}
	

	public PurchaseVO findPurchase2(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from transaction where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		PurchaseVO vo = null;
		while (rs.next()) {
			vo = new PurchaseVO();
			vo.setTranNo((rs.getInt("TRAN_NO")));
			
			//getProductVO
			ProductService service = new ProductServiceImpl();
			ProductVO productVO = new ProductVO();
			productVO = service.getProduct(rs.getInt("prod_no")); 				
			vo.setPurchaseProd(productVO);
			
			//getUserVO
			UserService serviceUser = new UserServiceImpl();
			UserVO userVO = new UserVO();
			userVO = serviceUser.getUser(rs.getString("buyer_id"));
			vo.setBuyer(userVO);
			
			
			vo.setTranCode(rs.getString("tran_status_code"));
			vo.setReceiverPhone(rs.getString("receiver_phone"));
			vo.setReceiverName(rs.getString("receiver_name"));
			vo.setPaymentOption(rs.getString("payment_option"));
			vo.setOrderDate(rs.getDate("order_data"));
			vo.setDivyRequest(rs.getString("dlvy_request"));
			vo.setDivyDate(rs.getString("dlvy_date"));
			vo.setDivyAddr(rs.getString("demailaddr"));
		}
		
		con.close();

		return vo;
		}
	
	
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
	
		String sql = "update transaction set payment_option=?,receiver_name=?,receiver_phone=?,demailaddr=?,dlvy_request=?,dlvy_date=? where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDivyAddr());
		stmt.setString(5, purchaseVO.getDivyRequest());
		
		System.out.println(purchaseVO.getDivyDate());
		String temp = purchaseVO.getDivyDate().substring(0,10);
		System.out.println(temp);
		stmt.setDate(6, Date.valueOf(temp));
		
		
		stmt.setInt(7, purchaseVO.getTranNo());
		stmt.executeUpdate();
		System.out.println("update 들어온 purchaseVO"+purchaseVO);
		con.close();
	}
	
	public void updateTranCode(PurchaseVO purchaseVO,String tranCode) throws Exception {
		
		Connection con = DBUtil.getConnection();
	
		String sql = "update transaction set TRAN_STATUS_CODE=? where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, tranCode);
		stmt.setInt(2, purchaseVO.getTranNo());
		stmt.executeUpdate();
		System.out.println("update 들어온 purchaseVO"+purchaseVO);
		con.close();
	}
	
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	
	private int getTotalCount(String sql,String buyerId) throws Exception {
		
		sql = "SELECT COUNT(*) "+
				"FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, buyerId);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}
