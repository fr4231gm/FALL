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

<display:table name="comments" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<display:column property="title" titleKey="comment.title"
		sortable="true" />
	
	<display:column property="description" titleKey="comment.title"
		sortable="true" />
	
	<display:column property="type" titleKey="comment.type"
		sortable="true" />
	
	<display:column property="pictures" titleKey="comment.pictures"
		sortable="true" />
		
	<display:column property="score" titleKey="comment.score"
		sortable="true" />
		
	<display:column>
		<acme:link link="comment/display.do?commentId=${row.id}" code="comment.display" />
	</display:column>
	

</display:table>

<br>

<acme:back code="comment.goback" />