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
<!-- <link rel="stylesheet" href="styles/table.css" type="text/css">-->

<h2><spring:message code="application"/> <jstl:if test="${application.status eq 'ACCEPTED' }"> <spring:message code="status.accepted" /> </jstl:if><jstl:if test="${application.status eq 'REJECTED'}"><spring:message code="status.rejected"/></jstl:if><jstl:if test="${application.status eq 'PENDING' }"><spring:message code="status.pending" /></jstl:if><spring:message code="application.company"/> <a href="https://localhost:8443/Acme-Print/company/display.do?companyId=${application.company.id}">${application.company.commercialName}</a></h2>
<acme:input code="application.moment" path="application.moment"
	readonly="true" />

	
<h3><spring:message code="application.status"></spring:message>: </h3>
<jstl:if test="${application.status eq 'ACCEPTED' }"> <spring:message code="status.accepted" /> </jstl:if><jstl:if test="${application.status eq 'REJECTED'}"><spring:message code="status.rejected"/></jstl:if><jstl:if test="${application.status eq 'PENDING' }"><spring:message code="status.pending" /></jstl:if>
	
<acme:input code="application.offeredPrice" path="application.offeredPrice"
	readonly="true" />
	
<acme:textarea code="application.companyComments" path="application.companyComments"
	readonly="true" />

<acme:textarea code="application.customerComments" path="application.customerComments"
	readonly="true" />

<security:authorize access="hasRole('COMPANY')">

	<jstl:if test="${permiso == true }">
		
		<jstl:if test="${encolable and application.status eq 'ACCEPTED'}">
			<acme:link link="request/company/create.do?orderId=${application.order.id}" code="application.printSpoolear" />
		</jstl:if>
		
		<jstl:if test="${not empty posicion}">
			<spring:message code="request.position"/><jstl:out value=": ${posicion}"></jstl:out>
		</jstl:if>
		
				<br><br>
		<acme:cancel url="application/company/list.do" code="application.goback" />
	</jstl:if>
	
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
	<jstl:if test="${permiso == true }">
		<acme:cancel url="application/customer/list.do?orderId=${application.order.id}" code="application.goback" />
		<jstl:if test="${application.status eq 'ACCEPTED'}">
			<acme:link link="invoice/customer/display.do?invoiceId=${invoice.id}" code="application.invoice" />
		</jstl:if>
	</jstl:if>
</security:authorize>