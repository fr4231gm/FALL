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

<form:form action="${actionURI}" modelAttribute="panel">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="conference" />

	<acme:input code="activity.title" path="title"
		placeholder="Title of panel" />
	<br />

	<acme:input code="activity.speakers" path="speakers"
		placeholder="Javier González, Rocío Gutiérrez" />
	<br />

	<p>
		<spring:message code="activity.message.startMoment" />
		<fmt:formatDate value="${panel.conference.startDate}"
			pattern="dd/MM/yyyy HH:mm" />
		<spring:message code="activity.message.endMoment" />
		<fmt:formatDate value="${panel.conference.endDate}"
			pattern="dd/MM/yyyy HH:mm" />
	</p>
	
	<acme:input code="activity.startMoment" path="startMoment"
		placeholder="dd/MM/yyyy HH:mm" />
	<br />

	<acme:input code="activity.duration" path="duration" placeholder="30" />
	<br />

	<acme:input code="activity.room" path="room" placeholder="Room A1.13" />
	<br />

	<acme:textarea code="activity.summary" path="summary"
		placeholder="Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum." />
	<br />

	<acme:textarea code="activity.attachments" path="attachments"
		placeholder="https://www.example.com/asasdf" />
	<br />

	<button type="submit" name="save">
		<spring:message code="activity.save" />
	</button>

	<acme:cancel code="activity.cancel" url="/" />

</form:form>
