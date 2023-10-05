<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/auth/loginProc" method= "post">
		<div class="form-group">
			<label for="username">Username</label> <input type="text"
				class="form-control" name = "username" placeholder="Enter Username" id="username">
		</div>

		<div class="form-group">
			<label for="password">Password:</label> <input type="password"  name="password"
				class="form-control" placeholder="Enter password" id="password">
		</div>
		<c:if test="${error eq true }">
			<div id="valid" class="alert alert-danger" role="alert">${exception }</div>
		</c:if>
	<button id="btn-login" class="btn btn-primary">로그인</button>
	<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=cdee08dfe352b3104e97fedffd2f90d6&redirect_uri=http://localhost:8000/auth/kakao/callback"><img height="38px"  src="/image/kakao_login_button.png"></a>
	</form>
</div>


<%@ include file="../layout/footer.jsp"%>