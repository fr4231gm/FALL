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
<spring:message code="author.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="author.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="author/edit.do" 
	modelAttribute="author" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="score" />
	<form:hidden path="userAccount" />
	
	<acme:input code="author.name" path="name" placeholder="Jose"/>
	<br />
	
	<acme:input code="author.middleName" path="middleName" placeholder="Javier"/>
	<br />

	<acme:input code="author.surname" path="surname" placeholder="Gonzalez Gutierrez"/>
	<br />

	<acme:input code="author.photo" path="photo" placeholder="http://www.instagram.com/resekyt"/>
	<br />

	<acme:input code="author.email" path="email" placeholder="miemilio@gmail.com"/>
	<br />
		
	<form:label path="phoneNumber">
		<spring:message code="author.phone.number" />&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="612345678"/>
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	<br />
	
	<acme:input code="author.address" path="address" placeholder="Calle Desengano,21"/>
	<br />
	
	<button type=submit name="save">
		<spring:message code="author.save" />
	</button>

	<acme:cancel code="author.cancel" url="/"/>
	
	
</form:form>	