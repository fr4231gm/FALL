<%--
 * list.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<link rel="stylesheet" href="styles/status.css" type="text/css">
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<jstl:if test="${not empty Requestresult}">
  	<h1><spring:message code="request.${Requestresult }" /></h1>
</jstl:if>

<display:table
	name 		= "requests"
	id 			= "row"
	requestURI 	= "${requestURI}"
	pagesize	= "5"
	class		= "displaytag"
>

<display:column
	property	= "procession.title"
	titleKey	= "request.procession"
	sortable	= "true">
	<acme:link 
		code="request.procession" 
		link="procession/display.do?processionId=${row.procession.id}"/>
</display:column>

<jstl:choose>

	<jstl:when test="${row.status eq 'PENDING'}">
		<display:column
	property	= "status"
	titleKey	= "request.status"
	sortable	= "true"
	class		= "pending">
</display:column>
	</jstl:when>
	
	<jstl:when test="${row.status eq 'APPROVED'}">
		<display:column
	property	= "status"
	titleKey	= "request.status"
	sortable	= "true"
	class		= "approved">
</display:column>
	</jstl:when>
	
	<jstl:when test="${row.status eq 'REJECTED'}">
		<display:column
	property	= "status"
	titleKey	= "request.status"
	sortable	= "true"
	class		= "rejected"
	>
</display:column>
	</jstl:when>
	
</jstl:choose>

<security:authorize access="hasRole('BROTHERHOOD')">
	
	<display:column
		titleKey	= "request.decide">
		<jstl:choose>
			<jstl:when test="${row.status eq 'PENDING' }">
		<acme:link 
			code="request.decide" 
			link="request/brotherhood/edit.do?requestId=${row.id}"/>
		
			</jstl:when>
		</jstl:choose>
	</display:column>
	
	<display:column
		property	= "member.name"
		titleKey	= "request.member"
		sortable	= "true">
		<acme:link code="request.member.display" link="request/member/display.do?memberId=${row.member.id}"/>
	</display:column>
</security:authorize>

<security:authorize access="hasRole('MEMBER')">
	<display:column
		titleKey	= "request.delete">
		<jstl:if test="${row.status eq 'PENDING'}">
			<acme:link code="request.delete" link="request/member/delete.do?requestId=${row.id}"/>
	
	</jstl:if></display:column>
	
</security:authorize>

</display:table>
