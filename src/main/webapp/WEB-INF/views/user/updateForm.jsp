<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<input type="hidden" id ="id" value="${principal.user.id }"/>
		<div class="form-group">
			<label for="username">Username</label> <input type="text"
				class="form-control"  value=" ${principal.user.username }" placeholder="Enter Username" id="username" readonly>
		</div>
		<c:if test="${empty principal.user.oauth }">
		<div class="form-group">
			<label for="password">Password:</label> <input type="password"
				class="form-control"   placeholder="Enter password" id="password">
		</div>		
		<div class="form-group">
			<label for="email">Email address:</label> <input type="email"
				class="form-control"  value=" ${principal.user.email }"  placeholder="Enter email" id="email" >
		</div>
			<button id="btn-update" class="btn btn-primary">수정</button>
		</c:if>

		<c:if test="${not empty principal.user.oauth }">
		<div class="form-group">
			<label for="password">Password:</label> <input type="password"
				class="form-control"   placeholder="" id="password" readonly>
		</div>		
		<div class="form-group">
			<label for="email">Email address:</label> <input type="email"
				class="form-control"  value=" ${principal.user.email }"  placeholder="Enter email" id="email" readonly>
			</div>
		</c:if>
	</form>
</div>
<script src ="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>