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

<form:form modelAttribute = "sponsorshipForm">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="isEnabled"/>
	<form:hidden path = "paradeId"/>
	
	<acme:input code="sponsorship.banner" path="banner" placeholder="https://www.google.es"/>
	<acme:input code="sponsorship.targetURL" path="targetURL" placeholder="https://www.google.es"/>
	
	<form:label path="${path}">
		<spring:message code="sponsorship.creditCard" />
	</form:label>
	<form:select path="creditCardId" >	
		<form:options items="${creditCards}" itemValue="id" itemLabel="number" />
	</form:select>
	<form:errors path="creditCardId" cssClass="error" />
	
	
	<acme:submit name="save" code="sponsorship.save"/>
	<acme:cancel code="sponsorship.cancel" url="/sponsorship/sponsor/list.do"/>
</form:form>
