<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<link rel="stylesheet" href="styles/table.css" type="text/css">




<display:table
	name 		= "curriculas"
	id 			= "row"
	requestURI 	= "${requestURI}"
	pagesize	= "5"
	class		= "displaytag"
>

<display:column
	property	= "personalData.statement"
	titleKey	= "curricula.personalData.statement"
	sortable	= "true"
/>

<display:column>
					<acme:link link="curricula/hacker/display.do?curriculaId=${row.id}"
							code="curricula.display" />
				</display:column>

</display:table>

<acme:link code="curricula.create"
					link="curricula/hacker/create.do" />

