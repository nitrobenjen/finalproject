<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style.css">

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
		
		//과목 삭제
		 $(".btndelete").on("click", function() {

				var subject_id = $(this).parents("tr").find("td:eq(0)").text();

				var subject_name = $(this).parents("tr").find("td:eq(1)").text();
				
				console.log(subject_id);
				console.log(subject_name);
				
				
				$(".delForm #subject_id").val(subject_id);
				$("#sub-del-Modal").find("input#subject_id").val(subject_id); 
				 $("#sub-del-Modal").find("h5#subject_id").text(subject_id);
				$("#sub-del-Modal").find("h4#subject_name").text(subject_name);

			
			        
			    }); 
		
		

			//과목 수정
			$(".btnupdate").on("click", function() {

				var subject_id = $(this).parents("tr").find("td:eq(0)").text();
				var subject_name = $(this).parents("tr").find("td:eq(1)").text();
				
				console.log(subject_id);
				console.log(subject_name);
				
				
				$(".upForm #subject_id2").val(subject_id);
				//$(".upForm #course_name").val(course_name);
	
				$(".upForm #focusedInput").attr("placeholder", subject_name)			
				$("#sub-mod-Modal").find("input#subject_name").val(subject_name);
				$("#sub-mod-Modal").find("h5#subject_id").text(subject_id);
				$("#sub-mod-Modal").find("h4#subject_name").text(subject_name);
				

			});

	});
</script>
</head>
<body>


	<div id="main">
		<div class="title">
			<img src="${pageContext.request.contextPath}/img/sist_logo.png"
				width="300px">
			<div class="login">관리자 님 │ 로그아웃</div>
		</div>
		<div id="menu">
			<div class="menu">
				<ul class="nav nav-pills nav-justified">
					<li class="active"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">기초 정보 관리</a>
						<ul class="dropdown-menu">
							<li><a
								href="${pageContext.request.contextPath}/adminmain.do">과정명</a></li>
							<li><a href="${pageContext.request.contextPath}/admin102.do">과목명</a></li>
							<li><a href="${pageContext.request.contextPath}/admin103.do">교재명</a></li>
							<li><a href="${pageContext.request.contextPath}/admin104.do">강의실명</a></li>
						</ul></li>
						<li class=""><a href="${pageContext.request.contextPath}/admin201.do">강사 계정 관리</a></li>
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

		<div class="content">


			<h3>기초 정보 관리 > 과목명</h3>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>과목ID</th>
						<th>과목명</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="in" items="${infosubject}">
						<tr>
							<td>${in.subject_id }</td>
							<td>${in.subject_name }</td>
							<td><button type="button" class="btn btn-default btnupdate"
									data-toggle="modal" data-target="#sub-mod-Modal" value="${in.subject_id}">수정</button></td>
							<td><button type="button" class="btn btn-default btndelete"
									data-toggle="modal" data-target="#sub-del-Modal" value="${in.subject_id}">삭제</button></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>


			<form class="form-inline" method="post" style="text-align: center;">
				<button type="button" style="float: left;" class="btn btn-default"
					data-toggle="modal" data-target="#sub-insert-Modal">등록</button>
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





	<!-- 과목 입력 Modal -->
	<div class="modal fade" id="sub-insert-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">과목명 등록</h5>
				</div>
				<form action="${pageContext.request.contextPath}/adminsubjectinsert.do" method="post" class="form-horizontal">
					<div class="modal-body">

						<h4 style="text-align: center;">새로운 과목명을 입력해 주세요.</h4>

						<hr>


						<div class="form-group">
							<label class="control-label col-sm-2">과목명</label>
							<div class="col-sm-10">
								<input class="form-control" name="subject_name" id="focusedInput" type="text">
							</div>
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





	<!-- 과목 수정 Modal -->
	<div class="modal fade" id="sub-mod-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title" id="subject_id"></h5>과목명 수정
				</div>
				<form action="${pageContext.request.contextPath}/adminsubjectupdate.do"
					method="post" class="form-horizontal upForm">
					<div class="modal-body">

						<h4 id="subject_name" style="text-align: center; font-weight: bold;"></h4>

						<hr>

						<div class="form-group">
							<label class="control-label col-sm-2">과목명</label>
							<div class="col-sm-10">
								<input class="form-control" id="focusedInput" type="text" name="subject_name">
							</div>
						</div>

					</div>
					<div class="modal-footer">
					<input type="hidden" id="subject_id2" name="subject_id">	
						<button type="submit" class="btn btn-default">수정</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					</div>
				</form>
			</div>

		</div>
	</div>



	<!-- 과목 삭제 Modal -->
	<div class="modal fade" id="sub-del-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title" id="subject_id" ></h5>과정명 삭제
				</div>
				<div class="modal-body">

					<h4 id="subject_name" style="text-align: center; font-weight: bold;"></h4>

					<h4 style="text-align: center;">과목을 삭제하시겠습니까?</h4>
				</div>
				<div class="modal-footer">
				<form
					action="${pageContext.request.contextPath}/adminsubjectdelete.do"
					method="post" class="form-horizontal delForm">
					<input type="hidden" id="subject_id" name="subject_id">
					<button type="submit" class="btn btn-default">삭제</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					</form>
				</div>
			</div>

		</div>
	</div>



</body>
</html>