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

<form:form modelAttribute = "creditCardForm" action="creditcard/sponsor/edit.do">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<jstl:set var="readonlyvar" value="true"></jstl:set>

	<jstl:if test="${creditCardForm.id == 0 }">
		<jstl:set var="readonlyvar" value="false"></jstl:set>
	</jstl:if>

	<acme:input code="creditcard.holder" path="holder" placeholder="Arturo" readonly="${readonlyvar}" />
	<acme:input code="creditcard.make" path="make" placeholder="${creditcardMakes[0]}" readonly="${readonlyvar}" />
	<acme:input code="creditcard.number" path="number" placeholder="13245675" readonly="${readonlyvar}" />
	<acme:input code="creditcard.expirationMonth" path="expirationMonth" placeholder="01" readonly="${readonlyvar}" />
	<acme:input code="creditcard.expirationYear" path="expirationYear" placeholder="19" readonly="${readonlyvar}" />
	<acme:input code="creditcard.CVV" path="CVV" placeholder="123" readonly="${readonlyvar}" />
	<br>
	<jstl:if test="${readonlyvar == false}">
		<acme:submit name="save" code="creditcard.save"/>
		&nbsp;
		<acme:cancel url="creditcard/sponsor/list.do" code="creditcard.cancel"/>
	</jstl:if>
	<br>
	<acme:cancel code="creditcard.go.back" url="/"/>
	
</form:form>
	