<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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

<fmt:formatDate value="${paradeForm.moment}" pattern="dd/MM/yyyy"
	var="parsedMoment" />

<form:form modelAttribute = "paradeForm">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="rows" />
	<form:hidden path="status"/>
	<form:hidden path="rejectReason"/>
	<jstl:out value="Ticker: ${paradeForm.ticker}"/>
	
	<acme:input code="parade.title" path="title" placeholder="title" />
	<acme:input code="parade.description" path="description" placeholder="the parade is a...."/>
	<form:label path="moment">
		<spring:message code="parade.moment" />:&nbsp;
	</form:label>
	<form:input path="moment" value="${parsedMoment}" placeholder="11/11/2019"/>
	<acme:checkbox code="parade.isDraft" path="isDraft"/>
	
	<acme:select items="${floats}" itemLabel="title" code="parade.floats" path="floats" id="id"/>

	<acme:submit name="save" code="parade.save"/>
	<acme:cancel url="parade/brotherhood/list.do" code="parade.cancel"/>

</form:form>