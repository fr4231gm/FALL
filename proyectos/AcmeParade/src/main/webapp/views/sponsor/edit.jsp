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
	<spring:message code="sponsor.phone.confirmation.prefix" />
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
	<spring:message code="sponsor.phone.confirmation.suffix" />
</jstl:set>

<form:form action="sponsor/edit.do" modelAttribute="sponsor"
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:input code="sponsor.name" path="name" placeholder="nombrecito" />
	<br />

	<acme:input code="sponsor.middleName" path="middleName"  placeholder="chingon"  />
	<br />

	<acme:input code="sponsor.surname" path="surname"  placeholder="appellidación apellido2"  />
	<br />

	<acme:input code="sponsor.photo" path="photo" placeholder="https://www.google.es"/>
	<br />

	<acme:input code="sponsor.email" path="email" placeholder="columbus@mail.com"/>
	<br />

	<form:label path="phoneNumber">
		<spring:message code="sponsor.phoneNumber" />:&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="+34 662130564" />
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	<br />

	<acme:input code="sponsor.address" path="address" placeholder="Calle milanos 33" />
	<br />

	<button type=submit name="save">
		<spring:message code="sponsor.save" />
	</button>
	
	<acme:cancel code="sponsor.cancel" url="/" />

</form:form>

