<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<fmt:formatDate value="${dateSystem}" pattern="yyyy-MM-dd HH:mm"
	var="systemDateFormated" />

<spring:message var="reportMoment" code="report.moment"></spring:message>
<b>${complaintMoment}:&nbsp;</b> <jstl:out value="${report.moment}"/>
<br>

<spring:message var="reportDescription" code="report.description"></spring:message>
<b>${complaintDescription}:&nbsp;</b> <jstl:out value="${report.description}"/>
<br>

<spring:message var="reportAttachments" code="report.attachments"></spring:message>
<b>${complaintAttachments}:&nbsp;</b> <jstl:out value="${report.attachments}"/>
<br>

<spring:message var="reportComplaint" code="report.complaint"></spring:message>
<b>${complaintAttachments}:&nbsp;</b> <jstl:out value="${report.complaint}"/>
<br>

<spring:message var="reportNotes" code="report.notes"></spring:message>
<b>${complaintAttachments}:&nbsp;</b> <jstl:out value="${report.notes}"/>
<br>

<spring:message var="reportIsDraft" code="report.isDraft"></spring:message>
<b>${complaintAttachments}:&nbsp;</b> <jstl:out value="${report.isDraft}"/>
<br>

<jstl:if test="${report.id != 0} and ${report.isDraft == true}">
		
	<a href="report/referee/edit.do"> <spring:message code="report.edit" /> </a>

</jstl:if>

<input type="button" name="back"
	value="<spring:message code='report.backbutton'></spring:message>"
	onclick="javascript: relativeRedir('/report/list.do');" />
<br>
