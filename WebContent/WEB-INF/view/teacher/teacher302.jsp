<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	$(document).ready(function() {
		
		$(".addBtn").on("click", function(){
			var student_id = $(this).parents("tr").find("td:eq(0)").text();
			var student_name = $(this).parents("tr").find("td:eq(1)").text();
			console.log(student_id);
			console.log(student_name);
			$(".addForm #student_id").val(student_id);
			$(".addForm #student_name").val(student_name);
		});
		
		$(".upBtn").on("click", function(){
			var student_id = $(this).parents("tr").find("td:eq(0)").text();
			var student_name = $(this).parents("tr").find("td:eq(1)").text();
			var g_chulsuk = $(this).parents("tr").find("td:eq(5)").text();
			var g_filki = $(this).parents("tr").find("td:eq(6)").text();
			var g_silki = $(this).parents("tr").find("td:eq(7)").text();
			console.log(student_id);
			console.log(student_name);
			$(".upForm #student_id").val(student_id);
			$(".upForm #name_").val(student_name);
			$(".upForm #g_chulsuk").val(g_chulsuk);
			$(".upForm #g_filki").val(g_filki);
			$(".upForm #g_silki").val(g_silki);
		});
	});
</script>
</head>
<body>


	<div id="main">
		<div class="title">
			<img src="${pageContext.request.contextPath}/img/sist_logo.png"
				width="300px">
			<div class="login">
				<a href="${pageContext.request.contextPath}/teachinfo.do"">[강사]${teachinfo.teacher_name}</a> 님 │ <a href="${pageContext.request.contextPath}/logout.do">로그아웃</a>
			</div>
		</div>
		<div id="menu">
			<div class="menu">
				<ul class="nav nav-pills nav-justified">
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="#">강의스케쥴</a>
						<ul class="dropdown-menu">
							<li><a href="${pageContext.request.contextPath}/teachschedulelist.do">강의
									예정</a></li>
							<li><a href="${pageContext.request.contextPath}/teachinsub.do">강의 중</a></li>
							<li><a href="${pageContext.request.contextPath}/teachendsub.do">강의
									종료</a></li>
						</ul></li>
					<li><a href="${pageContext.request.contextPath}/teacher201.do">배점관리</a></li>
					<li class="active"><a
						href="${pageContext.request.contextPath}/teacher301.do">성적관리</a></li>
				</ul>
			</div>
		</div>

		<div class="content">


			<h3>성적관리</h3>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>과목ID</th>
						<th>과목명</th>
						<th style="width: 90px;">과목기간</th>
						<th>과정명</th>
						<th style="width: 90px;">과정기간</th>
						<th style="width: 85px;">강의실</th>
						<th>교재명</th>
						<th>출결배점</th>
						<th>필기배점</th>
						<th>실기배점</th>
						<th>시험일</th>
						<th>시험문제</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty bajuminfo}">
							<tr>
								<td colspan="13" style="text-align: center">- 관리 가능한 과목이
									없습니다. -</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="g" items="${bajuminfo}">
								
								<tr>
									<td>${g.open_sub_id}</td>
									<td>${g.subject_name}</td>
									<td style="text-align: center">${g.sub_start_day}<br>~<br>${g.sub_end_day}</td>
									<td>${g.course_name}</td>
									<td style="text-align: center">${g.course_start_day}<br>~<br>${g.course_end_day }</td>
									<td>${g.class_name}</td>
									<td>${g.book_name}</td>
									<td>${g.b_chulsuk}</td>
									<td>${g.b_filki}</td>
									<td>${g.b_silki}</td>
									<td>${g.test_date}</td>
									<td>${g.test_munje}</td>
								</tr>
								<c:set var="subject_name" value="${g.subject_name}"/>
								<c:set var="sub_day" value="${g.sub_start_day}~${g.sub_end_day}" />
								<c:set var="b_chulsuk" value="${g.b_chulsuk}"/>
								<c:set var="b_filki" value="${g.b_filki}"/>
								<c:set var="b_silki" value="${g.b_silki}"/>
								<c:set var = "open_sub_id" value="${g.open_sub_id}" />
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>수강생ID</th>
						<th>이름</th>
						<th>전화번호</th>
						<th>등록일</th>
						<th>수료여부</th>
						<th>출석점수</th>
						<th>필기점수</th>
						<th>실기점수</th>
						<th>등록</th>
						<th>수정</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty studentList}">
							<tr>
								<td colspan="13" style="text-align: center">- 관리 가능한 과목이
									없습니다. -</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="s" items="${studentList}">
								<tr>
									<td>${s.student_id}</td>
									<td>${s.student_name}</td>
									<td>${s.student_phone}</td>
									<td>${s.student_regdate}</td>
									<td>${s.pass_drop}(${s.finish_day})</td>
									<td>${s.g_chulsuk}</td>
									<td>${s.g_filki}</td>
									<td>${s.g_silki}</td>
									<td><button type="button" class="btn btn-default addBtn"
											data-toggle="modal" data-target="#grade-insert-Modal" value="${s.student_id}" ${(s.g_chulsuk eq '-')?'':'disabled'}>등록</button></td>
									<td><button type="button" class="btn btn-default upBtn"
											data-toggle="modal" data-target="#grade-mod-Modal" value="${s.student_id}" ${(s.g_chulsuk eq '-')?'disabled':''}>수정</button></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>

				</tbody>
			</table>

			<div style="float: left; position: absolute">
				<button type="button" class="btn btn-default btn-sm">
					TotalCount <span class="badge" id="totalCount">25</span>
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


	<!-- 성적 등록 Modal -->
	<div class="modal fade" id="grade-insert-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">성적관리 > 등록</h5>
				</div>
				<form class="form-horizontal addForm" method="post" action="${pageContext.request.contextPath}/teachgradeAdd.do">
					<div class="modal-body">
						
						<h4>
							${subject_name}(${sub_day})
							</td>
						</h4>
						<h5 style="text-align: center;">출결배점 : ${b_chulsuk} / 필기배점 : ${b_filki} / 실기배점
							: ${b_silki}</h5>

						<hr>
						<input type="hidden" name="open_sub_id" value="${open_sub_id}">
						<div class="form-group">
							<label class="control-label col-sm-3">수강생ID</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="student_id"  name="student_id" value="" readonly>
							</div>
							<label class="control-label col-sm-3 m10">이름</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control" id="student_name"  name="name_" value="" readonly>
							</div>
							<label class="control-label col-sm-3 m10">출결점수</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control" style="width: 100px;"  name="g_chulsuk" placeholder="MAX ${b_chulsuk}">
							</div>
							<label class="control-label col-sm-3 m10">필기점수</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control" style="width: 100px;"  name="g_filki" placeholder="MAX ${b_filki}">
							</div>
							<label class="control-label col-sm-3 m10">실기점수</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control" style="width: 100px;" name="g_silki"  placeholder="MAX ${b_silki}">
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


	<!-- 성적 수정 Modal -->
	<div class="modal fade" id="grade-mod-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">성적관리 > 수정</h5>
				</div>
				<form class="form-horizontal upForm" method="post" action="${pageContext.request.contextPath}/teachgradeUp.do">
				<input type="hidden" name="open_sub_id" value="${open_sub_id}">
					<div class="modal-body">

						<h4>
							${subject_name}(${sub_day})
							</td>
						</h4>
						<h5 style="text-align: center;">출결배점 : ${b_chulsuk} / 필기배점 : ${b_filki} / 실기배점
							: ${b_silki}</h5>

						<hr>
						<input type="hidden" name="open_sub_id" value="">
						<div class="form-group">
							<label class="control-label col-sm-3">수강생ID</label>
							<div class="col-sm-9">
								<input class="form-control" id="student_id" type="text" name="student_id"
									value="" readonly>
							</div>
							<label class="control-label col-sm-3 m10">이름</label>
							<div class="col-sm-9 m10">
								<input class="form-control" id="name_" type="text" name="name_"
									value="" readonly>
							</div>
							<label class="control-label col-sm-3 m10">출결점수</label>
							<div class="col-sm-9 m10">
								<input class="form-control" style="width: 100px;" type="text" id="g_chulsuk" name="g_chulsuk"
									value="">
							</div>
							<label class="control-label col-sm-3 m10">필기점수</label>
							<div class="col-sm-9 m10">
								<input class="form-control" style="width: 100px;" type="text" id="g_filki" name="g_filki"
									value="">
							</div>
							<label class="control-label col-sm-3 m10">실기점수</label>
							<div class="col-sm-9 m10">
								<input class="form-control" style="width: 100px;" type="text" id="g_silki" name="g_silki"
									value="">
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

</body>
</html>