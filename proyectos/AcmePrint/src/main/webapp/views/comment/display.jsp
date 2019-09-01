<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!-- <link rel="stylesheet" href="styles/table.css" type="text/css">-->

<h2><spring:message code="comment.prev"/> "${comment.post.title}"</h2>

<acme:show code="comment.title" path="comment.title" />

<acme:show code="comment.description" path="comment.description" />

<acme:show code="comment.type" path="comment.type" />

<jstl:if test="${comment.type eq 'PRINTING EXPERIENCE'}">
	<acme:show code="comment.score" path="comment.score" />
</jstl:if>

<div class="form-group">
	<jstl:forEach items="${picturesList}" var="pictureLink">
		<acme:image src="${pictureLink}" />
	</jstl:forEach>
</div>
<br>

<acme:cancel url="post/display.do?postFormId=${comment.post.id}" code="comment.post.display" />