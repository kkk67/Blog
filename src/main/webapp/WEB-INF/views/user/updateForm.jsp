<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container justify-content-center" style="width: 900px"> 
<%-- 	<button id="btn-update"  class="btn btn-primary" >수정</button>
	<button id="btn-delete" class="btn btn-danger" onclick="index.delete(${principal.user.id})">회원탈퇴</button>
	<br/><br/> --%>
	
	<form style="width:800px;">
		<input type="hidden" id ="id" value="${principal.user.id }"/>
		<div class="form-group">
			<label for="username">Username</label> <input type="text"
				class="form-control"  value=" ${principal.user.username }" placeholder="Enter Username" id="username" readonly>
		</div>
		<c:if test="${empty principal.user.oauth }">
		<div class="form-group">
			<label for="password">비밀번호</label> 
			<!-- <input type="password" class="form-control"   placeholder="Enter password" id="password"> -->
				<a class="btn btn-secondary" data-toggle="modal" data-target="#PasswordModal" style="float: right;" >수정</a>
		</div>		
		<div class="form-group">
			<input type="hidden"  id="origin-email" value="${principal.user.email }">
			<label for="email">이메일</label>
			<h3>${principal.user.email }</h3>
			<a class="btn btn-secondary" data-toggle="modal" data-target="#EmailModal" style="float: right;" onclick="disabledFoam()">수정</a>
			<%-- <input type="email" class="form-control"  value=""  placeholder="${principal.user.email }" id="email" > --%>
				<!-- 이메일 인증 -->
		<!-- 	<button id="sendNumber"  type="button" class="btn btn-secondary"  onclick="index.sendMail()">인증번호 전송</button> -->
			<!-- <input type="hidden" id="hidden-text"> -->
		<!-- 	<input type="text" id="Confirm" name="Confirm" style="display: none" value="">
			<div class="form-group last mb-4 check_input" id="mail_number"   style="display: none" >
			<br/>
				<label for="number" id="confirmTxt">인증번호를 입력해주세요</label>
				<div>
					<input type="text" name="number" id="number" style="width:250px; margin-top: -10px">
					<button id="confirmBtn" type="button" class="btn btn-primary" onclick="index.confirmNumber(0)">이메일 인증</button>			
				</div>
			</div> -->
		</div>
			<!--비밀번호 모달 -->
			<div class="modal fade" id="PasswordModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	    <div class="modal-dialog" role="document">
	      <div class="modal-content">
	      <%@include file="../modal/passwordModal.jsp" %>
	      </div>
	    </div>
	  </div>
	   <!-- 비밀번호 모달 끝 -->
	   	<!--이메일 모달 -->
			<div class="modal fade" id="EmailModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	    <div class="modal-dialog" role="document">
	      <div class="modal-content">
	      <%@include file="../modal/emailModal.jsp" %>
	      </div>
	    </div>
	  </div>
	   <!-- 이메일 모달 끝 -->
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
			
<!-- 			<span id="emailResult"></span> 	 -->
</div>
<script type="text/javascript">
function disabledFoam(){
	$("#newEmail").attr("disabled",true);
	$("#sendNumber").attr("disabled",true);
}
</script>
<script src ="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>