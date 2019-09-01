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

<h2><spring:message code="message.sended"></spring:message></h2>
<display:table
	name 		= "sended"
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

<display:column titleKey="message.tags">
			<jstl:if test="${row.tags eq 'DELETED'}">
			<spring:message code="tag.delete"></spring:message>
			</jstl:if>
			<jstl:if test="${row.tags eq 'SYSTEM'}">
			<spring:message code="tag.system"></spring:message>
			</jstl:if>
			<jstl:if test="${!(row.tags eq 'DELETED'or row.tags eq 'SYSTEM')}">
			${row.tags}
			</jstl:if>
</display:column> 

<%-- <display:column
	property	= "tags"
	titleKey	= "message.tags"
	sortable	= "true"
/> --%>

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

<display:column titleKey="message.tags">
			<jstl:if test="${row.tags eq 'DELETED'}">
			<spring:message code="tag.delete"></spring:message>
			</jstl:if>
			<jstl:if test="${row.tags eq 'SYSTEM'}">
			<spring:message code="tag.system"></spring:message>
			</jstl:if>
			<jstl:if test="${!(row.tags eq 'DELETED'or row.tags eq 'SYSTEM')}">
			${row.tags}
			</jstl:if>
</display:column> 

<%-- <display:column
	property	= "tags"
	titleKey	= "message.tags"
	sortable	= "true"
/> --%>

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


<acme:back code="position.go.back"/>


