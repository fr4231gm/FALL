<%--
 * action-1.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<display:table name="floats" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">


	<display:column property="title" titleKey="float.title" sortable="true" />

	<display:column property="description" titleKey="float.description"
		sortable="true" />

	<display:column titleKey="float.display">
		<acme:link link="float/display.do?floatId=${row.id}"
			code="float.display" />
	</display:column>
	
	<security:authorize access="hasRole('BROTHERHOOD')">
		<display:column titleKey="float.edit">
			<jstl:if test="${permiso==true}">
				<acme:link link="float/brotherhood/edit.do?floatId=${row.id}"
					code="float.edit" />

				<acme:link link="float/brotherhood/delete.do?floatId=${row.id}"
					code="float.delete" />
			</jstl:if>
		</display:column>
	</security:authorize>
	
</display:table>

<security:authorize access="hasRole('BROTHERHOOD')">
	<jstl:if test="${permiso==true}">
		<acme:link code="float.create" link="float/brotherhood/create.do" />
	</jstl:if>
</security:authorize>
<acme:cancel code="float.go.back" url="/"/>