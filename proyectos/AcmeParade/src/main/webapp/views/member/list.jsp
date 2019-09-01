<%--
 * action-1.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
	


<display:table
	name 		= "members"
	id 			= "row"
	requestURI 	= "${requestURI}"
	pagesize	= "5"
	class		= "displaytag"
>
<display:column
	property	= "name"
	titleKey	= "member.name"
	sortable	= "true"
/>

<display:column
	property	= "middleName"
	titleKey	= "member.middleName"
	sortable	= "true"
/>

<display:column
	property	= "surname"
	titleKey	= "member.surname"
	sortable	= "true"
/>

<display:column
	property	= "email"
	titleKey	= "member.email"
	sortable	= "true"
/>

	
<acme:cancel url="/" code="member.goback"/>

</display:table>
