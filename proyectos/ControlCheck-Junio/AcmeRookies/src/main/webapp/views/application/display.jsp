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
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!-- <link rel="stylesheet" href="styles/table.css" type="text/css">-->
	
<acme:input code="application.creationMoment" path="application.creationMoment"
	readonly="true" />
<acme:input code="application.answer" path="application.answer"
	readonly="true" />
<acme:input code="application.linkCode" path="application.linkCode"
	readonly="true" />
	
<spring:message code="application.status"></spring:message>
:<jstl:if test="${application.status eq 'SUBMITTED'}">
<spring:message code="application.submitted"></spring:message>
</jstl:if>
<jstl:if test="${application.status eq 'ACCEPTED'}">
<spring:message code="application.accepted"></spring:message>
</jstl:if>
<jstl:if test="${application.status eq 'REJECTED'}">
<spring:message code="application.rejected"></spring:message>
</jstl:if>

<acme:input code="application.submittedMoment"
	path="application.submittedMoment" readonly="true" />
	
<h3><spring:message code="application.curricula"></spring:message></h3>

<!-- CURRICULA -->

<spring:message code="curricula.propietaryOf" var="propietary" />
<h3>
	<jstl:out
		value="${propietary} ${curricula.rookie.userAccount.username}">
	</jstl:out>
</h3>

<table class="displayStyle">

	<tr>
		<td><strong> <spring:message code="curricula.propietary" />:
		</strong></td>
		<td>${curricula.rookie.name}</td>
	</tr>

	<tr>
		<td><strong> <spring:message
					code="curricula.personalData" />:
		</strong></td>
		<td>
			<table class="displayStyle">
				<tr>
					<th><strong> <spring:message
								code="curricula.personalData" /></strong></th>
					<th></th>
				</tr>

				<tr>
					<td><strong> <spring:message
								code="curricula.personalData.fullName" />:
					</strong></td>
					<td><jstl:out value="${curricula.personalData.fullName}" /></td>
				</tr>

				<tr>
					<td><strong> <spring:message
								code="curricula.personalData.statement" />:
					</strong></td>
					<td><jstl:out value="${curricula.personalData.statement}" /></td>
				</tr>
				
				<tr>
					<td><strong> <spring:message
								code="curricula.personalData.phoneNumber" />:
					</strong></td>
					<td><jstl:out value="${curricula.personalData.phoneNumber}" /></td>
				</tr>
				
				<tr>
					<td><strong> <spring:message
								code="curricula.personalData.GitHubLink" />:
					</strong></td>
					<td><jstl:out value="${curricula.personalData.gitHubLink}" /></td>
				</tr>
				
				<tr>
					<td><strong> <spring:message
								code="curricula.personalData.LinkedInLink" />:
					</strong></td>
					<td><jstl:out value="${curricula.personalData.linkedInLink}" /></td>
				</tr>
			</table>
		</td>
	</tr>

	<jstl:if test="${ not empty curricula.educationData}">
		<jstl:set var="countPR" value="1" />
		<tr>
			<td><strong> <spring:message
						code="curricula.educationDatas" />:
			</strong></td>
			<td><display:table name="curricula.educationData" id="row2"
					class="displayTag">
					<display:column property="degree" titleKey="curricula.educationData.degree"
						sortable="true" />
					<display:column property="institution"
						titleKey="curricula.educationData.institution" sortable="true" />
					<display:column property="startDate"
						titleKey="curricula.educationData.startDate" sortable="true" />
					<display:column property="endDate"
						titleKey="curricula.educationData.endDate" sortable="true" />
					<display:column property="mark"
						titleKey="curricula.educationData.mark" sortable="true" />

				</display:table></td>
		</tr>
	</jstl:if>
	
	<jstl:if test="${ not empty curricula.positionData}">
		<jstl:set var="countPD" value="1" />
		<tr>
			<td><strong> <spring:message
						code="curricula.positionDatas" />:
			</strong></td>
			<td><display:table name="curricula.positionData" id="row3"
					class="displayTag">
					<display:column property="title" titleKey="curricula.positionData.title"
						sortable="true" />
					<display:column property="description"
						titleKey="curricula.positionData.description" sortable="true" />
					<display:column property="startDate"
						titleKey="curricula.positionData.startDate" sortable="true" />
					<display:column property="endDate"
						titleKey="curricula.positionData.endDate" sortable="true" />
					
					<jstl:if
						test="${curricula.rookie == principal and curricula.id!=0}">
						<display:column titleKey="curricula.editPD" sortable="true">
							<input type="button" name="edit"
								value="<spring:message code="curricula.editPD" />"
								onclick="redirect: location.href = 'positionData/rookie/edit.do?positionDataId=${row3.id}';" />
						</display:column>
					</jstl:if>
				</display:table></td>
		</tr>
	</jstl:if>
	


	<%-- Igual para MiscellaneousData --%>
	<jstl:if test="${ not empty curricula.miscellaneousData}">
		<jstl:set var="countMR" value="1" />
		<tr>
			<td><strong> <spring:message
						code="curricula.miscellaneousDatas" />:
			</strong></td>
			<td><display:table name="curricula.miscellaneousData" id="row"
					class="displayTag">
					<display:column property="text" titleKey="curricula.miscellaneousData.text"
						sortable="true" />
					<display:column property="attachments"
						titleKey="curricula.miscellaneousData.attachments" sortable="true" />
				</display:table></td>
		</tr>
	</jstl:if>

</table>


<script>
$(document).ready( function () {	
    $('#row').dataTable( {
    	"language": {
        	"url": '${tableLang}'
    	},
		"lengthMenu": [ 3, 5, 10, 25, 50 ]
    } );
} );
</script>
<script>
$(document).ready( function () {	
    $('#row2').dataTable( {
    	"language": {
        	"url": '${tableLang}'
    	},
		"lengthMenu": [ 3, 5, 10, 25, 50 ]
    } );
} );
</script>
<script>
$(document).ready( function () {	
    $('#row3').dataTable( {
    	"language": {
        	"url": '${tableLang}'
    	},
		"lengthMenu": [ 3, 5, 10, 25, 50 ]
    } );
} );
</script>
	
<security:authorize access="hasRole('COMPANY')">
	
	<jstl:if test="${permiso == true }">
		
		<acme:input code="application.rookie"
	path="application.rookie.userAccount.username" readonly="true" />
	
		<acme:input code="application.position"
		path="application.position.title" readonly="true" />
		
		<acme:input code="application.problem"
		path="application.problem.title" readonly="true" />
	
		<acme:cancel url="application/company/list.do" code="application.goback" />
	
	</jstl:if>
	
</security:authorize>

<security:authorize access="hasRole('ROOKIE')">
	
	<jstl:if test="${permiso == true }">
	
		<acme:cancel url="application/rookie/list.do" code="application.goback" />
	
	</jstl:if>
	
</security:authorize>