<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<jstl:set var="phoneConfirmationPrefix">
<spring:message code="sponsor.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="sponsor.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="sponsor/register.do" 
	modelAttribute="sponsor" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:input code="sponsor.name" path="name" placeholder="Company1"/>
	<br />
	
	<acme:input code="sponsor.surname" path="surname" placeholder="Escobar Romero"/>
	<br />
	
	<acme:input code="sponsor.vatNumber" path="vatNumber" placeholder="ES29558874H"/>
	<br />
	
	<acme:input code="sponsor.commercialName" path="commercialName" placeholder="MOVISTAR"/>
	<br />

	<acme:input code="sponsor.photo" path="photo" placeholder="https://www.google.es"/>
	<br />

	<acme:input code="sponsor.email" path="email" placeholder="placeholder@mail.com"/>
	<br />
	
	<form:label path="phoneNumber">
		<spring:message code="sponsor.phone.number" />:&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="662130564"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	<br />
	<br />
	
	<acme:input code="sponsor.address" path="address" placeholder="Avda Pi y Margall"/>
	<br />
	<fieldset>
	<legend><spring:message code="company.creditCard" /></legend>
	<acme:input code="company.holder" path="holder"/>
	<br/>
	<form:label path="make">
        <spring:message code="rookie.make" />
    </form:label>
    <form:select path="make" >
        <form:options items="${creditcardMakes}"   />
    </form:select>
    <form:errors path="make" cssClass="error" />
	<acme:input code="company.number" path="number"/>
	<acme:input code="company.expirationMonth" path="expirationMonth"/>
	<acme:input code="company.expirationYear" path="expirationYear"/>
	<acme:input code="company.CVV" path="CVV"/>
	</fieldset>
	<br/>
	<fieldset>
  	<legend><spring:message code="company.useraccount" /></legend>
  
	<acme:input code="company.username" path="username" placeholder="User"/>
	<br />

	<acme:password code="company.password" path="password" />
	<br />

	<acme:password code="company.passwordConfirmation"
		path="passwordConfirmation" />
	<br />

  	</fieldset>

	<button type=submit name="save">
		<spring:message code="sponsor.save" />
	</button>

	<acme:cancel code="sponsor.cancel" url="/"/>
	
	
</form:form>	
