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

<link rel="stylesheet" href="styles/textMessageColor.css"
	type="text/css">

<display:table name="sponsorships" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="banner" titleKey="sponsorship.banner"
		sortable="true" class="${colorClass}" />



	
	<display:column titleKey="sponsorship.display">
		<acme:link
			link="sponsorship/sponsor/display.do?sponsorshipId=${row.id}"
			code="sponsorship.display" />
	</display:column>

	<display:column titleKey="sponsorship.edit">
		<acme:link link="sponsorship/sponsor/edit.do?sponsorshipId=${row.id}"
			code="sponsorship.edit" />
	</display:column>
	


</display:table>

<acme:link code="sponsorship.create"
	link="sponsorship/sponsor/create.do" />
<acme:cancel code="sponsorship.go.back" url="/" />