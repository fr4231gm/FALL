<%-- edit.jsp phase --%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<form:form action="${actionURI}" modelAttribute="phase">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="application"/>
	
	<fmt:formatDate value="${phase.start}" pattern="dd/MM/yyyy"
	var="parsedStartDate" />
<fmt:formatDate value="${phase.end}" pattern="dd/MM/yyyy"
	var="parsedEndDate" />
	
	<b><form:label path="title">
		<spring:message code="phase.title">:&nbsp;</spring:message></form:label></b>
	<form:input path="title"
		placeholder="<spring:message code='phase.title.placeholder'/>" />
	<form:errors path="title" cssClass="error" />
	<br />

	<b><form:label path="description"> <spring:message
			code="phase.description">:&nbsp;</spring:message></form:label></b>
	<form:textarea path="description"
		placeholder="<spring:message code='phase.description.placeholder'/>" />
	<form:errors path="description" cssClass="error" />
	<br />

	
	<form:label path="start">
		<spring:message code='phase.start' /> :</form:label>
	<form:input path="start" value="${parsedStartDate}" />
	<form:errors cssClass="error" path="start"></form:errors>
	<br />
	<form:label path="end">
		<spring:message code='phase.end' />
	</form:label>
	<form:input path="end" value="${parsedEndDate}" />
	<form:errors cssClass="error" path="end"></form:errors>
	<br />

	<input type="submit" name="save"
		value="<spring:message code='phase.save'></spring:message>"
		onclick="javascript: relativeRedir('phase/handyworker/save.do?phaseId=${phase.id}');" />
	<input type="button" name="cancel"
		value="<spring:message code='phase.cancel'></spring:message>"
		onclick="javascript: relativeRedir('${cancelURI}');" />

</form:form>
