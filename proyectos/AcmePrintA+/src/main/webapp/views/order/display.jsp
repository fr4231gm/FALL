<%--
 * display.jsp
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

<acme:input code="order.moment" path="order.moment"
	readonly="true" />
	<br />
	
<acme:input code="order.stl" path="order.stl"
	readonly="true" />
	<br />
	
<acme:input code="order.material" path="order.material"
	readonly="true" />
	<br />	

<acme:input code="order.status" path="order.status"
	readonly="true" />
	<br />
	
<acme:input code="order.comments" path="order.comments"
	readonly="true" />
	<br />
<security:authorize access="hasRole('CUSTOMER')">
	<jstl:if test="${not empty invoice}">
			<acme:link link="invoice/customer/display.do?invoiceId=${invoice.id}" code="order.invoice" />
	</jstl:if>
</security:authorize>	

<acme:back code="company.go.back"/>