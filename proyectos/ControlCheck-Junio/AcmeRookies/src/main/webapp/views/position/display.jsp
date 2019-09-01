<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<a href="#"><img src="${bannerSponsorship}" alt="${targetURLbanner}" height=200px width=30% /></a>
</div>

<acme:input code="position.title" path="position.title" readonly="true" />
<br />

<acme:input code="position.description" path="position.description"
	readonly="true" />
<br />

<acme:input code="position.ticker" path="position.ticker"
	readonly="true" />
<br />

<acme:input code="position.deadline" path="position.deadline"
	readonly="true" />
<br />

<acme:input code="position.profile" path="position.profile"
	readonly="true" />
<br />

<acme:input code="position.skills" path="position.skills"
	readonly="true" />
<br />

<acme:input code="position.technologies" path="position.technologies"
	readonly="true" />
<br />
<table class="displaytag">

	<spring:message code="position.problems"></spring:message>
	
	<jstl:forEach items="${position.problems}" var="problem">
		<tr>
			<td><jstl:out value="${problem.title}" /></td>
			<td><acme:link code="problem.display"
					link="problem/display.do?problemId=${problem.id}" /></td>
		</tr>
	</jstl:forEach>
</table>
<br />

<acme:input code="position.salary" path="position.salary"
	readonly="true" />
<br />

<security:authorize access="hasRole('AUDITOR')">
	<jstl:if test="${asignable}">
		<acme:link code="position.assign"
			link="position/auditor/assign.do?positionId=${position.id}" />
	</jstl:if>
</security:authorize>

<acme:link code="position.audits"
			link="audit/list.do?positionId=${position.id}" />


<acme:back code="company.go.back" />
