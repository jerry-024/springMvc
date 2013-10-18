<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'list.jsp' starting page</title>
    <style type="text/css">
    	.error{
    		color:red;
    	}
    </style>
  </head>
  
  <body>
    <h3>修改用户</h3>
    
    <form:form method="post" commandName="user" action="/user/save">
    	<form:hidden path="id"/>
    	用户名：<form:input path="username"/><form:errors path="username" cssClass="error"/><br/>
    	密码：<form:input path="password"/><form:errors path="password" cssClass="error"/> <br/>
    	<input type="submit" value="保存"/>
    </form:form>
  
  </body>
</html>
