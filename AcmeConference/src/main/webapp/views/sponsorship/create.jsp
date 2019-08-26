<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<link rel="stylesheet" href="styles/onclick.css" type="text/css">
<jstl:set var="estasseguro">
<spring:message code="sponsorship.aresure"/> 
</jstl:set>
<form:form 

action = "sponsorship/provider/edit.do"
modelAttribute = "sponsorshipForm"
onsubmit='return askSubmission("${estasseguro}")'>

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="isEnabled"/>
	<form:hidden path="cost"/>
	
	<acme:input code="sponsorship.banner" path="banner" placeholder="https://www.google.es"/>
	<acme:input code="sponsorship.targetPage" path="targetPage" placeholder="https://www.google.es"/>
	
	<form:label path="${path}">
		<spring:message code="sponsorship.post" />
	</form:label>
	<form:select path="postId" >	
		<form:options items="${posts}" itemValue="id" itemLabel="title" />
	</form:select>
	<form:errors path="postId" cssClass="error" />
	

	<acme:submit name="save" code="sponsorship.save"/>
	
	<acme:cancel code="sponsorship.cancel" url="/sponsorship/provider/list.do"/>
</form:form>

