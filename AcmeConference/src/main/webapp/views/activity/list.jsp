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
		
	<security:authorize access="hasRole('ADMINISTRATOR')">

		<display:column>

			<acme:link link="tutorial/edit.do?tutorialId=${row.id}"
				code="activity.edit" />
			&nbsp;
			<acme:link link="tutorial/delete.do?tutorialId=${row.id}"
				code="activity.delete" />

		</display:column>
	</security:authorize>
</display:table>

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

	<security:authorize access="hasRole('ADMINISTRATOR')">

		<display:column>

			<acme:link link="panel/edit.do?panelId=${row.id}"
				code="activity.edit" />
			&nbsp;
			<acme:link link="panel/delete.do?panelId=${row.id}"
				code="activity.delete" />

		</display:column>
	</security:authorize>

</display:table>

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

	<security:authorize access="hasRole('ADMINISTRATOR')">

		<display:column>

			<acme:link link="presentation/edit.do?presentationId=${row.id}"
				code="activity.edit" />
			&nbsp;
			<acme:link link="presentation/delete.do?presentationId=${row.id}"
				code="activity.delete" />

		</display:column>
	</security:authorize>

</display:table>

<br>

<acme:cancel code="activity.goback" url="/" />