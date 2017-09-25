<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"  trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="t" items="${temp}">
<div class="checkbox">
	<label><input type="checkbox" name="subject_id" value="${t.id}"
	<c:forEach var="s" items="${subList}">		
	<c:if test="${t.id eq s.subject_id}">
	checked
	</c:if>	
	</c:forEach>
	>${t.name }</label>
</div>
</c:forEach>
