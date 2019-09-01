<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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


<table class="gridtable">
<form:form modelAttribute="calculator">
<tr><th colspan=2><spring:message code="profile.action.2" /></th></tr>
	<tr><td><form:input path="x" /> <form:errors class="error" path="x" /> </td></tr>
	<tr><td><form:input path="y" /> <form:errors class="error" path="y" /></td></tr>
<tr><td><form:select path="operator" >
<form:option value="+" />
<form:option value="-" />
<form:option value="*" />
<form:option value="/" />
<form:option value="^" />
</form:select>

<form:errors path="operator" />
<input type="submit" value="<spring:message code='profile.compute'/>" />
</td></tr>

<tr><td>${x} ${operator} ${y} = <jstl:out value="${calculator.result}" /></td></tr>

</form:form>
</table>


