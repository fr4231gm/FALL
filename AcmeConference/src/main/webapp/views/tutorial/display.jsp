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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<h3><strong><spring:message code="activity.title"/></strong></h3>
<jstl:out value="${tutorial.title}" />
<br />

<h3><strong><spring:message code="activity.speakers"/></strong></h3>
<jstl:out value="${tutorial.speakers}" />
<br />

<h3><strong><spring:message code="activity.schedule"/></strong></h3>
<jstl:out value="${schedule}" />
<br />

<h3><strong><spring:message code="activity.room"/></strong></h3>
<jstl:out value="${tutorial.room}" />
<br />

<h3><strong><spring:message code="activity.summary"/></strong></h3>
<jstl:out value="${tutorial.summary}" />
<br />

<h3><strong><spring:message code="activity.attachments"/></strong></h3>
<jstl:out value="${tutorial.attachments}" />
<br />