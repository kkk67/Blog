<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
        <div class="modal-header">
	          <h5 class="modal-title" id="exampleModalLabel">비밀번호 수정</h5>
	          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
	            <span aria-hidden="true">x</span>
	          </button>
	        </div>
	        	<div class="modal-body">
						<form action="#">
							<input type="hidden" id="userid" value="${userInfo.id}">
							     
	        <c:choose>
	        	<c:when test="${principal.member.role eq 'ADMIN' }">
			        <input type="hidden" id="isAdmin" value="1">
	        	</c:when>
	        	
	        	<c:otherwise>
					<input type="hidden" id="isAdmin" value="0">
					<div class="form-group">
							    <input type="password" class="form-control" placeholder="현재 비밀번호" id="origin-password"  >
					</div>
	        	</c:otherwise>
	        </c:choose>
	        				<div class="form-group">
							    <input type="password" class="form-control" placeholder="새 비밀번호" id="password"  onkeyup="checkPassword()">
							  </div>
							  		  <div class="form-group">
							    <input type="password" class="form-control" placeholder="새 비밀번호 확인" id="rePassword" onkeyup="checkPassword()">
							    <span id="result"></span>
							  </div>
						</form>
			        </div>
	        <div class="modal-footer">
	        <button class="btn btn-primary" type="button"  id="password-btn" >수정</button>
	          <button class="btn btn-danger" type="button" data-dismiss="modal">취소</button>
	        </div>
</body>
<script type="text/javascript">
	function checkPassword(){
		var password= $("#password").val();
		var checkpassword= $("#rePassword").val();
		
		if(password == checkpassword){
			$("#result").text("");
			$("#result").text("비밀번호가 일치합니다.");
			$("#result").css("color","green");
		}
		else{
			$("#result").text("");
			$("#result").text("비밀번호가 일치하지 않습니다.");
			$("#result").css("color","red");
		}
	}
</script>
</html>