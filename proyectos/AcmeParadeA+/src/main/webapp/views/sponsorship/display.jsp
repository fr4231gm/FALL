<%--
 * action-1.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:input code="sponsorship.banner" path="sponsorship.banner"
	readonly="true" />

<acme:input code="sponsorship.targetURL" path="sponsorship.targetURL"
	readonly="true" />

<br>
<jstl:choose>
	<jstl:when test="${sponsorship.isEnabled eq true}">
		<form:label path="sponsorship.isEnabled">
			<spring:message code="sponsorship.isEnabled" />
		</form:label>
		<input type="text" value="<spring:message code="sponsorship.isEnabled.true" />" readonly/>
	</jstl:when>
	<jstl:when test="${sponsorship.isEnabled eq false}">
		<form:label path="sponsorship.isEnabled">
			<spring:message code="sponsorship.isEnabled" />
		</form:label>
		<input type="text" value="<spring:message code="sponsorship.isEnabled.false" />" readonly/>
	</jstl:when>
</jstl:choose>





<acme:input code="sponsorship.creditCard"
	path="sponsorship.creditCard.number" readonly="true" />

<acme:input code="sponsorship.parade" path="sponsorship.parade.title"
	readonly="true" />
<br>

<acme:cancel code="sponsorship.go.back"
	url="/sponsorship/sponsor/list.do" />
