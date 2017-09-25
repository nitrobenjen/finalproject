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
	$(document).ready(function() {
		$("#ocufinishdate").datepicker({
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd"
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


			<h3>수강생 관리 > 수강 이력</h3>

			<h4 style="text-align: center; font-weight: bold;">${student_id} / ${student_name}
				/ ${student_phone}</h4>

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
						<th>수료여부</th>
					</tr>
				</thead>
				<tbody>
					<!-- <tr>
						<td>OCU001</td>
						<td>Framework을 활용한 빅데이터 개발자 과정</td>
						<td>제2강의실</td>
						<td>25</td>
						<td>16-09-01</td>
						<td>17-02-01</td>
						<td>수료예정 <button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#ocu-end-Modal">입력</button></td>
					</tr>
					<tr>
						<td>OCU002</td>
						<td>JAVA를 활용한 사물인터넷(IOT) 응용SW 개발자</td>
						<td>제4강의실</td>
						<td>40</td>
						<td>17-01-17</td>
						<td>17-04-17</td>
						<td>수료 <button type="button" class="btn btn-default"
								data-toggle="modal" data-target="#ocu-end-Modal">입력</button></td>
					</tr> -->
					<c:forEach var="g" items="${studentoculist}">
						
						<tr>
							<td>${g.open_course_id}</td>
							<td>${g.course_name}</td>
							<td>${g.class_name}</td>
							<td>${g.jungwon}</td>
							<td>${g.course_start_day}</td>
							<td>${g.course_end_day}</td>
							<td><c:set var="finish_day" scope="session"
									value="${g.finish_day}" /> <c:choose>
									
									<c:when test="${finish_day == null}">
										수료예정
									</c:when>

									<%-- if문의 else 역할 --%>
									<c:otherwise>
										수료(${g.finish_day})
									</c:otherwise>
								</c:choose> <button type="button" class="btn btn-default"
									data-toggle="modal" data-target="#ocu-end-Modal">입력</button></td>
						</tr>
						
					</c:forEach>
				</tbody>
			</table>


		</div>
	</div>


	<!-- 개설 과정 삭제 Modal -->
	<div class="modal fade" id="ocu-end-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">수료 여부 입력</h5>
				</div>
				<div class="modal-body">

					<h4 style="text-align: center; font-weight: bold;">OCU001 /
						Framework을 활용한 빅데이터 개발자 과정</h4>
					<h5 style="text-align: center;">16-09-01 ~ 17-02-01</h5>
					<br>
					<h4 style="text-align: center;">STU002 / 이순신 / 010-4758-6532</h4>

					<hr>
					<form class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-3 m10">과정 수료일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control" id="ocufinishdate"
									name="ocufinishdate" placeholder="과정 수료일(YYYY-MM-DD)"
									required="required">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-default">등록</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>

		</div>
	</div>



</body>
</html>