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

	
<acme:input code="registration.creditCard.holder" path="registration.creditCard.holder"
	readonly="true" />
	<br />
	
<acme:input code="registration.creditCard.make" path="registration.creditCard.make"
	readonly="true" />
	<br />	

<acme:input code="registration.creditCard.number" path="registration.creditCard.number"
	readonly="true" />
	<br />
	
<acme:input code="registration.creditCard.expirationMonth" path="registration.creditCard.expirationMonth"
	readonly="true" />
	<br />
	
<acme:input code="registration.creditCard.expirationYear" path="registration.creditCard.expirationYear"
	readonly="true" />
	<br />
	
<acme:input code="registration.creditCard.CVV" path="registration.creditCard.CVV"
	readonly="true" />
	<br />			

<br />	
<br />	
<acme:back code="registration.goback"/>