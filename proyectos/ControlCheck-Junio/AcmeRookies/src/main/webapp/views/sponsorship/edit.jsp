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


<form:form modelAttribute = "sponsorshipForm">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="isEnabled"/>
	<form:hidden path = "positionId"/>
	
	<acme:input code="sponsorship.banner" path="banner" placeholder="https://www.google.es"/>
	<acme:input code="sponsorship.targetPage" path="targetPage" placeholder="https://www.google.es"/>
	
	
	<acme:submit name="save" code="sponsorship.save"/>
	<acme:cancel code="sponsorship.cancel" url="/sponsorship/list.do"/>
</form:form>