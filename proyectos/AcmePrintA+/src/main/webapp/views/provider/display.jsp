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

<acme:input code="provider.make" path="provider.make"
	readonly="true" />
	<br />
	
<acme:input code="provider.name" path="provider.name"
	readonly="true" />
	<br />

<acme:input code="provider.surname" path="provider.surname"
	readonly="true" />
	<br />
	
<acme:input code="provider.vatNumber" path="provider.vatNumber"
	readonly="true" />
	<br />

<acme:input code="provider.photo" path="provider.photo"
	readonly="true" />
	<br />
	
<acme:input code="provider.phone.number" path="provider.phoneNumber"
	readonly="true"/>
	<br />	

<acme:input code="provider.email" path="provider.email"
	readonly="true" />
	<br />	


<acme:back code="provider.go.back"/>