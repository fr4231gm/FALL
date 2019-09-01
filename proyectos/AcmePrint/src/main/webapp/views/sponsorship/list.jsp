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

	<display:column property="post.title" titleKey="sponsorship.post"
		sortable="true" class="${colorClass}" />

	<display:column property="cost" titleKey="sponsorship.cost"
		sortable="true" />

	<display:column titleKey="sponsorship.isEnabledor">
		<jstl:if test="${row.isEnabled == 'false'}">
			<spring:message code="sponsorship.isEnabled.false"></spring:message>
		</jstl:if>
		<jstl:if test="${row.isEnabled == 'true'}">
			<spring:message code="sponsorship.isEnabled.true"></spring:message>
		</jstl:if>
	</display:column>
	<display:column titleKey="sponsorship.display">
		<acme:link
			link="sponsorship/provider/display.do?sponsorshipId=${row.id}"
			code="sponsorship.display" />
	</display:column>

	<display:column titleKey="sponsorship.edit">
		<acme:link link="sponsorship/provider/edit.do?sponsorshipId=${row.id}"
			code="sponsorship.edit" />
	</display:column>
	
	<display:column titleKey="sponsorship.isEnabledor">
		<jstl:if test="${row.isEnabled eq true}">
			<acme:link
				link="sponsorship/provider/desactivate.do?sponsorshipId=${row.id}"
				code="sponsorship.isEnabled.false" />
		</jstl:if>
		<jstl:if test="${row.isEnabled eq false}">
			<acme:link
				link="sponsorship/provider/activate.do?sponsorshipId=${row.id}"
				code="sponsorship.isEnabled.true" />
		</jstl:if>
	</display:column>

</display:table>

<acme:link code="sponsorship.create"
	link="sponsorship/provider/create.do" />
<acme:cancel code="sponsorship.go.back" url="/" />