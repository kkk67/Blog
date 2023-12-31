<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container" style="margin-top: 100px;">
	<c:forEach var="search" items="${search.content }">
		<div class="card m-2">
		<span id = "id"  style="display:none;"><i>${search.id} </i></span>
			<div class="card-body card-hoverable" >
				<div class="card-clickable" data-searchid="${search.id }">
					<h3 class="card-title"><i><b>제목 :</b></i> <i>${search.title}</i></h3>
					<p><b>작성자 :</b> <i>${search.member.username }</i> / <b>작성 시간 :</b> <i>${search.createDate }</i></p>
					<%-- <a href="/board/${search.id}" class="btn btn-primary">상세 보기</a> --%>
				</div>
				<c:choose>
					<c:when test="${search.member.id == principal.member.id}">
						<div>
							<a href="/board/${search.id}/updateForm" class="btn btn-warning">수정</a>
							<button id="btn-delete"  type="button" role="button" class="btn btn-danger">삭제</button>
						</div>
					</c:when>
					<c:when test="${principal.member.role eq 'ADMIN'}">
							<div>
							<a href="/board/${search.id}/updateForm" class="btn btn-warning">수정</a>
							<button id="btn-delete"  type="button" role="button" class="btn btn-danger">삭제</button>
						</div>
					</c:when>
				</c:choose>
			</div>
		</div>
	</c:forEach>
	<script type="text/javascript">
	$(document).ready(function(){
		$(".card-clickable").click(function(){
			var searchid = $(this).data("searchid");
			var url = "/board/" + searchid;
			window.open(url,"_self");
		}); 
		$(".card-clickable").hover(function(){
			$(this).css("cursor","pointer");
		}, function(){
			$(this).css("cursor","default");
		});
		
		
		$(".card-hoverable").hover(function(){
			$(this).css("background-color","#90AFFF");  /* #007bff */
		}, function(){
			$(this).css("background-color","white");
		});
	})	;
</script>
	<!-- 검색 결과가 없으면 -->
	<c:choose>
		<c:when test="${isEmpty }">
			<br/>
			<h2>입력하신 "${keyword }"  의 대한 검색 결과가 없습니다. </h2>
			<button class="btn btn-secondary"  onclick="history.back()">돌아가기</button>
			<br/><br/>
		</c:when>
		<c:otherwise>
			<ul class="pagination justify-content-center">
	<c:choose>
		<c:when test="${hasPrev eq false }"> <!-- 이전 페이지가 없으면 -->
		  <li class="page-item disabled" ><a class="page-link" href="#" >Previous</a></li>		
		</c:when>
		<c:otherwise>
			  <li class="page-item" ><a class="page-link" href="/search?keyword=${keyword }&page=${previous+1}" >Previous</a></li>		
		</c:otherwise>
	</c:choose>
	
	<c:forEach  var="page"  begin="1"  end="${Total }">
		<c:choose>
			<c:when test="${search.number eq page-1}">
				<li class="page-item active"><a class="page-link" href="/search?keyword=${keyword }&page=${page}">${page}</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="/search?keyword=${keyword }&page=${page}">${page}</a></li>
			</c:otherwise>
		</c:choose>	
	</c:forEach>
	
	<c:choose>
				<c:when test="${hasNext eq false }"> <!-- 다음 페이지가 없으면 -->
		  <li class="page-item disabled" ><a class="page-link" href="#" >Next</a></li>		
		</c:when>
		<c:otherwise>
			  <li class="page-item" ><a class="page-link" href="/search?keyword=${keyword }&page=${next+1}" >Next</a></li>		
		</c:otherwise>
	</c:choose>
	</ul>
		</c:otherwise>
	</c:choose>
</div>
<br/>
<script src ="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>