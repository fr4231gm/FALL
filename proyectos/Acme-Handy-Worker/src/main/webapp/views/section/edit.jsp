<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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

<form:form modelAttribute="section" action="${actionURI }" >
	<form:hidden path="indice"/>
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<b><form:label path="title"><spring:message code="section.title">:&nbsp;</spring:message></form:label></b>
	<form:input type="text" path="title" value="${section.title}"/><br/>
	<form:errors path="title" cssClass="error"/>

	<b><form:label path="text"><spring:message code="section.text">:&nbsp;</spring:message></form:label></b>
	<form:textarea path="text" value="${section.title}"/><br />
	<form:errors path="text" cssClass="error"/>

	<b><form:label path="pictureUrl"><spring:message code="section.picture">:&nbsp;</spring:message></form:label></b>
	<form:input type="text" path="pictureUrl" placeholder="<spring:message code ='section.url'/>" value="${section.pictureUrl}" /> <br/>
	<form:errors path="pictureUrl" cssClass="error"/>

		<input type="submit" name="save"
			value="<spring:message code="section.save" />"
			onclick="javascript: relativeRedir('${actionURI}');" />

		<input type="submit" name="delete"
			value="<spring:message code="section.delete" />"
			onclick="javascript: relativeRedir('${deleteURI}');" />

		<input type="button" name="cancel"
			value="<spring:message code="section.cancel" />"
			onclick="javascript: relativeRedir('${cancelURI}');" />
</form:form>