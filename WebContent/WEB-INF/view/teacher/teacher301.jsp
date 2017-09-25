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
		$("subSelectbtn").on("click", function(){
			
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
						<th>선택</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty gradelist}">
							<tr>
								<td colspan="13" style="text-align: center">- 관리 가능한 과목이
									없습니다. -</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="g" items="${gradelist}">
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
									<td><a href="${pageContext.request.contextPath}/teacher302.do?open_sub_id=${g.open_sub_id}"><button type="button" class="btn btn-default subSelectbtn">선택</button></a></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>


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


</body>
</html>