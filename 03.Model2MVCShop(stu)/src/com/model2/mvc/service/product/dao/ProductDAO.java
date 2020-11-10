package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;

public class ProductDAO {
	
	public ProductDAO() {

	}
	
	public void insertProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		//증가하는게 1,20 맘대로 증가한다
		//ProdNo를 어떻게 VO에 넘겨주나? 필요한 작업인가? 나중에 받아올때 해주면되나?
		String sql ="insert into product values (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)" ;
				
		PreparedStatement stmt = con.prepareStatement(sql);
		//stmt.setInt(1, productVO.getProdNo());
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.executeUpdate();
		System.out.println("insert 완료");
		con.close();
		
	}
	
	/*
	 * public HashMap<String,Object> getProductList(SearchVO searchVO) throws
	 * Exception {
	 * 
	 * Connection con = DBUtil.getConnection();
	 * 
	 * String sql = "select * from product "; if (searchVO.getSearchCondition() !=
	 * null) { if (searchVO.getSearchCondition().equals("0")) { sql +=
	 * " where prod_no like '%" + searchVO.getSearchKeyword() + "%'"; } else if
	 * (searchVO.getSearchCondition().equals("1")) { sql +=
	 * " where prod_name like '%" + searchVO.getSearchKeyword() + "%'"; } else if
	 * (searchVO.getSearchCondition().equals("2")) { sql += " where price='" +
	 * searchVO.getSearchKeyword() + "'"; } } sql += " order by prod_no";
	 * 
	 * PreparedStatement stmt = con.prepareStatement( sql,
	 * ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs
	 * = stmt.executeQuery();
	 * 
	 * rs.last(); int total = rs.getRow(); System.out.println("로우의 수:" + total);
	 * 
	 * HashMap<String,Object> map = new HashMap<String,Object>(); map.put("count",
	 * new Integer(total));
	 * 
	 * rs.absolute(searchVO.getPage() * searchVO.getPageUnit() -
	 * searchVO.getPageUnit()+1); System.out.println("searchVO.getPage():" +
	 * searchVO.getPage()); System.out.println("searchVO.getPageUnit():" +
	 * searchVO.getPageUnit());
	 * 
	 * ArrayList<ProductVO> list = new ArrayList<ProductVO>(); if (total > 0) { for
	 * (int i = 0; i < searchVO.getPageUnit(); i++) { ProductVO vo = new
	 * ProductVO(); vo.setProdNo(rs.getInt("PROD_NO"));
	 * vo.setProdName(rs.getString("PROD_NAME"));
	 * vo.setProdDetail(rs.getString("PROD_DETAIL"));
	 * vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
	 * vo.setPrice(rs.getInt("PRICE")); vo.setFileName(rs.getString("IMAGE_FILE"));
	 * vo.setRegDate(rs.getDate("REG_DATE"));
	 * 
	 * list.add(vo); if (!rs.next()) break; } } System.out.println("list.size() : "+
	 * list.size()); map.put("list", list); System.out.println("map().size() : "+
	 * map.size());
	 * 
	 * con.close();
	 * 
	 * return map; }
	 */


public HashMap<String,Object> getProductList(Search searchVO) throws Exception {
	
	Connection con = DBUtil.getConnection();
	
	String sql = "select p.* ,  t.tran_status_code from product p,transaction t where p.prod_no = t.prod_no(+) ";
	if (searchVO.getSearchCondition() != null) {
		if (searchVO.getSearchCondition().equals("0")) {
			sql += " and p.prod_no like '%" + searchVO.getSearchKeyword()
					+ "%'";
		} else if (searchVO.getSearchCondition().equals("1")) {
			sql += " and p.prod_name like '%" + searchVO.getSearchKeyword()
					+ "%'";
		} else if (searchVO.getSearchCondition().equals("2")) {
			sql += " and p.price='" + searchVO.getSearchKeyword()
					+ "'";
		}
	}
	sql += " order by p.prod_no";
	
	System.out.println("ProductDAO::Original SQL :: " + sql);
	
	int totalCount = this.getTotalCount(sql);
	System.out.println("UserDAO :: totalCount  :: " + totalCount);
	
	sql = makeCurrentPageSql(sql, searchVO);

	PreparedStatement stmt = 
		con.prepareStatement(	sql,
													ResultSet.TYPE_SCROLL_INSENSITIVE,
													ResultSet.CONCUR_UPDATABLE);
	ResultSet rs = stmt.executeQuery();

	List<ProductVO> list = new ArrayList<ProductVO>();
	

	HashMap<String,Object> map = new HashMap<String,Object>();


		while(rs.next()) {
			ProductVO vo = new ProductVO();
			vo.setProdNo(rs.getInt("PROD_NO"));
			vo.setProdName(rs.getString("PROD_NAME"));
			vo.setProdDetail(rs.getString("PROD_DETAIL"));
			vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
			vo.setPrice(rs.getInt("PRICE"));
			vo.setFileName(rs.getString("IMAGE_FILE"));
			vo.setRegDate(rs.getDate("REG_DATE"));
			//'1  '으로 들어옴
			
			vo.setProTranCode((rs.getString("tran_status_code") != null)?rs.getString("tran_status_code").trim():rs.getString("tran_status_code"));
			
			list.add(vo);
		}	
	map.put("list", list);
	System.out.println("list.size() : "+ list.size());
	
	map.put("totalCount", new Integer(totalCount));
	System.out.println("totalcount : "+ totalCount);

	con.close();
	rs.close();
	stmt.close();
		
	return map;
}

public ProductVO findProduct(int prodNo) throws Exception {
	
	Connection con = DBUtil.getConnection();

	String sql = "select * from product where prod_no=?";
	
	PreparedStatement stmt = con.prepareStatement(sql);
	stmt.setInt(1, prodNo);

	ResultSet rs = stmt.executeQuery();

	ProductVO productVO = null;
	while (rs.next()) {
		productVO = new ProductVO();
		productVO.setProdNo(rs.getInt(1));
		productVO.setProdName(rs.getString(2));
		productVO.setProdDetail(rs.getString(3));
		productVO.setManuDate(rs.getString(4));
		productVO.setPrice(rs.getInt(5));
		productVO.setFileName(rs.getString(6));
		productVO.setRegDate(rs.getDate(7));
	}
	
	con.close();

	return productVO;
	}

	public void updateProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
	
		String sql = "update product set prod_name=?, prod_detail=?, manufacture_day=?,price=?,image_file=? where prod_No=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		System.out.println("update 들어온 productVO"+productVO);
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

