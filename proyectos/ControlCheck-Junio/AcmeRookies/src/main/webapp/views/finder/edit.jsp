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

<fmt:formatDate value="${finder.deadline}" pattern="dd/MM/yyyy HH:mm"
	var="parsedDeadline" />
<form:form modelAttribute="finder">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:input code="finder.keyWord" path="keyword" placeholder="anonymous"/>
	<form:label path="deadline">
		<spring:message code="finder.deadline" />:&nbsp;
	</form:label>
	
	<form:input path="deadline" value="${parsedDeadline}" placeholder="11/11/2019 22:00"/>
	<form:errors cssClass="error" path="deadline" />
	<acme:input code="finder.maximumSalary" path="maximumSalary" placeholder="999.1"/>
	<acme:input code="finder.minimumSalary" path="minimumSalary" placeholder="999.0"/>
	<acme:submit name="save" code="finder.buscar"/>
		<acme:submit name="clear" code="finder.clear"/>
	<acme:cancel url="/" code="finder.cancel"/>	
	
	


</form:form>
