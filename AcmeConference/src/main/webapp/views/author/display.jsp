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


<acme:image src="author.photo"/>


<acme:input code="author.name" path="author.name"
	readonly="true" />
	<br />

<acme:input code="author.surname" path="author.surname"
	readonly="true" />
	<br />

<acme:input code="author.middleName" path="author.middleName"
	readonly="true" />
	<br />
	
<acme:input code="author.score" path="author.score" readonly="true"/>
	<br/>
	

<acme:input code="author.email" path="author.email"
	readonly="true" />
	<br />	

<acme:input code="author.address" path="author.address"
	readonly="true" />
	<br />	



<acme:back code="master.go.back"/>