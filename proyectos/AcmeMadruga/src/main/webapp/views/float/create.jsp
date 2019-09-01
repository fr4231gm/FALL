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


<form:form 
action = "float/brotherhood/edit.do"
modelAttribute = "floatForm">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:input code="float.title" path="title"/>
	<acme:input code="float.description" path="description"/>
	<acme:input code="float.pictures" path="pictures"/>
	
	<acme:submit name="save" code="float.save"/>
	<acme:back code="float.cancel"/>
</form:form>
