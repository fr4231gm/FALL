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

	
<acme:input code="customer.name" path="customer.name"
	readonly="true" />
	<br />
	
<acme:input code="customer.middleName" path="customer.middleName"
	readonly="true" />
	<br />	

<acme:input code="customer.surname" path="customer.surname"
	readonly="true" />
	<br />
	
<acme:input code="customer.vatNumber" path="customer.vatNumber"
	readonly="true" />
	<br />

<acme:input code="customer.photo" path="customer.photo"
	readonly="true" />
	<br />
	
<acme:input code="customer.phone.number" path="customer.phoneNumber"
	readonly="true"/>
	<br />	

<acme:input code="customer.email" path="customer.email"
	readonly="true" />
	<br />	
	
<acme:input code="customer.address" path="customer.address"
	readonly="true" />
	<br />	
	
<acme:link code="company.socialprofiles" link="social-profile/list.do?actorId=${customer.id}"/>
	
<security:authorize access="hasRole('COMPANY')">

<acme:link code="order.list" link="order/company/listByCustomer.do?customerId=${customer.id}" />

</security:authorize>	

<acme:back code="customer.go.back"/>