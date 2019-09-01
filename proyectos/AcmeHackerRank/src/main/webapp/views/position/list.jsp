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



<jstl:if test="${general eq 'true'}">
<form name="searchForm" action="position/listSearch.do" method="get" >
    <input type="text" name="keyword">  
    <input type="submit" value="<spring:message code="position.search"/>">
</form>
</jstl:if>

<display:table
	name 		= "positions"
	id 			= "row"
	requestURI 	= "${requestURI}"
	pagesize	= "5"
	class		= "displaytag"
>

<display:column
	property	= "title"
	titleKey	= "position.title"
	sortable	= "true"
/>

<display:column
	property	= "description"
	titleKey	= "position.description"
	sortable	= "true"
/>

<display:column
	property	= "ticker"
	titleKey	= "position.ticker"
	sortable	= "true"
/>

<display:column
	property	= "title"
	titleKey	= "position.title"
	sortable	= "true"
/>

<display:column
	property	= "deadline"
	titleKey	= "position.deadline"
	sortable	= "true"
	format		="{0, date,dd / MM / yyyy HH:mm}"
/>	

<display:column
	property	= "profile"
	titleKey	= "position.profile"
	sortable	= "true"
/>

<display:column
	property	= "skills"
	titleKey	= "position.skills"
	sortable	= "true"
/>

<display:column
	property	= "technologies"
	titleKey	= "position.technologies"
	sortable	= "true"
/>

<display:column
	property	= "salary"
	titleKey	= "position.salary"
	sortable	= "true"
/>

<display:column titleKey="position.company">
		<acme:link link="company/display.do?positionId=${row.id}"
			code="position.company" />
</display:column>


<display:column titleKey="position.show">
		<acme:link link="position/display.do?positionId=${row.id}" 
			code="position.show"/>
</display:column>

<security:authorize access="hasRole('HACKER')">
		
			
	<display:column>
	
		<acme:link link="application/hacker/create.do?positionId=${row.id}"
				code="position.application.create" />

	</display:column>


</security:authorize>

<security:authorize access="hasRole('COMPANY')">

		<display:column titleKey="position.edit">
			
				<jstl:if test="${permiso==true}"> 
				
					 <jstl:if test="${row.isDraft==true}"> 
					<acme:link link="position/company/edit.do?positionId=${row.id}"
						code="position.edit" />&nbsp;
					 </jstl:if> 
						
		 		
						
				 </jstl:if>
		  
		</display:column>
		
		
		<display:column titleKey="position.delete">
			
				<jstl:if test="${permiso==true}"> 
				
					<jstl:if test="${row.isDraft==true}">
					 <acme:link link="position/company/delete.do?positionId=${row.id}"
						code="position.delete" />
						</jstl:if>
						
		 		   
						
				 </jstl:if>
		  
		</display:column>
		
		
		<display:column titleKey="position.cancel">
			
				<jstl:if test="${!row.isDraft}"> 

					 <jstl:if test="${row.isCancelled eq 'false'}">
						 	<acme:link link="position/company/cancel.do?positionId=${row.id}"
							code="position.cancel" />&nbsp;
					 </jstl:if> 

				 </jstl:if>
		  
		</display:column>

</security:authorize>
</display:table>


<security:authorize access="hasRole('COMPANY')">
<acme:link link="position/company/create.do" code="position.create" />
</security:authorize>

<acme:back code="position.go.back"/>


