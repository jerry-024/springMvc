<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'list.jsp' starting page</title>
  </head>
  
  <body>
  <a href="/user/add">添加用户</a>
  ${message}
   <c:forEach items="${userList }" var="user">
   	<h4>${user.username } <a href="javascript:;" class="delLink" ref="${user.id }">删除</a><a href="/user/${user.id}">修改</a></h4> 
   </c:forEach>
   
   <script type="text/javascript" src="http://cdn.staticfile.org/jquery/1.9.1/jquery.min.js"></script>
   <script type="text/javascript">
   	$(function(){
   		$(".delLink").click(function(){
   			var id = $(this).attr("ref");
   			$(this).parent().hide();
   			$.ajax({
   				type:"delete",
   				url:"/user/"+id,
   				success:function(data){
   					var data = 1;
   					if(data === 1) {
   						alert("删除成功");
   						
   					}
   				}
   			});
   		});
   	});
   </script>
  </body>
</html>
