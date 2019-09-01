<%--
 * edit.jsp
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

<form:form modelAttribute="request">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="member" />
	<form:hidden path="procession" />
	
	<form:select path="status" disabled="false">
		<form:option value="PENDING">
			<spring:message code="request.pending"></spring:message>
		</form:option>
			<form:option value="APPROVED">
				<spring:message code="request.approved"></spring:message>
			</form:option>
			<form:option value="REJECTED">
				<spring:message code="request.rejected"></spring:message>
		</form:option>
	</form:select>
	<acme:input code="request.row" path="rowRequest"/>
	<acme:input code="request.column" path="columnRequest"/>
	<acme:textarea code="request.rejectReason" path="rejectReason"/>
	
<security:authorize access="hasRole('MEMBER')">
	<acme:submit name="delete" code="request.delete"/>
</security:authorize>
	<acme:submit name="save" code="request.save"/>
	<acme:cancel url="/" code="request.cancel"/>	
</form:form>

