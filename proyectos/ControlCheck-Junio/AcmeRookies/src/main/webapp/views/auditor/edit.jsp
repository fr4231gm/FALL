<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<jstl:set var="phoneConfirmationPrefix">
<spring:message code="auditor.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="auditor.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="auditor/edit.do" 
	modelAttribute="auditor" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:input code="auditor.name" path="name" />
	<br />

	<acme:input code="auditor.surname" path="surname"/>
	<br />
	
	<acme:input code="auditor.vatNumber" path="vatNumber"/>
	<br />

	<acme:input code="auditor.photo" path="photo" />
	<br />

	<acme:input code="auditor.email" path="email" />
	<br />
		
	<form:label path="phoneNumber">
		<spring:message code="auditor.phoneNumber" />&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" />
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	<br />
	
	<acme:input code="auditor.address" path="address" />
	<br />
	<acme:link code="auditor.creditCard.edit" link="creditCard/edit.do"/>
	<br/>
	<button type=submit name="save">
		<spring:message code="auditor.save" />
	</button>

	<acme:cancel code="auditor.cancel" url="/"/>
	
	
</form:form>	