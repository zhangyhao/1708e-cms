<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<table class="table">
		  <thead>
		    <tr style="font-size: 15px;">
		      <th scope="col">Id</th>
		      <th scope="col">文章标题</th>
		      <th scope="col">投诉内容</th>
		      <th scope="col">投诉人的名字</th>
		      <th scope="col">投诉类型</th>
		      <th scope="col">投诉人的邮箱</th>
		      <th scope="col">投诉人的电话</th>
		      <th scope="col">投诉次数</th>
		      <th scope="col">投诉时间</th>
		      <th scope="col">投诉图片</th>
		    </tr>
		  </thead>
		  <tbody>
		    <c:forEach items="${link}" var="link">
        		<tr style="font-size: 12px;">
        			<td>${link.id}</td>
        			<td >${link.title}</td>
        			<td>${link.content}</td>
        			<td>${link.username}</td>
        			<td>
        			<c:if test="${link.complainType==1}">政治敏感</c:if>
        			<c:if test="${link.complainType==2}">反社会</c:if>
        			<c:if test="${link.complainType==3}">涉毒</c:if>
        			<c:if test="${link.complainType==4}">涉黄</c:if>
        			</td>
        			<td>${link.email}</td>
        			<td>${link.mobile}</td>
        			<td>${link.complainCnt}</td>
        			<td><fmt:formatDate value="${link.created}" pattern="yyyy年MM月dd日"/></td>
        			<td><img alt="" src="/pic/${link.picture}"  height="75px;" width="100px;"></td>
        		</tr>
        	</c:forEach>
		  </tbody>
		</table>
</body>
</html>