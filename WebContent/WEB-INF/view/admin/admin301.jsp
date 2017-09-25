<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">


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

	//개설과목 보기 버튼
	$(document).ready(function() {
		$(document).on("click",".btn-sub",function() {
			$(location).attr('href',"${pageContext.request.contextPath}/adminOpenSubList.do?open_course_id="+ $(this).val());
		});
		//개설과정을 수강하는 수강생 보기
		$(document).on("click",".btn-stu",function() {
			$(location).attr('href',"${pageContext.request.contextPath}/admin3_3_StudentList.do?open_course_id="+ $(this).val());
		});
		
		//새 개설과정 등록 버튼 
		$(document).on("click", ".btn-insert", function() {
			$("#course_id").empty();
			$("#class_id").empty();
			$.ajax({
				url : "admin3_1_CourseAndClassListToJSON.do",
				success : function(data) {
					//console.log(data);
					var temp=JSON.parse(data);
					console.log(temp.courseList);
					$.each(temp.courseList,function(idx,item){
						//<option>CA001 / 제1강의실 / 30</option>
						$("#course_id").append("<option value='"+item.id+"'>"+item.id+"/"+item.name+"</option>");
					});
					$.each(temp.classList,function(idx,item){
						//<option>CA001 / 제1강의실 / 30</option>
						$("#class_id").append("<option value='"+item.id+"'>"+item.id+"/"+item.name+"/"+item.jungwon+"</option>");
					});
				}
			});
			$("#ocu-insert-Modal").modal();
		});
		//개설과정 삭제 모달창 팝업 버튼
		$(document).on("click", ".btn-delete", function() {
			//OCU001 / Framework을 활용한 빅데이터 개발자 과정
			var open_course_id=$(this).parents("tr").find("td:nth-child(1)").text();
			
			var course_name=$(this).parents("tr").find("td:nth-child(2)").text();
			var course_start_day=$(this).parents("tr").find("td:nth-child(6)").text();
			var course_end_day=$(this).parents("tr").find("td:nth-child(7)").text();
			$("#ocu-del-Modal .modal-body h4:nth-child(1)").html(open_course_id+"/"+course_name);
			$("#ocu-del-Modal .modal-body h5").html(course_start_day+"~"+course_end_day);
			$("#ocu-del-Modal #open_course_id").val($(this).val());
			$("#ocu-del-Modal").modal();
			
		});
	
		//개설 과정 수정 버튼
		$(document).on("click", ".btn-modify", function() {
			$("#ocu-mod-Modal .classList").empty();
			var open_course_id=$(this).parents("tr").find("td:nth-child(1)").text();
			var course_name=$(this).parents("tr").find("td:nth-child(2)").text();
			var course_start_day=$(this).parents("tr").find("td:nth-child(6)").text();
			var course_end_day=$(this).parents("tr").find("td:nth-child(7)").text();
			$("#ocu-mod-Modal .modal-body h4:nth-child(1)").html(open_course_id+"/"+course_name);
			$("#ocu-mod-Modal .modal-body h5").html(course_start_day+"~"+course_end_day);
			$("#ocu-mod-Modal .ocustartdate").val(course_start_day);
			$("#ocu-mod-Modal .ocuenddate").val(course_end_day);
			$("#ocu-mod-Modal #open_course_id").val($(this).val());
			$.ajax({
				url : "admin3_1_CourseAndClassListToJSON.do",
				success : function(data) {
					//console.log(data);
					var temp=JSON.parse(data);
					console.log(temp.courseList);
					$.each(temp.classList,function(idx,item){
						//<option>CA001 / 제1강의실 / 30</option>
						$("#ocu-mod-Modal .classList").append("<option value='"+item.id+"'>"+item.id+"/"+item.name+"/"+item.jungwon+"</option>");
					});
				}
			});
			$("#ocu-mod-Modal").modal();			
		});
		
		//개설과정 중 시작 일자 등록시 예외 처리
		$(".ocustartdate").datepicker({
			changeMonth : true,
			changeYear : true,
			minDate : new Date(),
			//maxDate: "2017-09-14",
			dateFormat : "yy-mm-dd",
			onClose : function() {
				startDate = $(this).val();
				console.log("startDate", startDate);
			}
		});
		//개설과정 중 마지막일자 등록시 예외 처리
		$(".ocuenddate").datepicker(
						{
							beforeShow : function() {
								console.log("startDate",$(".ocustartdate").val());
								$(".ocuenddate").datepicker("option","minDate",$(".ocustartdate").val());
							},
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
			<img src="${pageContext.request.contextPath}/img/sist_logo.png" width="300px">
			<div class="login">
				관리자 님 │ 로그아웃
			</div>
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
					<li class="active"><a href="${pageContext.request.contextPath}/adminOpenCourseList.do">개설 과정/과목
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
			<h3>개설 과정 관리</h3>
			<hr>
			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>개설과정ID</th>
						<th>과정명</th>
						<th>강의실</th>
						<th>정원</th>
						<th>수강인원</th>
						<th>과정 시작일</th>
						<th>과정 종료일</th>
						<th>개설 과목</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="openCourse" items="${openCourseList}">
						<tr>
							<td>${openCourse.open_course_id}</td>
							<td>${openCourse.course_name}</td>
							<td>${openCourse.class_name}</td>
							<td>${openCourse.jungwon}</td>
							<td><button type="button" class="btn btn-default btn-stu"
									value="${openCourse.open_course_id}" ${openCourse.student_count==0?'disabled':''}>
									수강생수<span class="badge" id="studentCount">${openCourse.student_count}</span>
								</button></td>
							<td>${openCourse.course_start_day}</td>
							<td>${openCourse.course_end_day}</td>
							<td><button type="button" class="btn btn-default btn-sub"
									value="${openCourse.open_course_id}">
									개설과목수<span class="badge" id="subCount">${openCourse.subject_count}</span>
								</button></td>
							<td><button type="button" class="btn btn-default btn-modify" ${openCourse.subject_count==0?'':'disabled'} value="${openCourse.open_course_id}">수정</button></td>
							<td><button type="button" class="btn btn-default btn-delete"  ${openCourse.subject_count==0?openCourse.student_count==0?'':'disabled':'disabled'} value="${openCourse.open_course_id}">삭제</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
				<button type="button" class="btn btn-default btn-sm btn-modify">
						TotalCount <span class="badge" id="totalCount">${fn:length(openCourseList)}</span></button>
			<button type="button" style="float: left;position: absolute;" class="btn btn-default btn-insert">개설 과정 등록</button>

<!-- 			<form class="form-inline" method="post" style="text-align: center;"> -->
<!-- 				<div class="form-group"> -->
<!-- 					<select class="form-control" id="key" name="key"> -->
						<!-- <option value="name">Name</option>
						<option value="phone">Phone</option>
						<option value="email">Email</option>
						<option value="regDate">RegDate</option>
<!-- 					</select> -->
<!-- 				</div> -->
<!-- 				<div class="form-group"> -->
<!-- 					<input type="text" class="form-control" id="value" name="value" -->
<!-- 						required> -->
<!-- 				</div> -->
<!-- 				<button type="submit" class="btn btn-default">Search</button> -->
<!-- 			</form> -->


		</div>
	</div>


	<!-- 개설 과정 등록 Modal -->
	<div class="modal fade" id="ocu-insert-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">개설 과정 등록</h5>
				</div>
				<form class="form-horizontal" action="admin3_1_OpenCourseAdd.do">
				<div class="modal-body">
					
						<div class="form-group">
							<label class="control-label col-sm-3 m10">과정 ID</label>
							<div class="col-sm-9 m10">
								<select class="form-control" id="course_id" name="course_id">
							        <option>CU001 / 웹기반 빅데이터 분석 응용SW개발자</option>
							        <option>CU002 / 애플리케이션 성능 최적화 구현 양성과정</option>
							        <option>CU003 / Java&Python 기반 응용SW 개발자 양성</option>
							        <option>CU004 / JAVA를 활용한 사물인터넷(IOT) 응용SW 개발자</option>
							        <option>CU005 / Framework을 활용한 빅데이터 개발자 과정</option>
							        <option>CU006 / UI/UX 개발자 양성과정</option>
							        <option>CU007 / Java 보안 개발자 양성과정</option>
							    </select>
							</div>
							<label class="control-label col-sm-3 m10">강의실</label>
							<div class="col-sm-9 m10">
								<select class="form-control" id="class_id" name="class_id">
							        <option>CA001 / 제1강의실 / 30</option>
							        <option>CA002 / 제2강의실 / 25</option>
							        <option>CA003 / 제3강의실 / 30</option>
							        <option>CA004 / 제4강의실 / 40</option>
							        <option>CA005 / 제5강의실 / 25</option>
							    </select>
							</div>
							<label class="control-label col-sm-3 m10">과정 시작일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control ocustartdate" id="ocustartdate"
									name="ocustartdate" placeholder="과정 시작일(YYYY-MM-DD)"
									required="required">
							</div>
							<label class="control-label col-sm-3 m10">과정 종료일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control ocuenddate" id="ocuenddate"
									name="ocuenddate" placeholder="과정 종료일(YYYY-MM-DD)"
									required="required">
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


	<!-- 개설 과정 수정 Modal -->
	<div class="modal fade" id="ocu-mod-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">개설 과정 수정</h5>
				</div>
				<form class="form-horizontal" action="admin_3_1_OpenCourseUp.do">
				<div class="modal-body">
				
				<h4 style="text-align: center;">OCU001 / Framework을 활용한 빅데이터 개발자 과정</h4>
				<h5 style="text-align: center;">2016/09/01 ~ 2017/02/01</h5>
				
				<hr>
					
						<div class="form-group">
							<label class="control-label col-sm-3 m10">강의실</label>
							<div class="col-sm-9 m10">
								<select class="form-control classList" id="" name="class_id">
							        <option>CA001 / 제1강의실 / 30</option>
							        <option>CA002 / 제2강의실 / 25</option>
							        <option>CA003 / 제3강의실 / 30</option>
							        <option>CA004 / 제4강의실 / 40</option>
							        <option>CA005 / 제5강의실 / 25</option>
							    </select>
							</div>
							<label class="control-label col-sm-3 m10">과정 시작일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control ocustartdate" id="ocustartmoddate"
									name="ocustartdate" placeholder="과정 시작일(YYYY-MM-DD)" value="2016/09/01"
									required="required">
							</div>
							<label class="control-label col-sm-3 m10">과정 종료일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control ocuenddate" id="ocuendmoddate"
									name="ocuenddate" placeholder="과정 종료일(YYYY-MM-DD)" value="2017/02/01"
									required="required">
							</div>
						</div>
					
				</div>
				<div class="modal-footer">
				<input type="hidden" id="open_course_id" name="open_course_id">
					<button type="submit" class="btn btn-default">수정</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
				</form>
			</div>

		</div>
	</div>

	<!-- 개설 과정 삭제 Modal -->
	<div class="modal fade" id="ocu-del-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">개설 과정 삭제</h5>
				</div>

				<div class="modal-body">

					<h4 style="text-align: center;">OCU001 / 웹기반 빅데이터 분석 응용SW개발자</h4>
					<h5 style="text-align: center;">2016/09/01 ~ 2017/02/01</h5>
					<br>
					<h4 style="text-align: center;">개설 과정을 삭제하시겠습니까?</h4>
				</div>
				<div class="modal-footer">
					<form action="admin_3_1_OpenCourseDel.do">
					<input type="hidden" id="open_course_id" name="open_course_id">
						<button type="submit" class="btn btn-default">삭제</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					</form>
				</div>
			</div>
		</div>
	</div>



</body>
</html>