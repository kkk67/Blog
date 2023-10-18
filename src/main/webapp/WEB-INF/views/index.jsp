<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>
<!-- <script>
function dateFormat(date) {
    let month = date.getMonth() + 1;
    let day = date.getDate();
    let hour = date.getHours();
    let minute = date.getMinutes();
    let second = date.getSeconds();

    month = month >= 10 ? month : '0' + month;
    day = day >= 10 ? day : '0' + day;
    hour = hour >= 10 ? hour : '0' + hour;
    minute = minute >= 10 ? minute : '0' + minute;
    second = second >= 10 ? second : '0' + second;

    return date.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
}
	var createDate = new Date(${board.createDate});
	
	var createDate = dateFormat(createDate);
	
	console.log(createDate);
</script> -->

<div class="container"  style="height: auto;">
	<c:forEach var="board" items="${boards.content }">
		<div class="card m-2">
			<div class="card-body card-clickable" data-boardid="${board.id }")>
				<h3 class="card-title"><b>제목 :</b> <i>${board.title}</i></h3>
				<p><b>작성자 :</b> <i>${board.user.username } </i> / <i><b>작성 시간 :</b> ${board.createDate }</i></p>
				<%-- <a href="/board/${board.id}" class="btn btn-primary">상세 보기</a> --%>
			</div>
		</div>
	</c:forEach>
<script type="text/javascript">
	$(document).ready(function(){
		$(".card-clickable").click(function(){
			var boardId = $(this).data("boardid");
			var url = "/board/" + boardId;
			window.open(url,"_self");
		});
		
		$(".card-clickable").hover(function(){
			$(this).css("background-color","#007bff");
		}, function(){
			$(this).css("background-color","white");
		});
	})	;
</script>
	<c:choose>
		<c:when test="${isEmpty eq true }">
			<br/>
			<h3>게시글이 존재하지 않습니다.</h3>
			<br/><br/>
		</c:when>
		<c:otherwise>
			<ul class="pagination  justify-content-center">
		<c:choose>
			<c:when test="${boards.first }">
					<li class="page-item disabled"><a class="page-link"
			href="">Previous</a></li>
			</c:when>
			<c:otherwise>
		<li class="page-item"><a class="page-link"
			href="?page=${boards.number}">Previous</a></li>
			</c:otherwise>
		</c:choose>
		<c:forEach var="page" begin="1" end="${Total}">
				<c:choose>
				<c:when test="${boards.number eq page-1}">
					<li class="page-item active"><a class="page-link" href="?page=${page}">${page}</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="?page=${page}">${page}</a></li>
				</c:otherwise>
				</c:choose>
		</c:forEach>
		
		<c:choose>
			<c:when test="${boards.last }">
				<li class="page-item disabled"><a class="page-link"
			href="">Next</a></li>
			</c:when>
			<c:otherwise>
			<li class="page-item"><a class="page-link"
			href="?page=${boards.number+2}">Next</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
		</c:otherwise>
	</c:choose>
</div>
<%@ include file="layout/footer.jsp"%>