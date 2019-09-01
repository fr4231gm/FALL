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

<display:table name="sections" id="row" pagesize="5"
	class="displaySection" requestURI="${actionURI}">

	<display:column property="<spring:message code='section.title' />"
		titleKey="section.title" />

	<display:column property="<spring:message code='section.text' />"
		titleKey="section.text" />

	<display:column property="<spring:message code='section.picture' />"
		titleKey="section.pictureUrl" />

	<display:column property="<spring:message code='section.indice' />"
		titleKey="section.indice" />

	<security:authorize access="hasRole('HANDYWORKER')">
	<display:column>
		<a href="section/handyworker/edit.do?sectionId=${row.id}"><spring:message
				code="section.edit" /></a>
	</display:column>
	</security:authorize>
	
</display:table>

	<security:authorize access="hasRole('HANDYWORKER')">	
	<a href="section/handyworker/create.do"><spring:message
					code="section.create" /></a>
	</security:authorize>
	
<input type="button" name="back" value="<spring:message code="section.backtutorial" />"
        onclick="javascript: relativeRedir('${actionURI}');" />
