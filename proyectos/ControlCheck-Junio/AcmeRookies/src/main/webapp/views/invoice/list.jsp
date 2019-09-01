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

<link rel="stylesheet" href="styles/textMessageColor.css"
	type="text/css">

<display:table name="charges" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
<%-- 
	<jstl:if test="${row.isEnabled eq true}">
		<jstl:set var="colorClass" value="active"></jstl:set>
	</jstl:if>
	<jstl:if test="${row.isEnabled eq false}">
		<jstl:set var="colorClass" value="deactive"></jstl:set>
	</jstl:if>
--%>
	<display:column property="moment" titleKey="invoice.moment"
		sortable="true" />

	<display:column property="amount" titleKey="invoice.amount"
		sortable="true" />
	
	<display:column property="tax" titleKey="invoice.tax"
		sortable="true" />

</display:table>

<br>
<spring:message code="invoice.visits" /><input type="text" value="${visits}" readonly />
<br>
<spring:message code="invoice.sumTotal" /><input type="text" value="${sumTotal}" readonly />
<br>
<spring:message code="invoice.totalAmount" /><input type="text" value="${totalWithTax}" readonly />
<br>
<acme:cancel code="sponsorship.go.back" url="/" />