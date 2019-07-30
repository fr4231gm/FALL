<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@taglib prefix="display" uri="http://displaytag.sf.net"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<acme:input 	code="activity.title"	path="activity.title"	readonly="true" />
<acme:input 	code="activity.speaker"	path="activity.speaker"	readonly="true" />
<jstl:choose>	<jstl:when test="${langCode eq 'en'}">		<fmt:formatDate			value="${activity.startMoment}" 			pattern="{0, date, MM-dd-yy}" 			var="parsedStartmoment" 		/>	</jstl:when>	<jstl:otherwise>		<fmt:formatDate			value="${activity.startMoment}" 			pattern="{0, date, dd-MM-yy}" 			var="parsedStartmoment" 		/>	</jstl:otherwise></jslt:choose>
<form:label path="activity.startMoment"> 	<spring:message code="activity.startMoment"/></form:label><form:input code="activity.startMoment" 	path="activity.startMoment" 	value="${parsedStartmoment}" 	readonly="true" /> <br>
<acme:input 	code="activity.duration"	path="activity.duration"	readonly="true" />
<acme:input 	code="activity.room"	path="activity.room"	readonly="true" />
<acme:input 	code="activity.summary"	path="activity.summary"	readonly="true" />
<acme:input 	code="activity.attachments"	path="activity.attachments"	readonly="true" />
<acme:input 	code="activity.comments"	path="activity.comments"	readonly="true" />
<acme:link 	code="activity.Conference" 	link="Conference/display.do?ConferenceId=${activityConference.id}" />
<acme:back 	code="master.go.back" />