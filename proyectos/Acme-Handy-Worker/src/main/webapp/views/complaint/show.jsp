<%-- elreyrata did that --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<spring:message var="complaintTicker" code="complaint.ticker"></spring:message>
<b>${complaintTicker}:&nbsp;</b> <jstl:out value="${complaint.ticker}"/>
<br>

<fmt:formatDate value="${dateSystem}" pattern="yyyy-MM-dd HH:mm"
	var="systemDateFormated" />

<spring:message var="complaintMoment" code="complaint.moment"></spring:message>
<b>${complaintMoment}:&nbsp;</b> <jstl:out value="${complaint.moment}"/>
<br>

<spring:message var="complaintDescription" code="complaint.description"></spring:message>
<b>${complaintDescription}:&nbsp;</b> <jstl:out value="${complaint.description}"/>
<br>

<spring:message var="complaintAttachments" code="complaint.attachments"></spring:message>
<b>${complaintAttachments}:&nbsp;</b> <jstl:out value="${complaint.attachments}"/>
<br>

<spring:message var="complaintReport" code="complaint.report"></spring:message>
<b>${complaintReport}:&nbsp;</b> <jstl:out value="${complaint.report}"/>
<br>

<spring:message var="complaintFixUpTask" code="complaint.fixUpTask"></spring:message>
<b>${complaintFixUpTask}:&nbsp;</b> <jstl:out value="${complaint.fixUpTask}"/>
<br>

<spring:message var="complaintCustomer" code="complaint.customer"></spring:message>
<b>${complaintCustomer}:&nbsp;</b> <jstl:out value="${complaint.customer}"/>
<br>

<spring:message var="complaintReferee" code="complaint.referee"></spring:message>
<b>${complaintReferee}:&nbsp;</b> <jstl:out value="${complaint.referee}"/>
<br>

<spring:message var="complaintIsDraft" code="complaint.isDraft"></spring:message>
<b>${complaintIsDraft}:&nbsp;</b> <jstl:out value="${complaint.isDraft}"/>
<br>

<input type="button" name="back"
	value="<spring:message code='complaint.backbutton'></spring:message>"
	onclick="javascript: relativeRedir('/complaint/customer/list.do');" />

<security:authorize access="hasRole('CUSTOMER')">

	<jstl:if test="${complaint.id != 0} and ${complaint.isDraft == true}">
<!--
			<input type="button" name="edit"
			value="<spring:message code='complaint.editbutton'></spring:message>"
			onclick="javascript: relativeRedir('/complaint/customer/edit.do');" />
-->
		<a href="/complaint/customer/edit.do"> <spring:message code='complaint.editbutton' /> </a> 

	</jstl:if>

</security:authorize>

<br>
