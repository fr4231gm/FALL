<%--
 * action-2.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<jstl:set var="phoneConfirmationPrefix">
<spring:message code="reviewer.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="reviewer.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="reviewer/edit.do" 
	modelAttribute="reviewer" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount" />
	
	<acme:input code="reviewer.name" path="name" placeholder="Jose"/>
	<br />
	
	<acme:input code="reviewer.middleName" path="middleName" placeholder="Javier"/>
	<br />

	<acme:input code="reviewer.surname" path="surname" placeholder="Gonzalez Gutierrez"/>
	<br />

	<acme:input code="reviewer.photo" path="photo" placeholder="http://www.instagram.com/resekyt"/>
	<br />

	<acme:input code="reviewer.email" path="email" placeholder="miemilio@gmail.com"/>
	<br />
		
	<form:label path="phoneNumber">
		<spring:message code="reviewer.phone.number" />&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="612345678"/>
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	<br />
	
	<acme:input code="reviewer.address" path="address" placeholder="Calle Desengano,21"/>
	<br />
	
	<acme:textarea code="reviewer.keywords" path="keywords" placeholder="Chemistry, physics, engineering..."/>
	<br />
	
	<button type=submit name="save">
		<spring:message code="reviewer.save" />
	</button>

	<acme:cancel code="reviewer.cancel" url="/"/>
	
	
</form:form>	