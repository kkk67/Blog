<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<link rel="stylesheet" href="/css/board.css">
<div class="container" >
  <h2>게시글 작성</h2>
  <p>하단에 있는 제목과 내용을 입력하여 작성이 가능합니다.</p> 
  <div class="wrapper" style="width:auto;">
	  <button id="btn-save" class="btn btn-primary" style="float:right">작성</button>
			<div class="form-group">
				<input type="text"
					class="form-control" placeholder="Enter title" id="title">
			</div>  
  </div>
		<div class="form-group">
			<textarea class="form-control summernote" rows="5" id="content"></textarea>
		</div>
</div>
  <script>
      $('.summernote').summernote({
        tabsize: 2,
        height: 500
      });
    </script>
<script src ="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>