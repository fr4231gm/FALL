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
	var="parsedEndDate" />
<form:form modelAttribute="finder">
	<form:hidden path="id" />
	<form:hidden path="version" />

	
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
	
	
	<form:label path="category">
			<spring:message code="finder.category" />
		</form:label>
		<form:select id="categories" path="category">
			<form:option itemLabel="root" value="" />
			<form:options items="${categories}" itemLabel="name" itemValue="id" />
		</form:select>
	<acme:submit name="save" code="finder.buscar"/>

	<acme:cancel url="/" code="finder.cancel"/>	
	
	


</form:form>