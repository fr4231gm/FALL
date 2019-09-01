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

<fmt:formatDate value="${finder.startDate}" pattern="dd/MM/yyyy"
	var="parsedStartDate" />
<fmt:formatDate value="${finder.endDate}" pattern="dd/MM/yyyy"
	var="parsedEndDate" />
<form:form modelAttribute="finder">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:input code="finder.keyWord" path="keyWord"/>
	<acme:input code="finder.area" path="area"/>
	<acme:date code="finder.startDate" path="startDate"/>
	<acme:date code="finder.endDate" path="endDate"/>

	<acme:submit name="save" code="finder.save"/>
	<acme:cancel url="/" code="finder.cancel"/>	
	
	


</form:form>
