<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<acme:input code="workplan.ticker" path="workplan.ticker"
	readonly="true" />

<display:table name="phases" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="name" titleKey="phase.name" sortable="true" >
	</display:column>
	
	<display:column titleKey="phase.moment" sortable="true">
		<fmt:formatDate value="${ row.moment}" pattern="dd-MM-yyyy" />
	</display:column>
	
	<display:column property="number" titleKey="phase.number"
		sortable="true" />
	
	<display:column titleKey="phase.isDone" sortable="true">
		<jstl:if test="${row.isDone == 'true'}">
			<spring:message code="phase.done"></spring:message>
		</jstl:if>
		<jstl:if test="${row.isDone == 'false'}">
			<spring:message code="phase.notDone"></spring:message>
		</jstl:if>
	</display:column>

	<display:column>
		<jstl:if test="${row.isDone eq 'false' and row.isDoneable eq 'true'}">
			<acme:link link="phase/company/edit.do?phaseId=${row.id}"
				code="phase.edit" />
		</jstl:if>
	</display:column>
	
	<display:column>
		<acme:link link="phase/company/display.do?phaseId=${row.id}"
			code="phase.display" />
	</display:column>

</display:table>



<acme:back code="go.back" />

