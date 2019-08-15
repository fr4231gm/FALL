<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<fmt:formatDate value="${finder.startDate}" pattern="dd/MM/yyyy HH:mm"
	var="parsedStartDate" />
	
<fmt:formatDate value="${finder.endDate}" pattern="dd/MM/yyyy HH:mm"
	var="parsedStartDate" />
<form:form modelAttribute="finder">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="author"/>
	
	<acme:input code="finder.keyWord" path="keyWord" placeholder="la n palabra"/>
	<form:label path="startDate">
		<spring:message code="finder.startDate" />:&nbsp;
	</form:label>
	
	<form:input path="startDate" value="${parsedStartDate}" placeholder="11/11/2019 22:00"/>
	<form:errors cssClass="error" path="startDate" />
	<br/>
	<form:label path="endDate">
		<spring:message code="finder.endDate" />:&nbsp;
	</form:label>
	
	<form:input path="endDate" value="${parsedEndDate}" placeholder="11/11/2019 22:00"/>
	<form:errors cssClass="error" path="endDate" />
	<br/>
	
	<acme:input code="finder.fee" path="fee"/>
	
	<acme:submit name="save" code="finder.buscar"/>
		<acme:submit name="clear" code="finder.clear"/>
			<acme:cancel url="/" code="finder.cancel"/>	
	
	


</form:form>
