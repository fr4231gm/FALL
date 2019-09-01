<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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

<b><spring:message code="tutorial.title">:&nbsp;</spring:message></b>
<jstl:out value="${tutorial.title}" />
<br />

<b><spring:message code="tutorial.summary">:&nbsp;</spring:message></b>
<jstl:out value="${tutorial.summary}" />
<br />

<b><spring:message code="tutorial.picture">:&nbsp;</spring:message></b>
<jstl:out value="${tutorial.pictureUrl}" />
<br />

<b><spring:message code="tutorial.lastupdate">:&nbsp;</spring:message></b>
<jstl:out value="${tutorial.lastestUpdate" />
<br />

<b><spring:message code="tutorial.sponsorship">:&nbsp;</spring:message></b>
<a href="${sponsorship.targetURL}"><img src="${sponsorship.bannerURL}"
alt="Picture Not Found" width="200" height="100" /></a>

<input type="button" name="viewsections" value="<spring:message code="tutorial.sections" />"
        onclick="javascript: relativeRedir('${actionURI}');" />







