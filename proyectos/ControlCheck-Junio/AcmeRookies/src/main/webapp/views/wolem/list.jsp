<%--
 * 
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="styles/publicationMoment.css" type="text/css">
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />

<display:table
	name 		= "wolems"
	id 			= "row"
	requestURI 	= "${requestURI}"
	pagesize	= "5"
	class		= "displaytag"
>

	<display:column
		property	= "ticker"
		titleKey	= "wolem.ticker"
		sortable	= "true"
	/>

	<jstl:choose>
	
		<jstl:when test="${row.publicationMoment.time gt oneMonth.time}">
			<display:column
				titleKey	= "wolem.publicationMoment"
				sortable	= "true"
				format		= "${format}"
				property	= "publicationMoment"
				class		= "lessOneMonthOld"/>
		</jstl:when>
		
		<jstl:when test="${row.publicationMoment.time lt oneMonth.time and row.publicationMoment.time gt twoMonths.time}">
			<display:column
				titleKey	= "wolem.publicationMoment"
				sortable	= "true"
				format		= "${format}"
				property	= "publicationMoment"
				class		= "betweenOneAndTwoMonthOld"/>
		</jstl:when>
		
		<jstl:when test="${row.publicationMoment.time lt twoMonths.time}">
			<display:column
				titleKey	= "wolem.publicationMoment"
				sortable	= "true"
				format		= "${format}"
				property	= "publicationMoment"
				class		= "moreTwoMonthOld"/>
		</jstl:when>
		
		<jstl:otherwise>
			<display:column
				titleKey	= "wolem.publicationMoment"
				sortable	= "true"
				property	= "publicationMoment"
			/>
		</jstl:otherwise>
		
	</jstl:choose>

	<display:column
		property	= "body"
		titleKey	= "wolem.body"
		sortable	= "true"
	/>

	<display:column
		titleKey	= "wolem.picture"
		sortable	= "true">
			<jstl:if test="${not empty row.picture}">
				<img src = "${row.picture}" style="width: 120px; height:100px;" alt="picture not found"/>
			</jstl:if>
	</display:column>

	<display:column titleKey="wolem.company">
		<acme:link 
			link="company/display.do?companyId=${row.audit.position.company.id}"
			code="wolem.company" />
	</display:column>

	<security:authorize access="hasRole('AUDITOR')">
	<display:column titleKey="wolem.show">
		<acme:link 
			link="wolem/auditor/display.do?wolemId=${row.id}" 
			code="wolem.display"
		/>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('COMPANY')">
	<display:column titleKey="wolem.show">
		<acme:link 
			link="wolem/company/display.do?wolemId=${row.id}" 
			code="wolem.display"
		/>
	</display:column>
	</security:authorize>


	<security:authorize access="hasRole('COMPANY')">
	
		<display:column titleKey="wolem.isDraft" sortable="true">
			<jstl:if test="${row.isDraft == 'true'}">
				<spring:message code="actor.yes"></spring:message>
			</jstl:if>
			<jstl:if test="${row.isDraft == 'false'}">
				<spring:message code="actor.no"></spring:message>
			</jstl:if>
		</display:column>	
		
		<jstl:if test="${company.id eq row.audit.position.company.id}"> 
			<display:column titleKey="wolem.delete">
				<jstl:if test="${row.isDraft}">
					<acme:link link="wolem/company/delete.do?wolemId=${row.id}"
						code="wolem.delete" />
					</jstl:if>	  
			</display:column>
		</jstl:if>
		
		<jstl:if test="${company.id eq row.audit.position.company.id}"> 
			<display:column titleKey="wolem.edit">
				<jstl:if test="${row.isDraft}">
					<acme:link link="wolem/company/edit.do?wolemId=${row.id}"
						code="wolem.edit" />
					</jstl:if>	  
			</display:column>
		</jstl:if>
	</security:authorize>
</display:table>


<acme:back code="wolem.go.back"/>


