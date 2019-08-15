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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<fmt:formatDate value="${fechaActual}" pattern="dd/MM/yyyy HH:mm"
	var="formatedFechaActual" />

<jstl:if test="${general eq 'true'}">
	<form name="searchForm" action="${searchPoint}" method="get">
		<input type="text" name="keyword"> <input type="submit"
			value="<spring:message code="conference.search"/>">
	</form>
</jstl:if>

<display:table name="conferences" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="title" titleKey="conference.title"
		sortable="true" />

	<display:column property="acronym" titleKey="conference.acronym"
		sortable="true" />

	<display:column property="startDate" titleKey="conference.startDate"
		sortable="true" format="{0, date, dd/MM/yyyy HH:mm}" />

	<display:column property="endDate" titleKey="conference.endDate"
		sortable="true" format="{0, date, dd/MM/yyyy HH:mm}" />

	<security:authorize access="isAuthenticated()">

		<display:column property="category.name"
			titleKey="conference.category" sortable="true" />

	</security:authorize>

	<display:column>

		<acme:link link="conference/display.do?conferenceId=${row.id}"
			code="conference.display" />

	</display:column>

	<display:column>

		<acme:link link="comment/listByConference.do?conferenceId=${row.id}"
			code="conference.comments" />

	</display:column>

	<security:authorize access="hasRole('AUTHOR')">
		<display:column>

			<jstl:if test="${fechaActual.time < row.submissionDeadline.time }">
				<acme:link link="submission/author/create.do?conferenceId=${row.id}"
					code="submission.create" />
			</jstl:if>

		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
	
	<display:column>
	<jstl:if test="${row.isDraft eq 'true'}">
	<acme:link code="conference.edit" link="conference/administrator/edit.do?conferenceId=${row.id}"/>
	</jstl:if>
	</display:column>
	</security:authorize>

	<display:column>

		<acme:link link="activity/list.do?conferenceId=${row.id}"
			code="conference.activities" />

	</display:column>


</display:table>

<br>

<security:authorize access="hasRole('ADMINISTRATOR')">
<acme:link code="conference.create" link="conference/administrator/create.do"/>
</security:authorize>

<acme:back code="conference.goback" />