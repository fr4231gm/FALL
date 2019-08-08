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

<h2>Tutorials</h2>
<display:table name="tutorials" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="titleActivity" titleKey="activity.title"
		sortable="true" />

	<display:column property="speakers" titleKey="activity.speakers"
		sortable="true" />

	<display:column property="room" titleKey="activity.room"
		sortable="true" />

	<display:column property="summaryActivity" titleKey="activity.summary"
		sortable="true" />

	<display:column property="attachments" titleKey="activity.attachments"
		sortable="true" />

</display:table>

<h2>Panels</h2>
<display:table name="panels" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="titleActivity" titleKey="activity.title"
		sortable="true" />

	<display:column property="speakers" titleKey="activity.speakers"
		sortable="true" />

	<display:column property="room" titleKey="activity.room"
		sortable="true" />

	<display:column property="summaryActivity" titleKey="activity.summary"
		sortable="true" />

	<display:column property="attachments" titleKey="activity.attachments"
		sortable="true" />

</display:table>

<h2>Presentations</h2>
<display:table name="presentations" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="titleActivity" titleKey="activity.title"
		sortable="true" />

	<display:column property="speakers" titleKey="activity.speakers"
		sortable="true" />

	<display:column property="room" titleKey="activity.room"
		sortable="true" />

	<display:column property="summaryActivity" titleKey="activity.summary"
		sortable="true" />

	<display:column property="attachments" titleKey="activity.attachments"
		sortable="true" />

	<display:column property="paper.title" titleKey="activity.paper"
		sortable="true" />

</display:table>

<br>

<acme:cancel code="activity.goback" url="/" />