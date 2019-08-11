<%--
 * action-1.jsp
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

<h2><spring:message code="message.sent"></spring:message></h2>
<display:table
	name 		= "sent"
	id 			= "row"
	requestURI 	= "${requestURI}"
	pagesize	= "5"
	class		= "displaytag"
>

<display:column
	property	= "subject"
	titleKey	= "message.subject"
	sortable	= "true"
/>

<display:column
	property	= "recipient.name"
	titleKey	= "message.recipient"
	sortable	= "true"
/>



<display:column
	property	= "topic"
	titleKey	= "message.topic"
	sortable	= "true"
/>

<display:column
	property	= "moment"
	titleKey	= "message.moment"
	sortable	= "true"
/>

<display:column 
	titleKey="message.show">
		<acme:link link="message/show.do?id=${row.id}" code="message.show" />
</display:column>

</display:table>

<h2><spring:message code="message.received"></spring:message></h2>

<display:table
	name 		= "received"
	id 			= "row"
	requestURI 	= "${requestURI}"
	pagesize	= "5"
	class		= "displaytag"
>

<display:column
	property	= "subject"
	titleKey	= "message.subject"
	sortable	= "true"
/>

<display:column
	property	= "sender.name"
	titleKey	= "message.sender"
	sortable	= "true"
/>



 <display:column
	property	= "topic"
	titleKey	= "message.topic"
	sortable	= "true"
/> 

<display:column
	property	= "moment"
	titleKey	= "message.moment"
	sortable	= "true"
/>

<display:column 
	titleKey="message.show">
		<acme:link link="message/show.do?id=${row.id}" code="message.show" />
</display:column>
</display:table>

		<acme:link link="message/create.do"
				code="message.create" />


	<acme:cancel code="actor.cancel" url="/"/>


