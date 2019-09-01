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

<form:form modelAttribute="tutorial" action="${actionURI}" >
	<form:hidden path="lastesUpdate"/>
	<form:hidden path="sections"/>
	<form:hidden path="handyWorker"/>
	<form:hidden path="sponsorships"/>
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	
	<form:label path="title"><spring:message code="tutorial.title">:&nbsp;</spring:message></form:label>
	<form:input type="text" path="title"/><br/>
	<form:errors path="title" cssClass="error"/>

	<form:label path="summary"><spring:message code="tutorial.summary">:&nbsp;</spring:message></form:label>
	<form:textarea path="summary"/><br />
	<form:errors path="summary" cssClass="error"/>

	<form:label path="pictureUrl"><spring:message code="tutorial.picture">:&nbsp;</spring:message></form:label>
	<form:input type="text" path="pictureUrl" placeholder="<spring:message code ='tutorial.url'/>" /> <br/>
	<form:errors path="pictureUrl" cssClass="error"/>

		<input type="submit" name="save"
			value="<spring:message code="tutorial.save" />"
			onclick="javascript: relativeRedir('${actionURI}');" />
		<br />


		<input type="button" name="cancel"
			value="<spring:message code="tutorial.cancel" />"
			onclick="javascript: relativeRedir('${cancelURI}');" />
		<br />

</form:form>
