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
		
		
		//이미지 출력
		$("a.img").on("click", function(){
			
			//var book_name = $(this).text();
			
			
		var book_name = $(this).parents("tr").find("td:eq(3)").text();
			
	
			
			$("#bookImgModal").find("h4#book_name").text(book_name);

			
			var result;

				
			$.ajax({
				url : "studentbook.do",
				data : {book_name : book_name},
				
				success : function(data) {
					
					 result = data;
					 
					 console.log(result);
					 
					 $("#bookImgModal img").attr("src",result);
					$("#bookImgModal").modal();

				},
				async : false
			});	 
			return result;
			
		 	
			//$("#bookImgModal #book_name").html(book_name);
			//$("#bookImgModal").modal();
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
				<a href="${pageContext.request.contextPath}/studentinfo.do">[수강생]${studentinfo.student_name}</a> 님
				│ 로그아웃
			</div>
		</div>
		<div id="menu">
			<div class="menu">
				<ul class="nav nav-pills nav-justified">
					<li><a href="${pageContext.request.contextPath}/studentmain.do">성적조회</a></li>
				</ul>
			</div>
		</div>

		<div class="content">

			<h3>성적조회 > ${course_name}</h3>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>과정ID</th>
						<th>과정명</th>
						<th>과정기간</th>
						<th>강의실</th>
					</tr>
				</thead>
				<tbody>
					
					 <tr>
						<td>${open_course_id }</td>
						<td>${course_name}</td>
						<td>${course_start_day}~${course_end_day}</td>
						<td>${class_name}</td>
					</tr>
				</tbody>
			</table>
			<hr>
			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>과목ID</th>
						<th>과목명</th>
						<th>과목기간</th>
						<th>교재명</th>
						<th>강사명</th>
						<th>출결/배점</th>
						<th>필기/배점</th>
						<th>실기/배점</th>
						<th>총점</th>
						<th>시험날짜</th>
						<th>시험문제</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="sts" items="${stusub}">
						<tr>
							<td>${sts.open_sub_id }</td>
							<td>${sts.subject_name }</td>
							<td>${sts.sub_start_day}~${sts.sub_end_day}</td>
							<td><a href="#" class="img" src="">${sts.book_name }</a></td>
							<td>${sts.teacher_name }</td>
							<td>${sts.g_chulsuk }/${sts.b_chulsuk }</td>
							<td>${sts.g_filki }/${sts.b_filki }</td>
							<td>${sts.g_silki }/${sts.b_silki }</td>
							<td>${sts.total }</td>
							<td>${sts.test_date }</td>

							<td><button type="button" class="btn btn-default">다운</button></td>
						</tr>
					</c:forEach>
					
				</tbody>
			</table>


		</div>
	</div>
	
	<div id="bookImgModal" class="modal fade" role="dialog">
		<div class="modal-dialog modal-sm">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="book_name" ></h4>
				</div>
				<div class="modal-body">
					<div style="text-align: center;">
					
						<img src="" style="width: 100%;">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>
	


</body>
</html>