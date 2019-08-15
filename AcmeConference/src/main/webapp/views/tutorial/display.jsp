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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<h3>
	<strong><spring:message code="activity.title" /></strong>
</h3>
<jstl:out value="${tutorial.title}" />
<br />

<h3>
	<strong><spring:message code="activity.speakers" /></strong>
</h3>
<jstl:out value="${tutorial.speakers}" />
<br />

<h3>
	<strong><spring:message code="activity.schedule" /></strong>
</h3>
<jstl:out value="${schedule}" />
<br />

<h3>
	<strong><spring:message code="activity.room" /></strong>
</h3>
<jstl:out value="${tutorial.room}" />
<br />

<h3>
	<strong><spring:message code="activity.summary" /></strong>
</h3>
<jstl:out value="${tutorial.summary}" />
<br />

<h3>
	<strong><spring:message code="activity.attachments" /></strong>
</h3>
<jstl:out value="${tutorial.attachments}" />
<br />

<h3>
	<strong><spring:message code="tutorial.sections" /></strong>
</h3>

<table class="displaytag">

	<tr>
		<th><spring:message code="section.title" /></th>
		<th><spring:message code="section.summary" /></th>
		<th><spring:message code="section.pictures" /></th>
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<th><spring:message code="section.edit" /></th>
			<th><spring:message code="section.delete" /></th>
		</security:authorize>
	</tr>
	<jstl:forEach items="${tutorial.sections}" var="section">
		<tr>
			<td><jstl:out value="${section.title}" /></td>
			<td><jstl:out value="${section.summary}" /></td>
			<td><jstl:out value="${section.pictures}" /></td>
			<security:authorize access="hasRole('ADMINISTRATOR')">
				<td><acme:link code="section.edit"
						link="section/edit.do?sectionId=${section.id}" /></td>
				<td><acme:link code="section.delete"
						link="section/delete.do?sectionId=${section.id}" /></td>
			</security:authorize>
		</tr>
	</jstl:forEach>
</table>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<jstl:if test="${conferencePast eq false }">
		<acme:link code="tutorial.create.section"
			link="section/create.do?tutorialId=${tutorial.id}" />
	</jstl:if>
</security:authorize>