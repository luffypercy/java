<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>welcome</h1>

<table class="ui-table">
		        <thead>
		            <tr>
					    <th>队名</th>
						<th class="text-r">队员</th>
						<th class="text-r">管理员</th>
					    <!-- <th>激活时间</th> -->
						<th>有效期</th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:forEach items="${dblist}" var="item">
		             <tr>
					    <td>${item}</td>
					    <td>
					     <c:forEach items="${colist[item] }" var="coi">
					     <th>
					     ${coi }
					     </th>
		           		 </c:forEach>
					    </td>
		            </tr>
		            </c:forEach>
		            
		        </tbody>
		        
		    </table>
</body>
</html>