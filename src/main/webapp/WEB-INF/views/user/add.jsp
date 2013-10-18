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
    <h3>添加用户</h3>
    <form:form method="post" commandName="user" action="/user/save">
    	<form:errors path="*" cssClass="errorblock" element="div" />
    	用户名：<form:input path="username"/><form:errors path="username" cssClass="error"/><br/>
    	密码：<form:password path="password"/><form:errors path="password" cssClass="error"/> <br/>
    	<input type="submit" value="保存"/>
    </form:form>
    
    
    <form action="/user/save" method="post">
    	用户名：<input type="text" name="username" value="${user.username }"/><br/>
    	密码： <input type="password" name="password" value="${user.password}"/> <br/>
    	<input type="submit" value="保存"/>
    </form>
  </body>
</html>
