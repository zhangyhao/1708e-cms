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
<body>
			<form action="/article/plain" method="post">
			投诉类型:
			<select name="type" id="type">
			<option value=" ">--请选择--</option>
			<option value="1">政治敏感</option>
			<option value="2">反社会</option>
			<option value="3">涉毒</option>
			<option value="4">涉黄</option>
			</select>
			次数大于<input type="text" id="complain1" name="complain1">小于<input type="text" name="complain2" id="complain2">
			<input type="submit" value="提交">
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
		      <th scope="col">投诉次数<a href="/article/plain?a=1">▼</a><a href="/article/plain?a=2" onclick="aa()">▲</a></th>
		      <th scope="col">投诉时间</th>
		      <th scope="col">投诉图片</th>
		      <th scope="col">操作</th>
		     
		    </tr>
		  </thead>
		  <tbody>
		    <c:forEach items="${plain}" var="link">
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
        			<td>
        			<c:if test="${link.complainCnt>50}"></c:if>
        			<c:if test="${link.complainCnt<50}">
        			<input type="button" value="禁止">
        			</c:if></td>
        			<Td><a href="/article/xq?id=${link.id}">详情</a></Td>
        		</tr>
        	</c:forEach>
		  </tbody>
		</table>
		</form>
		<nav aria-label="Page navigation example">
		  <ul class="pagination justify-content-center">
		    <li class="page-item">
		      <a class="page-link" href="javascript:void()" tabindex="-1" aria-disabled="true" onclick="gopage(${pg.prePage==0?1:pg.prePage})"  >Previous</a>
		    </li>
		   	<c:forEach begin="1" end="${pg.pages}" varStatus="i">
		   		<li class="page-item"><a class="page-link" href="javascript:void()" onclick="gopage(${i.index})">${i.index}</a></li>
		   	</c:forEach>
		    <li class="page-item">
		      <a class="page-link" href="javascript:void()" onclick="gopage(${pg.nextPage==0?pg.pages:pg.nextPage})">Next</a>
		    </li>
		  </ul>
		</nav>
		
		 <script type="text/javascript">
		 function gopage(page){
				$("#workcontent").load("/article/plain?page="+page);
			}
		 
		function a() {
			$("#workcontent").load("/article/plain?a=1");
		}
		function aa() {
			$("#workcontent").load("/article/plain?a=2");
		}
		function xq(id) {
			$("#workcontent").load("/article/xq?id="+id);
		}
		 </script>
</body>
</html>