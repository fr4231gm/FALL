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
<spring:message code="administrator.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="administrator.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="administrator/register.do" 
	modelAttribute="administratorForm" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:input code="administrator.name" path="name" placeholder="Alberto"/>
	<br />
	
	<acme:input code="administrator.middleName" path="middleName" placeholder="Luis"/>
	<br />
	
	<acme:input code="administrator.surname" path="surname" placeholder="Escobar Romero"/>
	<br />
	
	<acme:input code="administrator.vatNumber" path="vatNumber" placeholder="ES29558874H"/>
	<br />
	
	<acme:input code="administrator.photo" path="photo" placeholder="https://www.google.es"/>
	<br />

	<acme:input code="administrator.email" path="email" placeholder="placeholder@mail.com"/>
	<br />
	
	<div class="form-group">
		<form:label path="phoneNumber">
			<spring:message code="administrator.phone.number" />:&nbsp;
		</form:label>
		<form:input path="phoneNumber" id="phoneId" placeholder="662130564"/>
		<form:errors cssClass="error" path="phoneNumber"/>
	</div>
	<br />
	
	<acme:input code="administrator.address" path="address" placeholder="Avda Pi y Margall"/>
	
	<br/>
	<fieldset>
  	<legend><spring:message code="administrator.useraccount" /></legend>
  
	<acme:input code="administrator.username" path="username" placeholder="User"/>
	<br />

	<acme:password code="administrator.password" path="password" />
	<br />

	<acme:password code="administrator.passwordConfirmation"
		path="passwordConfirmation" />
	<br />

	<acme:checkbox code="administrator.checkTerms" path="checkTerms" />
	<br />
  	</fieldset>

	<button type=submit name="save">
		<spring:message code="administrator.save" />
	</button>

	<acme:cancel code="administrator.cancel" url="/"/>
	
	
</form:form>	
