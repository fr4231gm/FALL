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

<fmt:formatDate value="${processionForm.moment}" pattern="dd/MM/yyyy"
	var="parsedMoment" />

<form:form modelAttribute = "processionForm">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="rows" />
	<jstl:out value="Ticker: ${processionForm.ticker}"/>
	
	<acme:input code="procession.title" path="title"/>
	<acme:input code="procession.description" path="description"/>
	<form:label path="moment">
		<spring:message code="procession.moment" />:&nbsp;
	</form:label>
	<form:input path="moment" value="${parsedMoment}"/>
	<acme:checkbox code="procession.isDraft" path="isDraft"/>
	
	<acme:select items="${floats}" itemLabel="title" code="procession.floats" path="floats" id="id"/>

	<acme:submit name="save" code="procession.save"/>
	<acme:cancel url="procession/brotherhood/list.do" code="procession.cancel"/>

</form:form>