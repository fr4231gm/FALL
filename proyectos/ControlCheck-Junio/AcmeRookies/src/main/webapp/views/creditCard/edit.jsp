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

<form:form modelAttribute = "creditCardForm" action="creditCard/edit.do">

	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<acme:input code="creditcard.holder" path="holder" />
	<br>
	<form:label path="make">
        <spring:message code="rookie.make" />
    </form:label>
    <form:select path="make" >
        <form:options items="${creditcardMakes}" />
    </form:select>
    <form:errors path="make" cssClass="error" />
	<acme:input code="creditcard.number" path="number" />
	<acme:input code="creditcard.expirationMonth" path="expirationMonth"  />
	<acme:input code="creditcard.expirationYear" path="expirationYear" />
	<acme:input code="creditcard.CVV" path="CVV" />
	<br>
	
	<acme:submit name="save" code="creditcard.save"/>
	<acme:cancel code="creditcard.go.back" url="/"/>
	
</form:form>