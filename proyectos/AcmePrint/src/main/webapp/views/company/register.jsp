<%--
 * register.jsp
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
<spring:message code="company.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="company.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="company/register.do" 
	modelAttribute="companyForm" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:input code="company.name" path="name" placeholder="Company1"/>
	<br />
	
	<acme:input code="company.middleName" path="middleName" placeholder="Luis"/>
	<br />
	
	<acme:input code="company.surname" path="surname" placeholder="Escobar Romero"/>
	<br />
	
	<acme:input code="company.vatNumber" path="vatNumber" placeholder="ES29558874H"/>
	<br />
	
	<acme:input code="company.commercialName" path="commercialName" placeholder="MOVISTAR"/>
	<br />

	<acme:input code="company.photo" path="photo" placeholder="https://www.google.es"/>
	<br />

	<acme:input code="company.email" path="email" placeholder="placeholder@mail.com"/>
	<br />
	
	<div class="form-group">
		<form:label path="phoneNumber">
			<spring:message code="company.phone.number" />:&nbsp;
		</form:label>
		<form:input path="phoneNumber" id="phoneId" placeholder="662130564"/>
		<form:errors cssClass="error" path="phoneNumber"/>
	</div>
	<br />
	
	<acme:input code="company.address" path="address" placeholder="Avda Pi y Margall"/>
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

	<acme:checkbox code="company.checkTerms" path="checkTerms" />
	<br />
  	</fieldset>

	<button type=submit name="save">
		<spring:message code="company.save" />
	</button>

	<acme:cancel code="company.cancel" url="/"/>
	
	
</form:form>	
