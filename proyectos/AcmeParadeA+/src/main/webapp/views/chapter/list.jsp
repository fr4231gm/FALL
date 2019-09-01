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
	name 		= "chapters"
	id 			= "row"
	requestURI 	= "${requestURI}"
	pagesize	= "5"
	class		= "displaytag"
>

<display:column
	property	= "title"
	titleKey	= "chapter.title"
	sortable	= "true"
/>

<display:column
	property	= "name"
	titleKey	= "chapter.name"
	sortable	= "true"
/>

<display:column
	property	= "middleName"
	titleKey	= "chapter.middle.name"
	sortable	= "true"
/>

<display:column
	property	= "surname"
	titleKey	= "chapter.surname"
	sortable	= "true"
/>

<display:column
	property	= "email"
	titleKey	= "chapter.email"
	sortable	= "true"
/>

<display:column
	titleKey	= "chapter.area.brotherhood"
>

	<acme:link
		link="area/displayByChapter.do?chapterId=${row.id}"
		code= "chapter.area.brotherhood"    
	/>
</display:column>

<display:column
	titleKey	= "chapter.brotherhoods"
>

	<acme:link
		link="brotherhood/listByChapter.do?chapterId=${row.id}"
		code= "chapter.brotherhoods"    
	/>
</display:column>

<display:column
	titleKey	= "chapter.proclaims"
>

	<acme:link
		link="proclaim/list.do?chapterId=${row.id}"
		code= "chapter.proclaims"    
	/>
</display:column>

	
<acme:cancel code="chapter.go.back" url="/"/>

</display:table>
