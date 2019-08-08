<%--
 * display.jsp
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	
<acme:input code="conference.title" path="conference.title"
	readonly="true" />
	<br />

<acme:input code="conference.acronym" path="conference.acronym"
	readonly="true" />
	<br />
	
<acme:input code="conference.venue" path="conference.venue"
	readonly="true" />
	<br />

<acme:input code="conference.submissionDeadline" path="conference.submissionDeadline"
	readonly="true" />
	<br />
	
<acme:input code="conference.notificationDeadline" path="conference.notificationDeadline"
	readonly="true"/>
	<br />	

<acme:input code="conference.cameraReadyDeadline" path="conference.cameraReadyDeadline"
	readonly="true" />
	<br />	

<acme:input code="conference.startDate" path="conference.startDate"
	readonly="true" />
	<br />	
	

<acme:input code="conference.endDate" path="conference.endDate"
	readonly="true" />
	<br />	


<acme:textarea code="conference.summary" path="conference.summary"
	readonly="true" />
	<br />	
	
	
<acme:input code="conference.fee" path="conference.fee"
	readonly="true" />
	<br />	

<br />	
<br />		
	
<jstl:if test="${future eq 'true'}">	
	<security:authorize access="hasRole('AUTHOR')">	
		
	
			<acme:link link="registration/author/register.do?conferenceId=${conference.id}"
				code="conference.registration" />
	
	</security:authorize>
</jstl:if>

<br />	
<br />	

<acme:link code="conference.create.comment" link="comment/createByConference.do?conferenceId=${conference.id}" />
<br />	
<br />	
<acme:back code="conference.goback"/>