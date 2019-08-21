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

<h2>
	<spring:message code="activity.tutorials" />
</h2>
<display:table name="tutorials" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="title" titleKey="activity.title"
		sortable="true" />

	<display:column property="speakers" titleKey="activity.speakers"
		sortable="true" />

	<display:column property="room" titleKey="activity.room"
		sortable="true" />

	<display:column property="summary" titleKey="activity.summary"
		sortable="true" />
		
	<display:column>

		<acme:link link="comment/list.do?targetId=${row.id}"
			code="activity.comments" />

	</display:column>
	
	<display:column>

		<acme:link link="tutorial/display.do?tutorialId=${row.id}"
			code="activity.display" />

	</display:column>

	<security:authorize access="hasRole('ADMINISTRATOR')">

		<jstl:if test="${conferencePast eq false}">
			<display:column>

				<acme:link link="tutorial/edit.do?tutorialId=${row.id}"
					code="activity.edit" />
			&nbsp;
			<acme:link link="tutorial/delete.do?tutorialId=${row.id}"
					code="activity.delete" />

			</display:column>
		</jstl:if>
	</security:authorize>


</display:table>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<jstl:if test="${conferencePast eq false}">
		<acme:link link="tutorial/create.do?conferenceId=${row.conference.id}"
			code="tutorial.create" />
	</jstl:if>
</security:authorize>

<h2>
	<spring:message code="activity.panels" />
</h2>
<display:table name="panels" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="title" titleKey="activity.title"
		sortable="true" />

	<display:column property="speakers" titleKey="activity.speakers"
		sortable="true" />

	<display:column property="room" titleKey="activity.room"
		sortable="true" />

	<display:column property="summary" titleKey="activity.summary"
		sortable="true" />

	<display:column>

		<acme:link link="comment/list.do?targetId=${row.id}"
			code="activity.comments" />

	</display:column>

	<display:column>

		<acme:link link="panel/display.do?panelId=${row.id}"
			code="activity.display" />

	</display:column>

	<security:authorize access="hasRole('ADMINISTRATOR')">

		<jstl:if test="${conferencePast eq false}">

			<display:column>

				<acme:link link="panel/edit.do?panelId=${row.id}"
					code="activity.edit" />
			&nbsp;
			<acme:link link="panel/delete.do?panelId=${row.id}"
					code="activity.delete" />

			</display:column>
		</jstl:if>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<jstl:if test="${conferencePast eq false}">
		<acme:link link="panel/create.do?conferenceId=${row.conference.id}"
			code="panel.create" />
	</jstl:if>
</security:authorize>

<h2>
	<spring:message code="activity.presentations" />
</h2>
<display:table name="presentations" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="title" titleKey="activity.title"
		sortable="true" />

	<display:column property="speakers" titleKey="activity.speakers"
		sortable="true" />

	<display:column property="room" titleKey="activity.room"
		sortable="true" />

	<display:column property="summary" titleKey="activity.summary"
		sortable="true" />

	<display:column property="paper.title" titleKey="activity.paper"
		sortable="true" />
		
	<display:column>

		<acme:link link="comment/list.do?targetId=${row.id}"
			code="activity.comments" />

	</display:column>

	<display:column>

		<acme:link link="presentation/display.do?presentationId=${row.id}"
			code="activity.display" />

	</display:column>

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<jstl:if test="${conferencePast eq false}">
			<display:column>

				<acme:link link="presentation/edit.do?presentationId=${row.id}"
					code="activity.edit" />
			&nbsp;
			<acme:link link="presentation/delete.do?presentationId=${row.id}"
					code="activity.delete" />

			</display:column>
		</jstl:if>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<jstl:if test="${conferencePast eq false}">
		<acme:link
			link="presentation/create.do?conferenceId=${row.conference.id}"
			code="presentation.create" />
	</jstl:if>
</security:authorize>

<br>

<acme:cancel code="activity.goback" url="/" />