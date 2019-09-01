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

<!-- Aqui deberia controlar que si isDraft entonces no se
 puede mostrar a Brotherhoods que no le correspondan -->

<display:table name="processions" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

<display:column
	property	= "moment"
	titleKey	= "procession.moment"
	sortable	= "true"
	format		= "{0, date,dd / MM / yyyy HH:mm}"
/>

	<display:column property="title" titleKey="procession.title"
		sortable="true" />

	<display:column property="description"
		titleKey="procession.description" sortable="true" />


	<display:column
		titleKey	= "procession.display">
	      <acme:link
				link="procession/display.do?processionId=${row.id}"
		    	code="procession.display"
		 	/>
		 	
	</display:column>

	<security:authorize access="hasRole('BROTHERHOOD')">
	
	<display:column
		titleKey	= "procession.edit">
			<jstl:if test="${row.isDraft==true}">
			<jstl:if test="${permiso==true}">

	      <acme:link
				link="procession/brotherhood/edit.do?processionId=${row.id}"
		    	code="procession.edit"
		 	/>&nbsp
		 		      <acme:link
				link="procession/brotherhood/delete.do?processionId=${row.id}"
		    	code="procession.delete"
		 	/>
		 			</jstl:if>
		 			</jstl:if>

	</display:column>

</security:authorize>

<security:authorize access="hasRole('MEMBER')">
	<jstl:if test="${permiso==true}">
	<display:column
		titleKey	= "procession.request">

	      <acme:link
				link="request/member/create.do?processionId=${row.id}"
		    	code="procession.request"
		 	/>
	</display:column>
	</jstl:if>
</security:authorize>

</display:table>
<security:authorize access="hasRole('BROTHERHOOD')">

	<acme:link link="procession/brotherhood/create.do"
		code="procession.create" />
</security:authorize>
<security:authorize access="hasRole('BROTHERHOOD')">
	<acme:back code="procession.go.back" />
</security:authorize>
<security:authorize access="hasRole('MEMBER')">
	<acme:cancel url="/" code="procession.go.welcome" />
</security:authorize>