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
<spring:message code="actor.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="actor.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="sponsor/register.do" 
	modelAttribute="sponsorForm" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:input code="actor.name" path="name" placeholder="Company1"/>
	<acme:input code="actor.surname" path="surname" placeholder="Escobar Romero"/>
	<acme:input code="actor.middleName" path="middleName" placeholder="Pablo"/>
	<acme:input code="actor.photo" path="photo" placeholder="https://www.google.es"/>
	<acme:input code="actor.email" path="email" placeholder="placeholder@mail.com"/>
	<acme:input code="actor.phone.number" path="phoneNumber" placeholder="662130564" id="phoneId"/>
	<acme:input code="actor.address" path="address" placeholder="Avda Pi y Margall"/>

	<fieldset>
		<div class="centrado">
			<i class="fa fa-credit-card"></i>   
			<spring:message code="creditCard" />
			<br><br>
		</div>
		
		<acme:input code="creditCard.holder" path="creditCard.holder" placeholder="Juan Pérez Prado"/>
		
		<div class="form-group">
		<form:label path="creditCard.make"> <spring:message code="creditCard.make" /> </form:label>
    	<form:select path="creditCard.make" >
        	<form:options items="${makes}" />
    	</form:select>
		<form:errors path="creditCard.make" cssClass="error" />
		</div>
		<br>
		<acme:input code="creditCard.number" path="creditCard.number" placeholder="5332563560352168"/>
		<acme:input code="creditCard.expirationMonth" path="creditCard.expirationMonth" type="number" placeholder="07"/>
		<acme:input code="creditCard.expirationYear" path="creditCard.expirationYear" type="number" placeholder="24"/>
		<acme:input code="creditCard.CVV" path="creditCard.CVV" type="number" placeholder="324"/>
	</fieldset>

	<br/>
	
	<fieldset>
		<div class="centrado">
		  	<i class="fa fa-user"></i> <spring:message code="actor.useraccount" />
		  	<br><br>
	  	</div>
		<acme:input code="actor.username" path="username" placeholder="User"/>
		<acme:password code="actor.password" path="password" />
		<acme:password code="actor.passwordConfirmation" path="passwordConfirmation" />
		<acme:checkbox code="actor.checkTerms" path="checkTerms" value="${sponsorForm.checkTerms}"/>
  	</fieldset>
	  	<br><br>
	<acme:submit name="save" code="actor.save"/>
	<acme:cancel code="actor.cancel" url="/"/>
	
	
</form:form>	
