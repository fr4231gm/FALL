<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jstl:set var="phoneConfirmationPrefix">
	<spring:message code="member.phone.confirmation.prefix" />
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
	<spring:message code="member.phone.confirmation.suffix" />
</jstl:set>

<form:form action="member/edit.do" modelAttribute="member"
	onsubmit="return phoneValidation('${phoneConfirmationPrefix}', '${phoneConfirmationSuffix}')">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:input code="member.name" path="name" />
	<br />

	<acme:input code="member.middleName" path="middleName" />
	<br />

	<acme:input code="member.surname" path="surname" />
	<br />

	<acme:input code="member.photo" path="photo" />
	<br />

	<acme:input code="member.email" path="email" />
	<br />

	<form:label path="phoneNumber">
		<spring:message code="member.phoneNumber" />:&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" />
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	<br />

	<acme:input code="member.address" path="address" />
	<br />

	<button type=submit name="save">
		<spring:message code="member.save" />
	</button>

	<button type=submit name="delete">
		<spring:message code="member.delete.account" />
	</button>
	
	<button type=submit name="export">
		<spring:message code="member.export" />
	</button>

	<acme:back code="member.cancel" />

</form:form>

