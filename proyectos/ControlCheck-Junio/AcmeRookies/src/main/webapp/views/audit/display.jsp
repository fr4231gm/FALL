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
	
<acme:input code="audit.moment" path="audit.moment"
	readonly="true" />
	
<acme:input code="audit.text" path="audit.text"
	readonly="true" />
	
<acme:input code="audit.score" path="audit.score"
	readonly="true" />
	
<br>

Auditor: <jstl:out value="${audit.auditor.name}"></jstl:out><br>
Position: <jstl:out value="${audit.position.title}"></jstl:out>
	<br>
	

<security:authorize access="hasRole('COMPANY')">
	<jstl:if test="${permiso}">
		<a href="wolem/company/create.do?auditId=${audit.id}"><spring:message code="wolem.create" /></a>	<br>
		<a href="wolem/company/list.do?auditId=${audit.id}"><spring:message code="wolem.list" /></a>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('AUDITOR')">
	<jstl:if test="${permiso}">
	<a href="wolem/auditor/list.do?auditId=${audit.id}"><spring:message code="wolem.list" /></a>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('COMPANY')">
	
</security:authorize>

<acme:back code="position.go.back"/>
