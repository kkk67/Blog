<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에러 페이지</title>
<script>
	var message = "${msg}";
	var url = "${url}";
	alert(message);
	document.location.href = url;
</script>
</head>
<body>
 
</body>
</html>