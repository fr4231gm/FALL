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
<spring:message code="rookie.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="rookie.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="rookie/register.do" 
	modelAttribute="rookieForm" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:input code="rookie.name" path="name" placeholder="rookie1"/>
	<br />
	
	<acme:input code="rookie.surname" path="surname" placeholder="Escobar Romero"/>
	<br />
	
	<acme:input code="rookie.vatNumber" path="vatNumber" placeholder="ES29558874H"/>
	<br />
	
	<acme:input code="rookie.photo" path="photo" placeholder="https://www.google.es"/>
	<br />

	<acme:input code="rookie.email" path="email" placeholder="placeholder@mail.com"/>
	<br />
	
	<form:label path="phoneNumber">
		<spring:message code="rookie.phone.number" />:&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="662130564"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	<br />
	<br />
	
	<acme:input code="rookie.address" path="address" placeholder="Avda Pi y Margall"/>
	<br />
	<fieldset>
	<legend><spring:message code="rookie.creditCard" /></legend>
	<acme:input code="rookie.holder" path="holder"/>
	
	<br/>
	
	<form:label path="make">
		<spring:message code="rookie.make" />
	</form:label>	
	<form:select path="make" >	
		<form:options items="${creditcardMakes}"   />
	</form:select>
	<form:errors path="make" cssClass="error" />
	
	<acme:input code="rookie.number" path="number"/>
	<acme:input code="rookie.expirationMonth" path="expirationMonth"/>
	<acme:input code="rookie.expirationYear" path="expirationYear"/>
	<acme:input code="rookie.CVV" path="CVV"/>
	</fieldset>
	<br/>
	<fieldset>
  	<legend><spring:message code="rookie.useraccount" /></legend>
  
	<acme:input code="rookie.username" path="username" placeholder="User"/>
	<br />

	<acme:password code="rookie.password" path="password" />
	<br />

	<acme:password code="rookie.passwordConfirmation"
		path="passwordConfirmation" />
	<br />

	<acme:checkbox code="rookie.checkTerms" path="checkTerms" />
	<br />
  	</fieldset>

	<button type=submit name="save">
		<spring:message code="rookie.save" />
	</button>

	<acme:cancel code="rookie.cancel" url="/"/>
	
	
</form:form>	
