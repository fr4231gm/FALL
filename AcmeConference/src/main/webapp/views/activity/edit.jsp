<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@taglib prefix="display" uri="http://displaytag.sf.net"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<form:form action="${actionURI}" modelAttribute="activity">	<form:hidden path="id" />	<form:hidden path="version" />	<form:hidden path="comments" /> 	<form:hidden path="Conference" /> 	<acme:input 		code="activity.title"		path="title"		placeholder="uwu" 	/>
	<acme:input 		code="activity.speaker"		path="speaker"		placeholder="uwu" 	/>
	<acme:date 		code="activity.startMoment"		path="startMoment" 	/>
	<acme:input 		code="activity.duration"		path="duration"		placeholder="5" 	/>
	<acme:input 		code="activity.room"		path="room"		placeholder="uwu" 	/>
	<acme:input 		code="activity.summary"		path="summary"		placeholder="uwu" 	/>
	<acme:input 		code="activity.attachments"		path="attachments"		placeholder="uwu" 	/>
	<acme:back 		code="master.go.back" 	/>
	<acme:submit 		name="save" 		code="master.save" 	 />
</form:form>