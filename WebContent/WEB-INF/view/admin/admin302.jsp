<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
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
	$(document).ready(function() {
		
		addBtnCheck("${empty openSubList}","${openCourse.course_end_day}","${openSubList[0].sub_end_day}");
		BtnCheck("${openCourse.course_start_day}");
		$(document).on("click", ".btn-insert", function() {
			$("#subject_id").empty();
			$("#book_id").empty();
			$("#book_id").append("<option value=''>교재없음</option>");
			var open_course_id="${openCourse.open_course_id}";
			$.ajax({
				url:"admin3_2_SubjectAndBookListToJSON.do"
				,data:{open_course_id:open_course_id}
				,success:function(data){
						//console.log(data);
						var temp=JSON.parse(data);
						console.log(temp.courseList);
						$.each(temp.subjectList,function(idx,item){
							$("#subject_id").append("<option value='"+item.id+"'>"+item.id+"/"+item.name+"</option>");
						});
						$.each(temp.bookList,function(idx,item){
							$("#book_id").append("<option value='"+item.id+"'>"+item.id+"/"+item.name+"/"+item.publisher+"</option>");
						});
				}
			});
			$("#oub-insert-Modal").modal();
		});
		
		
		$(document).on("change", "#subject_id", function() {
			$("#teacher_id").empty();
			$("#teacher_id").removeAttr("disabled");
			var subject_id=$(this).val()
			$.ajax({
				url:"admin3_2_TeacherListToJSON.do"
				,data:{subject_id:subject_id}
				,success:function(data){
						console.log(data);
						var temp=JSON.parse(data);
						$.each(temp.teacherList,function(idx,item){
							$("#teacher_id").append("<option value='"+item.id+"'>"+item.id+"/"+item.name+"</option>");
						});
				}
			});
			
		});
		//개설과목 삭제 모달창 팝업 버튼
		$(document).on("click", ".btn-delete", function() {
			//OCU001 / Framework을 활용한 빅데이터 개발자 과정
			var open_sub_id=$(this).parents("tr").find("td:nth-child(1)").text();
			var subject_name=$(this).parents("tr").find("td:nth-child(2)").text();
			var sub_start_day=$(this).parents("tr").find("td:nth-child(3)").text();
			var sub_end_day=$(this).parents("tr").find("td:nth-child(4)").text();
			var book_name=$(this).parents("tr").find("td:nth-child(5)").text();
			var teacher_name=$(this).parents("tr").find("td:nth-child(6)").text();
			$("#oub-del-Modal .modal-body h4:nth-child(1)").html(open_sub_id+"/"+subject_name);
			$("#oub-del-Modal .modal-body h5").html(sub_start_day+"~"+sub_end_day+"/"+book_name+"/"+teacher_name);
			$("#oub-del-Modal #open_sub_id").val($(this).val());
			$("#oub-del-Modal").modal();
			
		});
		//개설 과목 수정 버튼
		$(document).on("click", ".btn-modify", function() {
			$("#teacher_id").empty();
			$("#oub-mod-Modal #book_id").empty();
			$("#oub-mod-Modal #book_id").append("<option value='' >교재없음</option>");
			var open_sub_id=$(this).parents("tr").find("td:nth-child(1)").text();
			var subject_name=$(this).parents("tr").find("td:nth-child(2)").text();
			var sub_start_day=$(this).parents("tr").find("td:nth-child(3)").text();
			var sub_end_day=$(this).parents("tr").find("td:nth-child(4)").text();
			var book_name=$(this).parents("tr").find("td:nth-child(5)").text();
			var teacher_name=$(this).parents("tr").find("td:nth-child(6)").text();
			var subject_id=$(this).parents("tr").find("td:nth-child(2)").attr("title")
			console.log("subject_id",subject_id)
			$("#oub-mod-Modal .modal-body h4:nth-child(1)").html(open_sub_id+"/"+subject_name);
			$("#oub-mod-Modal .modal-body h5").html(sub_start_day+"~"+sub_end_day+"/"+book_name+"/"+teacher_name);
			$("#oub-mod-Modal .oubstartdate01").val(sub_start_day);
			$("#oub-mod-Modal .oubenddate01").val(sub_end_day);
			$("#oub-mod-Modal #open_sub_id").val($(this).val());
			var open_course_id="${openCourse.open_course_id}";
			$.ajax({
				url : "admin3_2_SubjectAndBookListToJSON.do",
				data:{open_course_id:open_course_id},
				success : function(data) {
					//console.log(data);
					var temp=JSON.parse(data);
					$.each(temp.bookList,function(idx,item){
						$("#oub-mod-Modal #book_id").append("<option value='"+item.id+"'>"+item.id+"/"+item.name+"/"+item.publisher+"</option>");
					});
				}
			});
			$.ajax({
				url:"admin3_2_TeacherListToJSON.do"
				,data:{subject_id:subject_id}
				,success:function(data){
						console.log(data);
						var temp=JSON.parse(data);
						$.each(temp.teacherList,function(idx,item){
							$("#oub-mod-Modal #teacher_id").append("<option value='"+item.id+"'>"+item.id+"/"+item.name+"</option>");
						});
				}
			});	
			$("#oub-mod-Modal").modal();			
		});
		
		$(".oubstartdate").datepicker({
			beforeShow : function() {
				if("true"=="${empty openSubList}"){
					$(".oubstartdate").datepicker("option","minDate","${openCourse.course_start_day}");
					$(".oubstartdate").datepicker("option","maxDate","${openCourse.course_start_day}");
				}else{
					var startDate = new Date("${openSubList[0].sub_end_day}");
					var dayOfMonth = startDate.getDate();
					startDate.setDate(dayOfMonth + 1);
					console.log("startDate",startDate);
					$(".oubstartdate").datepicker("option","minDate",startDate);
					$(".oubstartdate").datepicker("option","maxDate",startDate);
				}
			},
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd",
			onClose : function() {
			}
		});
		
		$(".oubenddate").datepicker(
				{
					beforeShow : function() {
										$(".oubenddate").datepicker("option","minDate",$(".oubstartdate").val());
							},
							changeMonth : true,
							changeYear : true,
							maxDate: "${openCourse.course_end_day}",
							dateFormat : "yy-mm-dd"
				});
		
		
		//개설과목 수정 날짜 입력 값 처리
		$(".oubstartdate01").datepicker({
			beforeShow : function() {
					$(".oubstartdate01").datepicker("option","minDate","${openCourse.course_start_day}");
					$(".oubstartdate01").datepicker("option","maxDate","${openCourse.course_end_day}");
			},
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd",
			onClose : function() {
			}
		});
		$(".oubenddate01").datepicker(
				{
					beforeShow : function() {
										$(".oubenddate01").datepicker("option","minDate",$(".oubstartdate01").val());
							},
							changeMonth : true,
							changeYear : true,
							maxDate: "${openCourse.course_end_day}",
							dateFormat : "yy-mm-dd"
				});
		//개설과목 등록을 비활성화
		function addBtnCheck(data,arg0,arg1){
			if("true"!=data){
				var courseEndDate = new Date(arg0);
				var subLastDate = new Date(arg1);
				if((courseEndDate-subLastDate)<=0){
					$(".btn-insert").attr("disabled","disabled");
				}
			}
		}	
		//개설과목 수정 및 삭제을 비활성화
		function BtnCheck(arg0){
				var courseStartDate = new Date(arg0);
				var now = new Date();
				if((courseStartDate-now)<=0){
					$(".btn-modify").attr("disabled","disabled");
					$(".btn-delete").attr("disabled","disabled");
				}
		}	
		
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
	

		<div class="content">


			<h3>개설 과목 관리</h3>
			
			<h4 style="text-align: center;font-weight:bold;">${openCourse.open_course_id} / ${openCourse.course_name}</h4>
			<h5 style="text-align: center;">${openCourse.course_start_day} ~ ${openCourse.course_end_day}</h5>

			<hr>

			<table class="table table-striped table-bordered ocu">
				<thead>
					<tr>
						<th>개설과목ID</th>
						<th>개설 과목명</th>
						<th>과목 시작일</th>
						<th>과목 종료일</th>
						<th>교재명</th>
						<th>강사명</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="openSub" items="${openSubList}">
						<tr>
							<td>${openSub.open_sub_id}</td>
							<td title="${openSub.subject_id}">${openSub.subject_name}</td>
							<td>${openSub.sub_start_day}</td>
							<td>${openSub.sub_end_day}</td>
							<td class="bookName">${openSub.book_name}</td>
							<td>${openSub.teacher_name}</td>
							<td><button type="button" class="btn btn-default btn-modify" value="${openSub.open_sub_id}" >수정</button></td>
							<td><button type="button" class="btn btn-default btn-delete" value="${openSub.open_sub_id}" >삭제</button></td>
						</tr>
					</c:forEach>
					<c:if test="${empty openSubList}">
						<tr><td colspan="8" align="center"><h3>개설된 과목이 없습니다.</h3></td></tr>
					</c:if>
				</tbody>
				
			</table>
			<button type="button" class="btn btn-default btn-sm btn-modify">
						TotalCount <span class="badge" id="totalCount">${fn:length(openSubList)}</span></button>
				<button type="button" style="float: left;position: absolute;" class="btn btn-default btn-insert">개설 과목 등록</button>
			


		</div>
	</div>


	<!-- 개설 과목 등록 Modal -->
	<div class="modal fade" id="oub-insert-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">개설 과목 등록</h5>
				</div>
				<form class="form-horizontal" action="admin3_3_OpenSubAdd.do">
				<div class="modal-body">
					<input type="hidden" value="${openCourse.open_course_id}" name="open_course_id">
						<div class="form-group">
							<label class="control-label col-sm-3 m10">과목 ID</label>
							<div class="col-sm-9 m10">
								<select class="form-control" id="subject_id" name="subject_id">
							        <option>SUB001 / 자바 네트워트 프로그래밍</option>
							        <option>SUB002 / 자바 웹 프로그래밍</option>
							        <option>SUB003 / JDBC 프로그래밍</option>
							        <option>SUB004 / HTML5/CSS3/JavaScript</option>
							        <option>SUB005 / jQuery & Ajax</option>
							        <option>SUB006 / UI디자인</option>
							        <option>SUB007 / UI/UX가이드 제작</option>
							        <option>SUB008 / 플랫폼별 UI디자인</option>
							        <option>SUB009 / Framework</option>
							        <option>SUB010 / Oracle DBMS</option>
							        <option>SUB011 / Front-end 개발</option>
							        <option>SUB012 / 빅데이터 분석 및 시각화</option>
							        <option>SUB013 / 실무 프로젝트 통합구현</option>
							    </select>
							</div>
							<label class="control-label col-sm-3 m10">교재 ID</label>
							<div class="col-sm-9 m10">
								<select class="form-control" id="book_id" name="book_id">
							        <option>B001 / 이것이 자바다 / 한빛출판사</option>
							        <option>B002 / 쉽게 배우는 오라클 / 생능출판사</option>
							        <option>B003 / 속깊은 jQuery / 루비페이퍼</option>
							        <option>B004 / html 웹 프로그래밍 입문 / 생능출판사</option>
							        <option>B005 / 프런트엔드 웹 디자인 입문 / 이지스퍼블리싱</option>
							        <option>B006 / UI / UX 디자인 이론과 실습 / 한빛아카데미</option>
							        <option>B007 / 자바 네트워크 프로그래밍 / 제이펍</option>
							        <option>B008 / TCP/IP 쉽게, 더 쉽게 / 제이펍</option>
							        <option>B009 / 파이썬 라이브러리를 활용한 데이터 분석 / 한빛미디어</option>
							        <option>B010 / 논쟁적 UX / 제이펍</option>
							    </select>
							</div>
							<label class="control-label col-sm-3 m10">강사 ID</label>
							<div class="col-sm-9 m10">
								<select class="form-control" id="teacher_id" name="teacher_id" disabled="disabled">
							    </select>
							</div>
							<label class="control-label col-sm-3 m10">과목 시작일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control oubstartdate" id="oubstartdate"
									name="oubstartdate" placeholder="과목 시작일(YYYY-MM-DD)"
									required="required">
							</div>
							<label class="control-label col-sm-3 m10">과목 종료일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control oubenddate" id="oubenddate"
									name="oubenddate" placeholder="과목 종료일(YYYY-MM-DD)"
									required="required">
							</div>
						</div>
					
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-default"  >등록</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
				</form>
			</div>

		</div>
	</div>


	<!-- 개설 과정 수정 Modal -->
	<div class="modal fade" id="oub-mod-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">개설 과목 수정</h5>
				</div>
				<form class="form-horizontal" action="admin_3_2_OpenSubUp.do"> 
				<input type="hidden" value="${openCourse.open_course_id}" name="open_course_id">
				<input type="hidden" id="open_sub_id" name="open_sub_id">
				<div class="modal-body">
				
				<h4 style="text-align: center;">OUB001 / 자바 네트워트 프로그래밍</h4>
				<h5 style="text-align: center;">2017/07/01 ~ 2017/07/31 / html 웹 프로그래밍 입문 / 심심해</h5>
				
				<hr>

						<div class="form-group">
							<label class="control-label col-sm-3 m10">교재 ID</label>
							<div class="col-sm-9 m10">
								<select class="form-control" id="book_id" name="book_id">
							        <option>B001 / 이것이 자바다 / 한빛출판사</option>
							        <option>B002 / 쉽게 배우는 오라클 / 생능출판사</option>
							        <option>B003 / 속깊은 jQuery / 루비페이퍼</option>
							        <option selected>B004 / html 웹 프로그래밍 입문 / 생능출판사</option>
							        <option>B005 / 프런트엔드 웹 디자인 입문 / 이지스퍼블리싱</option>
							        <option>B006 / UI / UX 디자인 이론과 실습 / 한빛아카데미</option>
							        <option>B007 / 자바 네트워크 프로그래밍 / 제이펍</option>
							        <option>B008 / TCP/IP 쉽게, 더 쉽게 / 제이펍</option>
							        <option>B009 / 파이썬 라이브러리를 활용한 데이터 분석 / 한빛미디어</option>
							        <option>B010 / 논쟁적 UX / 제이펍</option>
							    </select>
							</div>
							<label class="control-label col-sm-3 m10">강사 ID</label>
							<div class="col-sm-9 m10">
								<select class="form-control" id="teacher_id" name="teacher_id" >
							        
							    </select>
							</div>
							<label class="control-label col-sm-3 m10">과목 시작일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control oubstartdate01" id="oubstartmoddate"
									name="oubstartdate" placeholder="과목 시작일(YYYY-MM-DD)" value="2017/07/01"
									required="required">
							</div>
							<label class="control-label col-sm-3 m10">과목 종료일</label>
							<div class="col-sm-9 m10">
								<input type="text" class="form-control oubenddate01" id="oubendmoddate"
									name="oubenddate" placeholder="과목 종료일(YYYY-MM-DD)" value="2017/07/31"
									required="required">
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




	<!-- 개설 과목 삭제 Modal -->
	<div class="modal fade" id="oub-del-Modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title">개설 과목 삭제</h5>
				</div>
				<div class="modal-body">
					
					<h4 style="text-align: center;">OUB001 / 자바 네트워트 프로그래밍</h4>
					<h5 style="text-align: center;">2017/07/01 ~ 2017/07/31 / html 웹 프로그래밍 입문 / 심심해</h5>
					<br>
					<h4 style="text-align: center;">개설 과목을 삭제하시겠습니까?</h4>
				</div>
				<div class="modal-footer">
				<form action="admin_3_2_OpenSubDel.do">
				<input  type="hidden" id="open_sub_id" name="open_sub_id">
				<input  type="hidden" id="open_course_id" name="open_course_id" value="${openCourse.open_course_id}">
					<button type="submit" class="btn btn-default">삭제</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					</form>
				</div>
			</div>

		</div>
	</div>



</body>
</html>