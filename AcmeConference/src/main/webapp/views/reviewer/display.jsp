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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<acme:input code="reviewer.name" path="reviewer.name" readonly="true"/>
	<br />	
	
	<acme:input code="reviewer.surname" path="reviewer.surname" readonly="true" />
	<br />
	
	<acme:input code="reviewer.middleName" path="reviewer.middleName" readonly="true"/>
	<br />
	
	<acme:input code="reviewer.photo" path="reviewer.photo" readonly="true"/>
	<br />
	
	<acme:input code="reviewer.email" path="reviewer.email" readonly="true"/>
	<br />
	
	<acme:input code="reviewer.phone.number" path="reviewer.phoneNumber" readonly="true" id="phoneId"/>
	<br />
	
	<acme:input code="reviewer.address" path="reviewer.address" readonly="true"/>
	<br />	
	
	<acme:input code="reviewer.keywords" path="reviewer.keywords" readonly="true"/>
	<br />	

<br />	
<br />	
<acme:back code="reviewer.goback"/>