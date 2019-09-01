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
	name 		= "areas"
	id 			= "row"
	requestURI 	= "${requestURI}"
	pagesize	= "5"
	class		= "displaytag"
>
<display:column
	property	= "name"
	titleKey	= "area.name"
	sortable	= "true"
/>

<display:column
	property	= "pictures"
	titleKey	= "area.pictures"
	sortable	= "true"
/>

<display:column
	titleKey	= "area.display"
>

	<acme:link
		link="area/display.do?areaId=${row.id}"
		code= "area.display"    
	/>
</display:column>


<security:authorize access="hasRole('BROTHERHOOD')">
<display:column
	titleKey	= "area.assign"
>
<acme:link
		link="area/brotherhood/assign.do?areaId=${row.id}"
		code= "area.assign"    
	/>
	</display:column></security:authorize>
	
	<security:authorize access="hasRole('CHAPTER')">
<display:column
	titleKey	= "area.assign"
>
<acme:link
		link="chapter/assign.do?areaId=${row.id}"
		code= "area.assign"    
	/>
	</display:column></security:authorize>
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
<display:column
	titleKey	= "area.edit"
>
<acme:link
		link="area/administrator/edit.do?areaId=${row.id}"
		code= "area.edit"    
	/>
	</display:column></security:authorize>


</display:table>

<security:authorize access="hasRole('ADMINISTRATOR')">

<acme:link
		link="area/administrator/create.do"
		code= "area.create"    
	/>
	</security:authorize>
