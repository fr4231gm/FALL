<%-- Show an application --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<fmt:formatDate value="${dateSystem}" pattern="yyyy-MM-dd HH:mm"
	var="systemDateFormated" />
<jstl:choose>
	<jstl:when
		test="${application.fixUpTask.startDate < systemDateFormated and row.status eq 'PENDING'}">
		<jstl:set var="style" value="color: grey;"></jstl:set>
	</jstl:when>
	<jstl:when
		test="${application.fixUpTask.startDate > systemDateFormated and row.status eq 'PENDING'}">
		<jstl:set var="style" value="color: black;"></jstl:set>
	</jstl:when>
	<jstl:when test="${application.status eq 'ACCEPTED'}">
		<jstl:set var="style" value="color: green;"></jstl:set>
	</jstl:when>
	<jstl:when test="${application.status eq 'REJECTED'}">
		<jstl:set var="style" value="color: orange;"></jstl:set>
	</jstl:when>
</jstl:choose>

<spring:message var="applicationMoment" code="application.moment"></spring:message>
<b>${applicationMoment}:&nbsp;</b>
<jstl:out value="${application.moment}" />
<br />

<spring:message var="applicationStatus" code="application.status" />
<jstl:choose>
	<jstl:when test="${application.status eq 'ACCEPTED'}">
		<spring:message var="appStatus" code="application.accepted"></spring:message>
	</jstl:when>
	<jstl:when test="${application.status eq 'REJECTED'}">
		<spring:message var="appStatus" code="application.rejected"></spring:message>
	</jstl:when>
	<jstl:when test="${application.status eq 'PENDING'}">
		<spring:message var="appStatus" code="application.pending"></spring:message>
	</jstl:when>
</jstl:choose>
<b>${applicationStatus}</b>
<b style="${style}">:&nbsp;${appStatus}</b>

<spring:message var="applicationComments" code="application.comments"></spring:message>
<b>${applicationComments}:&nbsp;</b>
<jstl:out value="${application.comments}" />
<br />

<a href="fixuptask/show.do?fixuptaskId=${application.fixUpTask.id}"><spring:message
		code="application.show.task"></spring:message></a>
<br />

<b><spring:message code="application.workplan">:&nbsp;</spring:message></b>
<jstl:choose>
	<jstl:when test="${application.status eq 'ACCEPTED'}">
		<a href="phase/handyworker/list.do?applicationId=${application.id}"><spring:message
				code="appliction.workplan.list"></spring:message></a>
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="application.notaccepted">
		</spring:message>
	</jstl:otherwise>
</jstl:choose>
<br />

<b><spring:message code="application.creditcard">:&nbsp;</spring:message></b>
<jstl:choose>
	<jstl:when test="${application.status eq 'ACCEPTED'}">
		<a href="creditcard/endorser/show.do?creditcardId=${application.creditCard.id}">
			<spring:message code="application.creditcard.link"></spring:message>
		</a>
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="application.notaccepted"></spring:message>
	</jstl:otherwise>
</jstl:choose>
<br />

<input type="button" name="back"
	value="<spring:message code='application.backbutton'></spring:message>"
	onclick="javascript: relativeRedir('application/handyworker/list.do');" />







