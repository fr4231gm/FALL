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

<fmt:formatDate value="${finder.minDate}" pattern="dd/MM/yyyy HH:mm"
	var="parsedMinDate" />
	
<fmt:formatDate value="${finder.maxDate}" pattern="dd/MM/yyyy HH:mm"
	var="parsedMaxDate" />
<form:form modelAttribute="finder">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:input code="finder.keyWord" path="keyword" placeholder="pla"/>
	<form:label path="minDate">
		<spring:message code="finder.minDate" />:&nbsp;
	</form:label>
	
	<form:input path="minDate" value="${parsedMinDate}" placeholder="11/11/2019 22:00"/>
	<form:errors cssClass="error" path="minDate" />
	<br/>
	<form:label path="maxDate">
		<spring:message code="finder.maxDate" />:&nbsp;
	</form:label>
	
	<form:input path="maxDate" value="${parsedMaxDate}" placeholder="11/11/2019 22:00"/>
	<form:errors cssClass="error" path="maxDate" />
	<br/>
	
	<acme:submit name="save" code="finder.buscar"/>
		<acme:submit name="clear" code="finder.clear"/>
	<acme:cancel url="/" code="finder.cancel"/>	
	
	


</form:form>
