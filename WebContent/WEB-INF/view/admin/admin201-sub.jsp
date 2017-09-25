<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"  trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var = "s" items ="${subList }" >
<tr>
	<td>${s.subject_id }</td>
	<td>${s.subject_name }</td>
</tr>
</c:forEach>