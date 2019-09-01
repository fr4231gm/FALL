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
	
<jstl:if test="${not empty Enrolresult}">
  	<h1><spring:message code="enrolment.${Enrolresult }" /></h1>
</jstl:if>

<display:table
	name 		= "brotherhoods"
	id 			= "row"
	requestURI 	= "${requestURI}"
	pagesize	= "5"
	class		= "displaytag"
>
<display:column
	property	= "title"
	titleKey	= "brotherhood.title"
	sortable	= "true"
/>

<display:column
	property	= "establishmentDate"
	titleKey	= "brotherhood.establishmentDate"
	sortable	= "true"
	format		= "{0, date,yyyy}"
/>
<security:authorize access="hasRole('MEMBER')">
	<jstl:if test="${enroll}">
		<display:column
			titleKey	= "brotherhood.enrolme"
		>
			<acme:link
				link= "enrolment/member/enroll.do?brotherhoodId=${row.id}"
				code= "brotherhood.enrolme"
			/>
		          
		</display:column>
	</jstl:if>
</security:authorize>	
	
<display:column
	titleKey	= "brotherhood.display"
>

	<acme:link
		link="brotherhood/display.do?brotherhoodId=${row.id}"
		code= "brotherhood.display"    
	/>
</display:column>

</display:table>
