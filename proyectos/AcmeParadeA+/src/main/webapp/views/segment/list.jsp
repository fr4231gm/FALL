<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="segment" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">


	<display:column property="origin.longitude"
		titleKey="segment.origin.longitude" sortable="true" />

	<display:column property="origin.latitude"
		titleKey="segment.origin.latitude" sortable="true" />

	<display:column property="destination.longitude"
		titleKey="segment.destination.longitude" sortable="true" />

	<display:column property="destination.latitude"
		titleKey="segment.destination.latitude" sortable="true" />

	<display:column property="startTime" titleKey="segment.startTime"
		sortable="true" format="{0, date, HH:mm}" />

	<display:column property="endTime" titleKey="segment.endTime"
		sortable="true" format="{0, date, HH:mm}" />

	<display:column titleKey="segment.display">
		<acme:link link="segment/brotherhood/display.do?segmentId=${row.id}"
			code="segment.display" />
	</display:column>

	<security:authorize access="hasRole('BROTHERHOOD')">

		<display:column titleKey="segment.delete">
			<acme:link
				link="segment/brotherhood/delete.do?paradeId=${parade.id}&segmentId=${row.id}"
				code="segment.delete" />
		</display:column>

		<display:column titleKey="segment.edit">
			<acme:link
				link="segment/brotherhood/edit.do?paradeId=${parade.id}&segmentId=${row.id}"
				code="segment.edit" />
		</display:column>

	</security:authorize>

</display:table>
<security:authorize access="hasRole('BROTHERHOOD')"><acme:link code="segment.create"
	link="segment/brotherhood/create.do?paradeId=${parade.id}" /></security:authorize>


<acme:cancel code="segment.go.back" url="parade/brotherhood/list.do" />
