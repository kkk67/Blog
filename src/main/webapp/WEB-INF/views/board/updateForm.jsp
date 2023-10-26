<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" href="/css/board.css">
<div class="container">
	  <h2>게시글  수정</h2>
  <p>하단에 있는 제목과 내용을 입력하여 수정이 가능합니다.</p> 
	<div class="wrapper" style="width:auto;">
		<button id="btn-update" class="btn btn-primary" style="float:right">수정</button>
		<input type="hidden" id="id" value="${board.id}" />
			<div class="form-group">
				<input value="${board.title}" type="text" class="form-control" placeholder="Enter title" id="title">
			</div>
	</div>
			<div class="form-group">
				<textarea class="form-control summernote" rows="5" id="content">${board.content}</textarea>
			</div>
</div>
  <script>
      $('.summernote').summernote({
        tabsize: 2,
        height: 800
      });
    </script>
<script src ="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>