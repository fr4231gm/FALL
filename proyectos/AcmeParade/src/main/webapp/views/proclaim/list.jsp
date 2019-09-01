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

<script>
function myFunction(id) {
	var a = parseInt(id);
	if (confirm('<spring:message code="proclaim.confirm.publish" />')) window.location.href='proclaim/chapter/publish.do?proclaimId='+a;
}
</script>

<link rel="stylesheet" href="styles/onclick.css" type="text/css">

<display:table name="proclaims" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<display:column property="text" titleKey="proclaim.text"
		sortable="true" />
	
	<display:column property="moment" titleKey="proclaim.moment"
		sortable="true" format="{0, date,dd / MM / yyyy HH:mm}" />

	<security:authorize access="hasRole('CHAPTER')">
		<jstl:if test="${permiso==true}">
			
				<display:column>
				
					<jstl:if test="${empty row.moment}">
					
						<acme:link link="proclaim/chapter/edit.do?proclaimId=${row.id}"
							code="proclaim.edit" />
						
					</jstl:if>
				</display:column>
				
				<display:column>
				
					<jstl:if test="${empty row.moment}">
				<%-- 	<input 
							type="submit" 
							name="publish" 
							value="<spring:message code="proclaim.publish" />"
							onclick="if (confirm('<spring:message code="proclaim.confirm.publish" />')) window.location.href='proclaim/chapter/publish.do?proclaimId=${row.id}'" />		
						--%>
						
						<acme:onclick 
							code="proclaim.publish"
							name="publish"
							onclick="myFunction(${row.id})"
						/>
					</jstl:if>
				</display:column>
				
				<display:column>
				
					<jstl:if test="${empty row.moment}">
						<acme:link link="proclaim/chapter/delete.do?proclaimId=${row.id}"
							code="proclaim.delete" />
					</jstl:if>
				</display:column>
			
		</jstl:if>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('CHAPTER')">
	<jstl:if test="${permiso==true}">
		<acme:link code="proclaim.create" link="proclaim/chapter/create.do" />
	</jstl:if>
</security:authorize>

<br>
<br>

<acme:back code="proclaim.goback" />