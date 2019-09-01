<%--
 * action-2.jsp
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

<acme:image src="${brotherhood.photo}" />

<acme:input code="brotherhood.name" path="brotherhood.name"
	readonly="true" />
<acme:input code="brotherhood.middle.name" path="brotherhood.middleName"
	readonly="true" />
<acme:input code="brotherhood.surname" path="brotherhood.surname"
	readonly="true" />

<acme:input code="brotherhood.email" path="brotherhood.email"
	readonly="true" />
<acme:input code="brotherhood.phone.number"
	path="brotherhood.phoneNumber" readonly="true" />
<acme:input code="brotherhood.address" path="brotherhood.address"
	readonly="true" />
<acme:input code="brotherhood.area.name" path="brotherhood.area.name"
	readonly="true" />

<jstl:forEach items="${picturesList}" var="pictureLink">
	<acme:image src="${pictureLink}" />
</jstl:forEach>

<div class="links">
	<acme:link code="brotherhood.processions" link="procession/list.do?brotherhoodId=${brotherhood.id}" />
	<br>
	
	<acme:link code="brotherhood.floats"
		link="float/list.do?brotherhoodId=${brotherhood.id}" />
	<br>
	<acme:link code="brotherhood.area"
		link="area/display.do?areaId=${brotherhood.area.id}" />
	<br>
</div>
<acme:cancel url="brotherhood/list.do" code="brotherhood.go.back" />