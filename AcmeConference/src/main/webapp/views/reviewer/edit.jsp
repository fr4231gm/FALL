<%--
 * action-2.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<jstl:set var="phoneConfirmationPrefix">
<spring:message code="reviewer.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="reviewer.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="reviewer/edit.do" 
	modelAttribute="reviewer" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" />
	
	<acme:input code="actor.name" path="name" placeholder="Company1"/>
	<acme:input code="actor.surname" path="surname" placeholder="Escobar Romero"/>
	<acme:input code="actor.middleName" path="middleName" placeholder="Pablo"/>
	<acme:input code="actor.photo" path="photo" placeholder="https://www.google.es"/>
	<acme:input code="actor.email" path="email" placeholder="placeholder@mail.com"/>
	<acme:input code="actor.phone.number" path="phoneNumber" placeholder="662130564" id="phoneId"/>
	<acme:input code="actor.address" path="address" placeholder="Avda Pi y Margall"/>
	<acme:textarea code="reviewer.keywords" path="keywords" placeholder="Chemistry, physics, engineering..."/>
	<br />
	
	<acme:submit name="save" code="actor.save"/>
	<acme:cancel code="actor.cancel" url="/"/>
	
</form:form>	