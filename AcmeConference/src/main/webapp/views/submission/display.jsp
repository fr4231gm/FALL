<%--
 * display.jsp
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<fieldset><legend><spring:message code="author.submission" /></legend>
<acme:input code="submission.ticker" path="submission.ticker"
	readonly="true" />
	<br />

<acme:input code="submission.moment" path="submission.moment"
	readonly="true" />
	<br />
	


<h3><spring:message code="submission.status"></spring:message>: </h3>
<jstl:if test="${submission.status eq 'ACCEPTED' }"> <spring:message code="submission.accepted" /> </jstl:if><jstl:if test="${submission.status eq 'REJECTED'}"><spring:message code="submission.rejected"/></jstl:if><jstl:if test="${submission.status eq 'UNDER-REVIEW' }"><spring:message code="submission.underreview" /></jstl:if>

</fieldset>

<fieldset><legend><spring:message code= "author.submission.paper"></spring:message></legend>


<acme:input code="paper.title" path="submission.paper.title"
	readonly="true"/>
	<br />	

<acme:textarea code="paper.summary" path="submission.paper.summary"
	readonly="true" />
	<br />	

<acme:input code="paper.document" path="submission.paper.document"
	readonly="true" />
	<br />	

<spring:message code="submission.cameraReadyPaper"> </spring:message>:        
<jstl:if test="${submission.paper.cameraReadyPaper eq 'true'}">
<spring:message code="boolean.true"></spring:message>
</jstl:if>
<jstl:if test="${submission.paper.cameraReadyPaper eq 'false'}">
<spring:message code="boolean.false"></spring:message>
</jstl:if>

</fieldset>
	
	<br />		

<security:authorize access="hasRole('ADMINISTRATOR')">
	<table>
		<tr>
			<jstl:if test="${asignable eq 'true'}">
				<td style="width:25%; vertical-align:middle;text-align: center;"><h3><acme:link link="submission/administrator/assign.do?submissionId=${submission.id}" code="submission.assign.reviewer" /></h3></td>
				<td style="width:25%; vertical-align:middle;text-align: center;"><h3><acme:link link="submission/administrator/autoassign.do?submissionId=${submission.id}" code = "submission.autoassign"/></h3></td>
			</jstl:if>
			
			<jstl:if test="${asignable eq 'false'}">
				<td style="width:25%; vertical-align:middle;text-align: center; opacity:0.5;"><h3><spring:message code="submission.assign.reviewer" /></h3></td>
				<td style="width:25%; vertical-align:middle;text-align: center; opacity:0.5;"><h3><spring:message code = "submission.autoassign"/></h3></td>
			</jstl:if>
			
			<jstl:if test="${decide eq 'true'}">
				<td style="width:25%; vertical-align:middle;text-align: center;"><h3><acme:link link="submission/administrator/decide.do?submissionId=${submission.id}" code="submission.decide" /></h3></td>
			</jstl:if>
			
			<jstl:if test="${decide eq 'false'}">
				<td style="width:25%; vertical-align:middle;text-align: center; opacity:0.5;"><h3><spring:message code="submission.decide" /></h3></td>
			</jstl:if>
		
			<jstl:if test="${notificable eq 'true'}">
				<td style="width:25%; vertical-align:middle;text-align: center;"><h3><acme:link link="message/notify.do?submissionId=${submission.id}" code = "submission.notify"/></h3></td>
			</jstl:if>
		
			<jstl:if test="${notificable eq 'false'}">
				<td style="width:25%; vertical-align:middle;text-align: center; opacity:0.5;"><h3><spring:message code = "submission.notify"/></h3></td>
			</jstl:if>
		<tr>
	</table>
<br />	<br />	
</security:authorize>

<acme:back code="conference.goback"/>