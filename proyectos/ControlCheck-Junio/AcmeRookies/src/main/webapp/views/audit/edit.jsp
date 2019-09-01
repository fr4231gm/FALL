<%@page language="java" contentType="title/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="audit/auditor/edit.do" modelAttribute="audit"
	id="row">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="auditor" />

	<form:label path="score">
		<spring:message code="audit.score" />:
		</form:label>
	<form:input path="score" placeholder="4.99" />
	<form:errors cssClass="error" path="score" />
	<br />


	<acme:select items="${positions}" itemLabel="title"
		code="audit.position" path="position" />
	<br />

	<form:label path="text">
		<spring:message code="audit.text" />:
		</form:label>
	<form:textarea path="text" placeholder="some piece of text" />
	<form:errors cssClass="error" path="text" />
	<br />

	<form:label path="isDraft">
		<spring:message code="audit.isDraft" />:
		</form:label>
	<form:checkbox path="isDraft" />
	<form:errors cssClass="error" path="isDraft" />
	<br />

	<br />


	<input type="submit" name="save" id="save"
		value="<spring:message code="audit.save" />" />&nbsp; 
		
		<jstl:if test="${audit.id != 0}">
	<input type="submit" name="delete"
			value="<spring:message code="audit.delete" />" />&nbsp;
		</jstl:if>
		
	<acme:back code="position.go.back" />

</form:form>






