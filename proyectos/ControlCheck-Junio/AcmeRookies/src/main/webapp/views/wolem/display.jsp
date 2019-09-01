<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<link rel="stylesheet" href="styles/formulario.css" type="text/css">

<fmt:formatDate value="${wolem.publicationMoment}" pattern="${format}"
	var="parsedDate" />
	
<jstl:choose>
	
		<jstl:when test="${wolem.publicationMoment.time gt oneMonth.time}">
			<jstl:set var="color" value="Cornsilk"/>
		</jstl:when>
		
		<jstl:when test="${wolem.publicationMoment.time lt oneMonth.time and wolem.publicationMoment.time gt twoMonths.time}">
			<jstl:set var="color" value="Moccasin"/>
		</jstl:when>
		
		<jstl:otherwise>
			<jstl:set var="color" value="Tomato"/>
		</jstl:otherwise>
</jstl:choose>
<div style="float: left; width: 100%; background-color: ${color}">

	<div style="float: left; width: 50%; margin-top: 90px; margin-left: 20px;">
		<acme:input code="wolem.ticker" path="wolem.ticker" readonly="true" /> <br />

		<form:label path="wolem.publicationMoment"> <spring:message code="wolem.publicationMoment" /> </form:label>
		<form:input code="wolem.publicationMoment" path="wolem.publicationMoment" value="${parsedDate}" readonly="true" /> <br />

		<acme:input code="wolem.body" path="wolem.body" readonly="true" /> <br />
		<form:label path="wolem.isDraft"> <spring:message code="wolem.isDraft" /> </form:label>
		
		<jstl:if test="${wolem.isDraft}">
				<spring:message code="actor.yes"></spring:message>
			</jstl:if>
			<jstl:if test="${wolem.isDraft == 'false'}">
				<spring:message code="actor.no"></spring:message>
			</jstl:if>
	
		
		<div style="text-align: center ">

				<acme:link code="wolem.audit" link="audit/display.do?auditId=${wolem.audit.id}"/>

				<acme:back code="wolem.go.back" />

		</div>
	</div>
	
	<div style="margin-top: 20px;">
	
		<jstl:if test="${not empty wolem.picture}">
			<img src = "${wolem.picture}" style="width: 300px; height:300px; margin-bottom: 20px;" alt="picture not found"/>
		</jstl:if>

</div>

</div>
