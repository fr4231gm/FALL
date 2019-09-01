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

	
<acme:input code="actor.name" path="actor.name"
	readonly="true" />
	<br />

<acme:input code="actor.surname" path="actor.surname"
	readonly="true" />
	<br />
	
<acme:input code="actor.vatNumber" path="actor.vatNumber"
	readonly="true" />
	<br />

<acme:input code="actor.photo" path="actor.photo"
	readonly="true" />
	<br />
	
<acme:input code="actor.phone.number" path="actor.phoneNumber"
	readonly="true"/>
	<br />	

<acme:input code="actor.email" path="actor.email"
	readonly="true" />
	<br />	

<acme:input code="actor.address" path="actor.address"
	readonly="true" />
	<br />	

<jstl:choose>
<jstl:when test="${actor.isSpammer eq true}">
	<acme:input code="actor.spammer" path="actor.isSpammer"
		readonly="true" />
		<br />	
</jstl:when>
<jstl:when test="${actor.isSpammer eq false && hasSpamMessages eq true}">
	<spring:message code="actor.spammer"></spring:message>
	<jstl:out value="N/A"></jstl:out>
	<br />	
	<br />	
</jstl:when>
<jstl:when test="${actor.isSpammer eq false && hasSpamMessages eq false}">
	<acme:input code="actor.spammer" path="actor.isSpammer"
		readonly="true" />
		<br />	
</jstl:when>

</jstl:choose>

<acme:back code="actor.go.back"/>