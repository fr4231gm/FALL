<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="styles/table.css" type="text/css">


<spring:message code="curricula.propietaryOf" var="propietary" />
<h3>
	<jstl:out
		value="${propietary} ${curricula.hacker.userAccount.username}">
	</jstl:out>
</h3>

<%-- Botones para añadir Datos al curricula --%>
<jstl:if test="${curricula.hacker.id == principal.id }">
	<table class="displayStyle">
		<tr>
			<th colspan="4"><spring:message
					code="curricula.buttonsCreationRecords" /></th>
		</tr>
		<tr>
			<td><input type="button" name="addMR"
				value="<spring:message code="curricula.addMR" />"
				onclick="redirect: location.href = 'miscellaneousData/hacker/create.do?curriculaId=${curricula.id}';" />
			</td>

			<td><input type="button" name="addER"
				value="<spring:message code="curricula.addER" />"
				onclick="redirect: location.href = 'educationData/hacker/create.do?curriculaId=${curricula.id}';" />
			</td>
			<td><input type="button" name="addPD"
				value="<spring:message code="curricula.addPD" />"
				onclick="redirect: location.href = 'positionData/hacker/create.do?curriculaId=${curricula.id}';" />
			</td>
		</tr>

	</table>
</jstl:if>


<table class="displayStyle">


	<tr>
		<th><jstl:out
				value="${propietary} ${curricula.hacker.userAccount.username}">
			</jstl:out></th>
		<th></th>
	</tr>

	<tr>
		<td><strong> <spring:message code="curricula.propietary" />:
		</strong></td>
		<td>${curricula.hacker.name}</td>
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

				<jstl:if
					test="${curricula.hacker == principal and curricula.id!=0}">
					<tr>
						<td colspan="2"><br> <input type="button" name="edit"
							value="<spring:message code="curricula.personalData.edit" />"
							onclick="redirect: location.href = 'curricula/hacker/edit.do?curriculaId=${curricula.id}';" />
						</td>
					</tr>
				</jstl:if>
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

					<jstl:if
						test="${curricula.hacker == principal and curricula.id!=0}">
						<display:column titleKey="curricula.editER" sortable="true">
							<input type="button" name="edit"
								value="<spring:message code="curricula.editER" />"
								onclick="redirect: location.href = 'educationData/hacker/edit.do?educationDataId=${row2.id}';" />
						</display:column>
					</jstl:if>
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
						test="${curricula.hacker == principal and curricula.id!=0}">
						<display:column titleKey="curricula.editPD" sortable="true">
							<input type="button" name="edit"
								value="<spring:message code="curricula.editPD" />"
								onclick="redirect: location.href = 'positionData/hacker/edit.do?positionDataId=${row3.id}';" />
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
					<jstl:if
						test="${curricula.hacker == principal and curricula.id!=0}">
						<display:column titleKey="curricula.editMR" sortable="true">
							<input type="button" name="edit"
								value="<spring:message code="curricula.editMR" />"
								onclick="redirect: location.href = 'miscellaneousData/hacker/edit.do?miscellaneousDataId=${row.id}';" />
						</display:column>
					</jstl:if>
				</display:table></td>
		</tr>
	</jstl:if>

</table>

<div>
	<jstl:if test="${curricula.hacker == principal}">
		<jstl:if test="${curricula.id!=0}">
			<input type="button" name="edit"
				value="<spring:message code="curricula.edit" />"
				onclick="redirect: location.href = 'curricula/hacker/edit.do?curriculaId=${curricula.id}';" />
		</jstl:if>
	</jstl:if>
</div>


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


</html>

