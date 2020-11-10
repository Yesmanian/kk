<%@page import="java.util.LinkedHashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Arrays"%>
<%@ page contentType="text/html; charset=EUC-KR" %>

<html>
<head>

<title>열어본 상품 보기</title>

</head>
<body>
	당신이 열어본 상품을 알고 있다
<br>
<br>
<%
	request.setCharacterEncoding("euc-kr");
	response.setCharacterEncoding("euc-kr");
	String history = null;
	Set wordsSet = new LinkedHashSet();
	Cookie[] cookies = request.getCookies();
	if (cookies!=null && cookies.length > 0) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("history")) {
				history = cookie.getValue();
			}
		}
		if (history != null) {
			String[] h = history.split(",");
			wordsSet.addAll(Arrays.asList(h));
			for (Object i : wordsSet) {
				if (!((String)i).equals("null")) {
%>
			<a href="/getProduct.do?prodNo=<%=Integer.parseInt((String)i)%>&menu=search"   target="rightFrame"><%=(String)i%></a>
		
			
<br>
<%
				}
			}
		}
	}
%>

</body>
</html>