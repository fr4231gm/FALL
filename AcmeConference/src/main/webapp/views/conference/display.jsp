<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<acme:input code="conference.title" path="conference.title" readonly="true" />
<acme:input code="conference.acronym" path="conference.acronym" readonly="true" />
<acme:input code="conference.venue" path="conference.venue" readonly="true" />
<acme:input code="conference.submissionDeadline" path="conference.submissionDeadline" readonly="true" />
<acme:input code="conference.notificationDeadline" path="conference.notificationDeadline" readonly="true" />
<acme:input code="conference.cameraReadyDeadline" path="conference.cameraReadyDeadline" readonly="true" />
<acme:input code="conference.startDate" path="conference.startDate" readonly="true" />
<acme:input code="conference.endDate" path="conference.endDate" readonly="true" />
<acme:textarea code="conference.summary" path="conference.summary" readonly="true" />
<acme:input code="conference.fee" path="conference.fee" readonly="true" />

<jstl:if test="${future eq 'true' and haveR eq 'false'}">
	<security:authorize access="hasRole('AUTHOR')">
		<acme:link link="registration/author/register.do?conferenceId=${conference.id}" code="conference.registration" />
	</security:authorize>
</jstl:if>

<jstl:if test="${canCreateActivity eq true}">
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<acme:link link="panel/edit.do?conferenceId=${conference.id}" code="conference.panel" />	&nbsp;
		<acme:link link="presentation/edit.do?conferenceId=${conference.id}" code="conference.presentation" />	&nbsp;
		<acme:link link="tutorial/edit.do?conferenceId=${conference.id}" code="conference.tutorial" /><br><br>
	</security:authorize>
</jstl:if>

<acme:link link="message/broadcast-authors-submitted.do?conferenceId=${conference.id}" code="master.page.broadcast.submitted" />&nbsp;
<acme:link link="message/broadcast-authors-registered.do?conferenceId=${conference.id}" code="master.page.broadcast.registered" /><br><br>
<acme:link code="conference.create.comment" link="comment/create.do?targetId=${conference.id}" /> <br><br>

<display:table  requestURI="conference/display.do" name="conference.comments" id="row" pagesize="5" class="displaytag">
	<display:column property="moment" titleKey="comment.moment" sortable="true" format="{0, date, dd/MM/yyyy HH:mm}" />
	<display:column property="author" titleKey="comment.author" sortable="true" />
	<display:column property="title" titleKey="comment.title" sortable="true" />
	<display:column property="text" titleKey="comment.text" sortable="true" />
</display:table>

<acme:back code="conference.goback" />

