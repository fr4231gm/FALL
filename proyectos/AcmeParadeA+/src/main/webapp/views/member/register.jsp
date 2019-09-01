<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<jstl:set var="phoneConfirmationPrefix">
<spring:message code="member.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="member.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="member/register.do" 
	modelAttribute="memberForm" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:input code="member.name" path="name" placeholder="Jose"/>
	<br />

	<acme:input code="member.middleName" path="middleName" placeholder="Luis"/>
	<br />

	<acme:input code="member.surname" path="surname" placeholder="Rodr�guez Zapatero"/>
	<br />

	<acme:input code="member.photo" path="photo" placeholder="https://www.google.es"/>
	<br />

	<acme:input code="member.email" path="email" placeholder="mail@mail.com"/>
	<br />

	<form:label path="phoneNumber">
		<spring:message code="member.phoneNumber" />:&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId"  placeholder="662130564"/>
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
		<br />

	<acme:input code="member.address" path="address" />
	<br />
  <fieldset>

  <legend><spring:message code="member.useraccount" /></legend>
  
	<acme:input code="member.username" path="username" />
	<br />

	<acme:password code="member.password" path="password" />
	<br />

	<acme:password code="member.passwordConfirmation"
		path="passwordConfirmation" />
	<br />

	<acme:checkbox code="member.checkTerms" path="checkTerms" />
	<br />
  </fieldset>

	<button type=submit name="save">
		<spring:message code="member.save" />
	</button>

	<acme:cancel code="member.cancel" url="/"/>

</form:form>

