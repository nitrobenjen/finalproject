<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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


<!-- jQuery UI 사용 환경 설정 -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- jQuery UI 사용 환경 설정 -->
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>


<style>
</style>

<script>
	$(document).ready(
			function() {

				$(".btndelete").on(
						"click",
						function() {

							var student_id = $(this).parents("tr").find(
									"td:eq(0)").text();
							var student_name = $(this).parents("tr").find(
									"td:eq(1)").text();
							var student_phone = $(this).parents("tr").find(
									"td:eq(3)").text();

							$(".delForm #student_id").val(student_id);
							$("#student-del-Modal").find("input#student_id")
									.val(student_id);

							$("#student-del-Modal").find("span#id").text(
									student_id);
							$("#student-del-Modal").find("span#name").text(
									student_name);
							$("#student-del-Modal").find("span#phone").text(
									student_phone);

						});

				$(".btnmodify").on(
						"click",
						function() {

							var student_id = $(this).parents("tr").find(
									"td:eq(0)").text();
							var student_name = $(this).parents("tr").find(
									"td:eq(1)").text();
							var student_ssn = $(this).parents("tr").find(
									"td:eq(2)").text();
							var student_phone = $(this).parents("tr").find(
									"td:eq(3)").text();

							$(".upForm #student_id").val(student_id);
							$("#student-mod-Modal").find("input#student_id")
									.val(student_id);
							$("#student-mod-Modal").find("input#student_name")
									.val(student_name);
							$("#student-mod-Modal").find("input#student_ssn")
									.val(student_ssn);
							$("#student-mod-Modal").find("input#student_phone")
									.val(student_phone);

						});

				$(".btnculist").on(
						"click",
						function() {

							var student_id = $(this).parents("tr").find(
									"td:eq(0)").text();
							var student_name = $(this).parents("tr").find(
									"td:eq(1)").text();
							var student_ssn = $(this).parents("tr").find(
									"td:eq(2)").text();

							$(".upForm #student_id").val(student_id);
							$("#student-mod-Modal").find("input#student_id")
									.val(student_id);
							$("#student-mod-Modal").find("input#student_name")
									.val(student_name);
							$("#student-mod-Modal").find("input#student_ssn")
									.val(student_ssn);
							$("#student-mod-Modal").find("input#student_phone")
									.val(student_phone);

						});
				
				$(".btnculist").on("click", function() {
                    var student_id = $(this).val();
                    $.get("adminteacher_subject.it", {
                    	student_id : student_id
                    }, function(data) {
                       $("div#demo").html(data);
                    });
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
					<li class=""><a class="dropdown-toggle"
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
					<li class="active"><a
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


			<h3>수강생 관리 > 수강생 정보</h3>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>수강생ID</th>
						<th>이름</th>
						<th>주민번호</th>
						<th>전화번호</th>
						<th>등록일</th>
						<th>과정등록</th>
						<th>수강이력</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="g" items="${studentlist}">
						<form action="${pageContext.request.contextPath}/admin402.do"
							method="post">
						<input type="hidden" id="id" name="student_id" value="${g.id}">						
						<input type="hidden" id="name" name="student_name" value="${g.name}">
						<input type="hidden" id="phone" name="student_phone" value="${g.phone}">
							
						<tr>
							<td>${g.id}</td>
							<td>${g.name}</td>
							<td>${g.ssn}</td>
							<td>${g.phone}</td>
							<td>${g.regDate}</td>
							<td><button type="button" class="btn btn-default"
									data-toggle="modal" data-target="#student-ocu-Modal">등록</button></td>
							<td><button type="submit"
									class="btn btn-default btnculist btn-sm"
									${(g.count==0)?'disabled':''} value="${g.id}">
									<span class="badge" id="Count">${g.count}</span> 보기
								</button></td>
							<td><button type="button" class="btn btn-default btnmodify"
									data-toggle="modal" data-target="#student-mod-Modal">수정</button></td>
							<td><button type="button" class="btn btn-default btndelete"
									data-toggle="modal" data-target="#student-del-Modal"
									id="student_del" ${(g.count>0)?'disabled':''} value="${g.id}">삭제</button></td>
						</tr>
						</form>
					</c:forEach>

				</tbody>
			</table>

			<div style="position: absolute;">
				<button type="button" class="btn btn-default" data-toggle="modal"
					data-target="#student-insert-Modal">수강생 등록</button>
				<button type="button" class="btn btn-default btn-sm">
					TotalCount <span class="badge" id="totalCount">${totalcount}</span>
				</button>
			</div>
			<div style="text-align: center;">

				<ul class="pagination" style="margin: 0px 0px 20px 0px;">
					<li class="active"><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
				</ul>
			</div>


			<form class="form-inline" method="post" style="text-align: center;">
				<div class="form-group">
					<select class="form-control" id="key" name="key">
						<option value="student_id">수강생ID</option>
						<option value="student_name">이름</option>
						<option value="student_regdate">등록일</option>
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


	<!-- 수강생 등록 Modal -->
	<div class="modal fade" id="student-insert-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">수강생 등록</h5>
				</div>
				<form class="form-horizontal" role="form"
					action="${pageContext.request.contextPath}/admin401insert.do"
					method="post">
					<div class="modal-body">


						<div class="form-group">
							<label class="control-label col-sm-3">수강생 이름</label>
							<div class="col-sm-9">
								<input class="form-control" id="name" name="student_name"
									type="text">
							</div>
							<label class="control-label col-sm-3 m10">주민등록번호</label>
							<div class="col-sm-9 m10">
								<input class="form-control" id="ssn" name="student_ssn"
									type="text">
							</div>
							<label class="control-label col-sm-3 m10">전화번호</label>
							<div class="col-sm-9 m10">
								<input class="form-control" id="phone" name="student_phone"
									type="text">
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


	<!-- 수강 과정 등록 Modal -->
	<div class="modal fade" id="student-ocu-Modal" role="dialog">
		<div class="modal-dialog modal-lg">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">수강 과정 등록</h5>
				</div>
				<form>
					<div class="modal-body">

						<h3 style="text-align: center;">${g.id}/ ${g.name} /
							${g.phone}</h3>

						<hr>

						<table class="table table-striped table-bordered ocu">
							<thead>
								<tr>
									<th>개설과정ID</th>
									<th>과정명</th>
									<th>강의실명</th>
									<th>정원</th>
									<th>과정시작일</th>
									<th>과정종료일</th>
									<th>과목수</th>
									<th>선택</th>
								</tr>
							</thead>
							<tbody>
								<!-- <tr>
									<td>OCU001</td>
									<td>Framework을 활용한 빅데이터 개발자 과정</td>
									<td>제2강의실</td>
									<td>1/25</td>
									<td>16-09-01</td>
									<td>17-02-01</td>
									<td>2</td>
									<td><input type="radio" name="optradio"></td>
								</tr> -->
								<%--  								<c:forEach var="s" items="oculist">
									<tr>
										<td>${s.open_course_id}</td>
										<td>${s.course_name}</td>
										<td>${s.class_name}</td>
										<td>${s.student_count} / ${s.jungwon}</td>
										<td>${s.course_start_day}</td>
										<td>${s.course_end_day}</td>
										<td>${s.subject_count}</td>
										<td><input type="radio" name="optradio"></td>
									</tr>
								</c:forEach> --%>
							</tbody>
						</table>

					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-default">등록</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					</div>
				</form>
			</div>

		</div>
	</div>




	<!-- 수강생 수정 Modal -->
	<div class="modal fade" id="student-mod-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">수강생 수정</h5>
				</div>
				<form class="form-horizontal upForm" role="form"
					action="${pageContext.request.contextPath}/admin401up.do"
					method="post">
					<div class="modal-body">


						<div class="form-group">
							<label class="control-label col-sm-3">수강생 ID</label>
							<div class="col-sm-9">
								<input class="form-control" id="student_id" name="student_id"
									type="text" readonly>
							</div>
							<label class="control-label col-sm-3 m10">수강생 이름</label>
							<div class="col-sm-9 m10">
								<input class="form-control" id="student_name"
									name="student_name" type="text">
							</div>
							<label class="control-label col-sm-3 m10">주민등록번호</label>
							<div class="col-sm-9 m10">
								<input class="form-control" id="student_ssn" name="student_ssn"
									type="text">
							</div>
							<label class="control-label col-sm-3 m10">전화번호</label>
							<div class="col-sm-9 m10">
								<input class="form-control" id="student_phone"
									name="student_phone" type="text">
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


	<!-- 수강생 삭제 Modal -->
	<div class="modal fade" id="student-del-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">수강생 삭제</h5>
				</div>

				<form class="form-horizontal delForm" role="form"
					action="${pageContext.request.contextPath}/admin401del.do"
					method="post">
					<div class="modal-body">

						<h4 style="text-align: center; font-weight: bold;">
							<span id="id"></span> / <span id="name"></span> / <span
								id="phone"></span>
						</h4>
						<h4 style="text-align: center;">수강생을 삭제하시겠습니까?</h4>
						<input type="hidden" id="student_id" name="student_id">
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