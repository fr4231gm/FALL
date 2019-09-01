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
<spring:message code="provider.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="provider.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="provider/register.do" 
	modelAttribute="providerForm" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:input code="provider.name" path="name" placeholder="Company1"/>
	<br />
	
	<acme:input code="provider.middleName" path="middleName" placeholder="Luis"/>
	<br />
	
	<acme:input code="provider.surname" path="surname" placeholder="Escobar Romero"/>
	<br />
	
	<acme:input code="provider.vatNumber" path="vatNumber" placeholder="ES29558874H"/>
	<br />
	
	<acme:input code="provider.make" path="make" placeholder="MOVISTAR"/>
	<br />

	<acme:input code="provider.photo" path="photo" placeholder="https://www.google.es"/>
	<br />

	<acme:input code="provider.email" path="email" placeholder="placeholder@mail.com"/>
	<br />
	
	<form:label path="phoneNumber">
		<spring:message code="provider.phone.number" />:&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="662130564"/>
	<form:errors cssClass="error" path="phoneNumber"/>
	<br />
	<br />
	
	<acme:input code="provider.address" path="address" placeholder="Avda Pi y Margall"/>
	<br/>
	
	<fieldset>
  	<legend><spring:message code="provider.useraccount" /></legend>
  
	<acme:input code="provider.username" path="username" placeholder="User"/>
	<br />

	<acme:password code="provider.password" path="password" />
	<br />

	<acme:password code="provider.passwordConfirmation"
		path="passwordConfirmation" />
	<br />

	<acme:checkbox code="provider.checkTerms" path="checkTerms" />
	<br />
  	</fieldset>

	<button type=submit name="save">
		<spring:message code="provider.save" />
	</button>

	<acme:cancel code="provider.cancel" url="/"/>
	
	
</form:form>	
