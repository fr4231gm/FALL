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
<spring:message code="provider.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="provider.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="provider/edit.do" 
	modelAttribute="provider" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:input code="provider.name" path="name" />
	<br />

	<acme:input code="provider.surname" path="surname" />
	<br />
	
	<acme:input code="provider.vatNumber" path="vatNumber" />
	<br />

	<acme:input code="provider.photo" path="photo" />
	<br />

	<acme:input code="provider.email" path="email" />
	<br />
	
	<acme:input code="provider.make" path="make" />
	<br />
	
	<form:label path="phoneNumber">
		<spring:message code="provider.phoneNumber" />&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="612345678"/>
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	<br />
	
	<acme:input code="provider.address" path="address" />
	<br />
	<acme:link code="provider.creditCard.edit" link="creditCard/edit.do"/>
	<br/>
	<br/>
	<button type=submit name="save">
		<spring:message code="provider.save" />
	</button>

	<acme:cancel code="provider.cancel" url="/"/>
	
	
</form:form>	