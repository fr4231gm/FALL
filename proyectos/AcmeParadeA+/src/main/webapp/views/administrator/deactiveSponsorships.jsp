<%--
 * deactiveSponsorships.jsp
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

<link rel="stylesheet" href="styles/textMessageColor.css"
	type="text/css">

<display:table name="sponsorships" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<jstl:if test="${row.isEnabled eq true}">
		<jstl:set var="colorClass" value="active"></jstl:set>
	</jstl:if>
	<jstl:if test="${row.isEnabled eq false}">
		<jstl:set var="colorClass" value="deactive"></jstl:set>
	</jstl:if>

	<display:column property="parade.title" titleKey="sponsorship.parade"
		sortable="true" class="${colorClass}" />

	<display:column property="creditCard.number"
		titleKey="sponsorship.creditCard" sortable="true"
		class="${colorClass}" />

	<display:column property="creditCard.expirationMonth"
		titleKey="creditcard.expirationMonth" sortable="true"
		class="${colorClass}" />

	<display:column property="creditCard.expirationYear"
		titleKey="creditcard.expirationYear" sortable="true"
		class="${colorClass}" />

	<display:column property="isEnabled" titleKey="administrator.sponsorship.isEnabled"
		sortable="true" class="${colorClass}" />

</display:table>
<br>
<jstl:if test="${exito}">
	<jstl:out value="${exito}"></jstl:out>
</jstl:if>
<br>
<jstl:if test="${desaparece eq false}">
<acme:link link="sponsorship/administrator/deactiveSponsorships.do?sponsorships=${sponsorships}"
	code="sponsorship.deactivate" />
</jstl:if>
<acme:cancel code="sponsorship.go.back" url="/" />