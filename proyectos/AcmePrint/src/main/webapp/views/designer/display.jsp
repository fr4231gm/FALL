<%--
 * display.jsp
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


<acme:image src="${designer.photo}"/>


<acme:input code="designer.name" path="designer.name" readonly="true" />
<br />

<acme:input code="designer.middleName" path="designer.middleName"
	readonly="true" />
<br />

<acme:input code="designer.surname" path="designer.surname"
	readonly="true" />
<br />

<acme:input code="designer.vatNumber" path="designer.vatNumber"
	readonly="true" />
<br />

<acme:input code="designer.score" path="designer.score"
	readonly="true" />
<br />

<acme:input code="designer.photo" path="designer.photo" readonly="true" />
<br />

<acme:input code="designer.phone.number" path="designer.phoneNumber"
	readonly="true" />
<br />

<acme:input code="designer.email" path="designer.email" readonly="true" />
<br />

<acme:input code="designer.address" path="designer.address"
	readonly="true" />
<br />

<acme:link link="post/listposts.do?designerId=${designer.id}"
	code="designer.list.posts" />
<br />

<acme:link code="designer.socialprofiles" link="social-profile/list.do?actorId=${designer.id}"/>
<br />

<acme:back code="designer.go.back" />