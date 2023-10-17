<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container justify-content-center">
	<h4 style="text-align: center; font-weight:bold;">비밀번호 확인</h4>
	
	<div class="form-group">
			<label for="username" style=>Username</label> <input type="text"
				class="form-control"  value=" ${principal.user.username }" placeholder="Enter Username"   id="username" readonly>
		</div>
		<div class="form-group">
			<label for="password" >Password:</label> 
			<input type="password" class="form-control"   placeholder="Enter password" id="password"  >
		<button id="validPwd"   type="button"  class="btn btn-secondary" onclick="index.validPwd(${principal.user.id })">비밀번호 확인</button>
		
		</div>		
		
</div>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>