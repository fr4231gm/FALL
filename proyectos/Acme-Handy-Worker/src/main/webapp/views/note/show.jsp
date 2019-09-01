<%-- elreyrata did that --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<fmt:formatDate value="${dateSystem}" pattern="yyyy-MM-dd HH:mm"
	var="systemDateFormated" />

<spring:message var="noteMoment" code="note.moment"></spring:message>
<b>${noteMoment}:&nbsp;</b> <jstl:out value="${note.moment}"/>
<br>

<spring:message var="noteRefereeComment" code="note.refereeComment"></spring:message>
<b>${noteRefereeComment}:&nbsp;</b> <jstl:out value="${note.refereeComment}"/>
<br>

<spring:message var="noteCustomerComment" code="note.customerComment"></spring:message>
<b>${noteCustomerComment}:&nbsp;</b> <jstl:out value="${note.customerComment}"/>
<br>

<spring:message var="noteHandyworkerComment" code="note.handyworkerComment"></spring:message>
<b>${noteHandyworkerComment}:&nbsp;</b> <jstl:out value="${note.handyworkerComment}"/>
<br>

<input type="button" name="back"
	value="<spring:message code='complaint.backbutton'></spring:message>"
	onclick="javascript: relativeRedir('/note/list.do');" />
<br>
