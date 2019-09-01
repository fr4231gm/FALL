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

<display:table
	name 		= "enrolments"
	id 			= "row"
	requestURI 	= "${requestURI}"
	pagesize	= "5"
	class		= "displaytag"
>
<display:column
	property	= "brotherhood.title"
	titleKey	= "enrolment.brotherhood"
	sortable	= "true"
/>
<display:column
	property	= "moment"
	titleKey	= "enrolment.moment"
	sortable	= "true"
	format		= "{0, date,dd / MM / yyyy}"
/>
<display:column
	property	= "dropOutMoment"
	titleKey	= "enrolment.dropOutMoment"
	sortable	= "true"
	format		= "{0, date,dd / MM / yyyy}"
/>

<jstl:if test="${language=='en'}">
<display:column 	
	titleKey	= "enrolment.position" 	
	sortable	= "true">
	${row.position.name['en']}
</display:column>
</jstl:if>
<jstl:if test="${language=='es'}">
<display:column 	
	titleKey	= "enrolment.position" 	
	sortable	= "true">
	${row.position.name['es']}
</display:column>
</jstl:if>



<security:authorize access="hasRole('BROTHERHOOD')">

	<display:column
		titleKey	= "enrolment.member">
			<acme:link
					link="member/display.do?memberId=${row.member.id}"
			    	code="enrolment.member.display"
			 	/>
	</display:column>

	<display:column
		titleKey	= "enrolment.assign">
	      <acme:link
				link="position/brotherhood/assign.do?enrolmentId=${row.id}"
		    	code="enrolment.assign"
		 	/>
	</display:column>
	
	<display:column
		titleKey = "enrolment.brotherhood.drop.out">
	   <acme:link
				link="enrolment/brotherhood/dropOut.do?enrolmentId=${row.id}"
		    	code="enrolment.brotherhood.drop.out"
		 	/>
	</display:column>
</security:authorize>

<security:authorize access="hasRole('MEMBER')">
<display:column titleKey = "enrolment.member.drop.out">
	<jstl:if test="${row.dropOutMoment eq null}">
		
			
			<acme:link
				link="enrolment/member/dropOut.do?enrolmentId=${row.id}"
		    	code="enrolment.member.drop.out"
		 	/>
		
	</jstl:if>
	</display:column>
	<%--<jstl:if test="${not empty row.dropOutMoment}">
		<display:column
			titleKey = "brotherhood.enrolme">
			<acme:link
				link= "enrolment/member/enroll.do?brotherhoodId=${row.id}"
				code= "brotherhood.enrolme"
			/> 
		</display:column>
	</jstl:if>--%>
	<display:column
		titleKey	= "brotherhood.display">
	    <a href="brotherhood/display.do?brotherhoodId=${row.brotherhood.id}"> 
	    	<spring:message code="brotherhood.display"></spring:message>
	    </a>
	</display:column>
</security:authorize>

</display:table>
