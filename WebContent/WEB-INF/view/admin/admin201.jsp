<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String contextRoot = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">	
	
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- jQuery UI 사용 환경 설정 -->
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>


<style>

</style>

<script>


	$(document).ready(function() {

/* 		//Search 버튼 클릭시 Ajax 요청 및 결과 출력
		$("button.btnSearch").on("click", function() {
			ajax(this);
		});

		//주의) 동적 생성된 Next 버튼에 대한 이벤트 등록
		$(document).on("click", "button#next", function() {
			ajax(this);
		});

		//주의) 동적 생성된 Previous 버튼에 대한 이벤트 등록
		$(document).on("click", "button#previous", function() {
			ajax(this);
		});

	});

	function ajax(btn) {
		//검색 키워드
		var keyword = $("input#keyword").val();
		//요청(현재) 페이지
		var start = $(btn).val();
		//한 페이지당 게시물수
		var maxResults = 10;
		if (keyword != "") {
			getAjax(keyword, start, maxResults);
		} */
		
		//강의가능 과목버튼
		$("button.btn-sm").on("click", function(){
			var teacher_id = $(this).val();
			var name =  $(this).parent().parent().children().eq(1).text();
			var id =  $(this).parent().parent().children().eq(0).text();
			var phone =  $(this).parent().parent().children().eq(3).text();
			console.log(teacher_id);
			console.log(name);
			
			$.get("admin_teacher_subjectList.do", {teacher_id:teacher_id}, function(data) {
	            var result = data;
	            
	            console.log(data);
	  
	   
	            $("#tlist-Modal h4").html(id+" "+name+"/"+phone);
	            $("#tlist-Modal tbody").html(result);
	         });      
		});
		
		//수정버튼
		$("button.btn-re").on("click", function(){
			var teacher_id = $(this).val();
			var name =  $(this).parent().parent().children().eq(1).text();
			var id =  $(this).parent().parent().children().eq(0).text();
			var phone =  $(this).parent().parent().children().eq(3).text();
			var ssn =  $(this).parent().parent().children().eq(2).text();
			
			
			$("#t-mod-Modal input#teacher_id").val(teacher_id);
			$("#t-mod-Modal input#name").val(name);
			$("#t-mod-Modal input#phone").val(phone);
			$("#t-mod-Modal input#ssn").val(ssn);

			
			 $.get("admin_teacherupdatelist.do", {teacher_id:teacher_id}, function(data) {
	            var result = data;
	            
	            console.log(data);
	            
	            $("#sublist").html(result);
	         });       
		});
		
		//삭제버튼
		$("button.btn-del").on("click", function(){
			var teacher_id = $(this).val();
			var id = $(this).parent().parent().children().eq(0).text();
			var name =  $(this).parent().parent().children().eq(1).text();
			var phone =  $(this).parent().parent().children().eq(3).text();
			console.log(id);
			console.log(teacher_id);
			console.log(name);
			console.log(phone);
			
			$.get("admin_teacherdelete.do", {teacher_id:teacher_id}, function(data) {
				var result = data;
			
			
				$("#t-del-Modal h5#title").html(id+" 강사 삭제");
				$("#t-del-Modal h4#info").html(name+" / "+phone);
		 });     
		});	
	
	

	});
</script>
</head>
<body>


	<div id="main">
		 <div class="title">
			<img src="${pageContext.request.contextPath}/img/sist_logo.png" width="300px">
			<div class="login">관리자 님 │ 로그아웃</div>
		</div>
		<div id="menu">
		<div class="menu">
				<ul class="nav nav-pills nav-justified">
					<li class=""><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">기초 정보 관리</a>
						<ul class="dropdown-menu">
							<li><a
								href="${pageContext.request.contextPath}/adminmain.do">과정명</a></li>
							<li><a href="${pageContext.request.contextPath}/admin102.do">과목명</a></li>
							<li><a href="${pageContext.request.contextPath}/admin103.do">교재명</a></li>
							<li><a href="${pageContext.request.contextPath}/admin104.do">강의실명</a></li>
						</ul></li>
						<li class="active"><a href="${pageContext.request.contextPath}/admin201.do">강사 계정 관리</a></li>
					<li class=""><a href="${pageContext.request.contextPath}/adminOpenCourseList.do">개설 과정/과목
							관리</a></li>
					<li class=""><a
						href="${pageContext.request.contextPath}/admin401.do">수강생 관리</a></li>
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="#">성적
							관리</a>
						<ul class="dropdown-menu">
							<li><a href="${pageContext.request.contextPath}/admin501.do">과목별</a></li>
							<li><a href="${pageContext.request.contextPath}/admin502.do">수강생별</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
<%-- <%@ include file = "/WEB-INF/view/header.jsp" %> --%>
		<div class="content">


			<h3>강사 계정 관리</h3>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>강사ID</th>
						<th>강사명</th>
						<th>주민번호 뒷자리</th>
						<th>전화번호</th>
						<td>등록일</td>
						<th>강의 가능 과목</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="r" items="${result}">
					<tr>
						<td>${r.id}</td>
						<td>${r.name}</td>
						<td>${r.ssn}</td>
						<td>${r.phone}</td>
						<td>${r.regDate}</td>
						<td><button type="button" class="btn btn-default btn-sm" ${(r.sub_count==0)?'disabled':''} data-toggle="modal" data-target="#tlist-Modal"  value ="${r.id}" >
								<span class="badge" id="Count">${r.sub_count}</span> 보기
							</button></td>
						<td><button type="button" class="btn btn-default btn-re" data-toggle="modal" data-target="#t-mod-Modal" value ="${r.id}">수정</button></td>
						<td><button type="button" class="btn btn-default btn-del" ${(r.open_sub_count==0)?'':'disabled'}
								data-toggle="modal" data-target="#t-del-Modal" value="${r.id}">삭제</button></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<ul class="pager">
				<li><button type="button" class="btn btn-default" id="previous"
						value="1" disabled="disabled">Previous</button></li>
				<li><button type="button" class="btn btn-default" id="next"
						value="2" disabled="disabled">Next</button></li>
			</ul>


			<form class="form-inline" method="post" style="text-align: center;">
				<button type="button" style="float: left;" class="btn btn-default"
					data-toggle="modal" data-target="#t-insert-Modal">등록</button>
				<div class="form-group">
					<select class="form-control" id="key" name="key">
						<option value="name">Name</option>
						<option value="phone">Phone</option>
						<option value="email">Email</option>
						<option value="regDate">RegDate</option>
					</select>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="value" name="value"
						required>
				</div>
				<button type="submit" class="btn btn-default">Search</button>
			</form>


		</div>
	</div>



	<!-- 강의 가능 과목 Modal -->
	<div class="modal fade" id="tlist-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">강의 가능 과목</h5>
				</div>
				<div class="modal-body">
				
				
					<h4 style="text-align:center;"></h4>
				
					
					<hr>
					
					<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>과목ID</th>
						<th>과목명</th>
					</tr>
				</thead>
				<tbody>
				
				
				</tbody>
			</table>
				
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>

		</div>
	</div>



	<!-- 강사 계정 등록 Modal -->
	<div class="modal fade" id="t-insert-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">강사 계정 등록</h5>
				</div>
				
				<div class="modal-body">
				<form role="form" action="${pageContext.request.contextPath}/admin_teacherinsert.do" method="post">
					
						<div class="form-group">
							<label class="control-label col-sm-3 m10">이름</label>
							<div class="col-sm-9 m10">
								<input class="form-control" id="name" name ="name" type="text">
							</div>
							<label class="control-label col-sm-3 m10">전화번호</label>
							<div class="col-sm-9 m10">
								<input class="form-control" id="phone" name ="phone" type="text">
							</div>
							<label class="control-label col-sm-3 m10">주민번호</label>
							<div class="col-sm-9 m10">
								<input class="form-control" id="ssn" name="ssn" type="text" value ="">
							</div>
							<label class="control-label col-sm-3 m10">강의 가능 과목</label>
							<div class="col-sm-9 m10">
								<c:forEach var = "t" items = "${temp}">
								<div class="checkbox">
								<label><input type="checkbox" name="subject_id" value="${t.id}">${t.name }</label>
								</div>
								</c:forEach>
								
							</div>
						</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-default">등록</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					</div>
					</form>
				</div>
				
				
			</div>

		</div>
	</div>


	<!-- 강사 계정 수정 Modal -->
	<div class="modal fade" id="t-mod-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">강사 계정 수정</h5>
				</div>
				<form role="form" action="${pageContext.request.contextPath}/admin_teacherupdate.do" method="post">
				<div class="modal-body">
				
					
						<div class="form-group">
							<label class="control-label col-sm-3">아이디</label>
							<div class="col-sm-9">
								<input class="form-control" id="teacher_id" name="teacher_id" type="text" value="" readonly>
							</div>
							<label class="control-label col-sm-3 m10">이름</label>
							<div class="col-sm-9 m10">
								<input class="form-control" id="name" name = "name" type="text" value="">
							</div>
							<label class="control-label col-sm-3 m10">전화번호</label>
							<div class="col-sm-9 m10">
								<input class="form-control" id="phone" name = "phone" type="text" value="">
							</div>
							<label class="control-label col-sm-3 m10">주민번호</label>
							<div class="col-sm-9 m10">
								<input class="form-control" id="ssn" name = "ssn" type="text" value="">
							</div>
							<label class="control-label col-sm-3 m10">강의 가능 과목</label>
							<div class="col-sm-9 m10" id="sublist">
							</div>
							</div> 
					
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-default">수정</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
				</form>
			</div>

		</div>
	</div>




	<!-- 강사 삭제 Modal -->
	<div class="modal fade" id="t-del-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<form role="form" action="${pageContext.request.contextPath}/admin_teacherdelete.do" method="post">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title" id ="title"></h5>
				</div>
				<div class="modal-body">

					<h4 style="text-align:center;font-weight:bold;" id="info"></h4>
					
					<h4 style="text-align:center;"></h4>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-default">삭제</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
				</form>
			</div>
			

		</div>
	</div>



</body>
</html>