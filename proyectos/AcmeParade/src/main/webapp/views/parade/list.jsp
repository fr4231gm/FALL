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

<link rel="stylesheet" href="styles/statusParade.css" type="text/css">
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<!-- Aqui deberia controlar que si isDraft entonces no se
 puede mostrar a Brotherhoods que no le correspondan -->

<display:table name="parades" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="moment" titleKey="parade.moment"
		sortable="true" format="{0, date,dd / MM / yyyy HH:mm}" />

	<jstl:choose>

		<jstl:when test="${row.status eq ''}">
			<display:column titleKey="parade.status"
				sortable="true" class="" >-
			</display:column>
		</jstl:when>

		<jstl:when test="${row.status eq 'SUBMITTED'}">
			<display:column titleKey="parade.status"
				sortable="true" class="submitted" ><spring:message code="parade.submitted"/>
			</display:column>
		</jstl:when>

		<jstl:when test="${row.status eq 'ACCEPTED'}">
			<display:column titleKey="parade.status"
				sortable="true" class="accepted"><spring:message code="parade.accepted"/>
			</display:column>
		</jstl:when>

		<jstl:when test="${row.status eq 'REJECTED'}">
			<display:column titleKey="parade.status"
				sortable="true" class="rejected"><spring:message code="parade.rejected"/>
			</display:column>
		</jstl:when>

	</jstl:choose>

	<display:column property="rejectReason" titleKey="parade.rejectReason"
		sortable="true" />

	<display:column property="title" titleKey="parade.title"
		sortable="true" />

	<display:column property="description" titleKey="parade.description"
		sortable="true" />

	<display:column titleKey="parade.segment">
		<acme:link link="segment/brotherhood/list.do?paradeId=${row.id}"
			code="parade.segment" />
	</display:column>

	<display:column titleKey="parade.display">
		<acme:link link="parade/display.do?paradeId=${row.id}"
			code="parade.display" />
	</display:column>

	<security:authorize access="hasRole('BROTHERHOOD')">

		<display:column titleKey="parade.copy">
			<a href="parade/brotherhood/copy.do?paradeId=${row.id}"> <spring:message
					code="parade.copy" />
			</a>
		</display:column>

	</security:authorize>

	<security:authorize access="hasRole('BROTHERHOOD')">

		<display:column titleKey="parade.edit">
			<jstl:if test="${row.isDraft==true}">
				<jstl:if test="${permiso==true}">

					<acme:link link="parade/brotherhood/edit.do?paradeId=${row.id}"
						code="parade.edit" />&nbsp
		 		      <acme:link
						link="parade/brotherhood/delete.do?paradeId=${row.id}"
						code="parade.delete" />
					<acme:link link="parade/brotherhood/copy.do?paradeId=${parade.id}" 
	code="parade.copy" />
				</jstl:if>
			</jstl:if>

		</display:column>

	</security:authorize>
	<security:authorize access="hasRole('CHAPTER')">

		<display:column titleKey="parade.decide">
			<jstl:if test="${row.status == 'SUBMITTED'}">
				<acme:link link="chapter/decide.do?paradeId=${row.id}"
					code="parade.decide" />
			</jstl:if>
		</display:column>

	</security:authorize>

	<security:authorize access="hasRole('MEMBER')">
		<jstl:if test="${permiso==true}">
			<display:column titleKey="parade.request">

				<acme:link link="request/member/create.do?paradeId=${row.id}"
					code="parade.request" />
			</display:column>
		</jstl:if>
	</security:authorize>

</display:table>
<security:authorize access="hasRole('BROTHERHOOD')">

	<acme:link link="parade/brotherhood/create.do" code="parade.create" />
</security:authorize>

<acme:cancel code="parade.go.back" url="/"/>

