<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="conference">

	<form:hidden path="id" />
	<form:hidden path="version"/>
	<form:hidden path="comments"/>
	
	<acme:input code="conference.title" path="title"/>
	<acme:input code="conference.acronym" path="acronym"/>
	<acme:input code="conference.venue" path="venue"/>
	<acme:date code="conference.submissionDeadline" path="submissionDeadline"/>
	<acme:date code="conference.notificationDeadline" path="notificationDeadline"/>
	<acme:date code="conference.cameraReadyDeadline" path="cameraReadyDeadline"/>
	<acme:date code="conference.startDate" path="startDate"/>
	<acme:date code="conference.endDate" path="endDate"/>
	<acme:textarea code="conference.summary" path="summary"/>
	<acme:input code="conference.fee" path="fee"/>
	<acme:checkbox code="conference.draft" path="isDraft" value="${ conference.isDraft}"/>
	<acme:select itemLabel="name[${lan}]" items="${categories}" code="conference.category" path="category"/>
	
	<acme:submit name="save" code="category.save" />

	<acme:back code="category.cancel" />

</form:form>