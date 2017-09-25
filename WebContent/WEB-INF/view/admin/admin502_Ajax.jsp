<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:forEach var="g" items="${studentOubGradelist}">
	<tr>
		<td>${g.open_sub_id}</td>
		<td>${g.subject_name}</td>
		<td>${g.b_chulsuk}</td>
		<td>${g.b_filki}</td>
		<td>${g.b_silki}</td>
		<td>${g.g_chulsuk}</td>
		<td>${g.g_filki}</td>
		<td>${g.g_silki}</td>
		<td>${g.test_date}</td>
		<td>${g.test_munje}</td>
	</tr>
</c:forEach>