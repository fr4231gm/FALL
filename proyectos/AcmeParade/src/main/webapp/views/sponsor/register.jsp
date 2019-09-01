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
<spring:message code="sponsor.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="sponsor.phone.confirmation.suffix"/> 
</jstl:set>

<form:form 
	action="sponsor/register.do" 
	modelAttribute="sponsorForm" 
	onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:input code="sponsor.name" path="name" placeholder="Quijote"/>
	<br />

	<acme:input code="sponsor.middleName" path="middleName" placeholder="de la Mancha"/>
	<br />

	<acme:input code="sponsor.surname" path="surname" placeholder="surname"/>
	<br />

	<acme:input code="sponsor.photo" path="photo" placeholder="https://www.google.es"/> />
	<br />

	<acme:input code="sponsor.email" path="email" placeholder="mail@mail.com"/>/>
	<br />

	<form:label path="phoneNumber">
		<spring:message code="sponsor.phoneNumber" />:&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="662130564"/>
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
		<br />

	<acme:input code="sponsor.address" path="address" placeholder="casa negra" />
	<br />
  <fieldset>

  <legend><spring:message code="sponsor.useraccount" /></legend>
  
	<acme:input code="sponsor.username" path="username" />
	<br />

	<acme:password code="sponsor.password" path="password" />
	<br />

	<acme:password code="sponsor.passwordConfirmation"
		path="passwordConfirmation" />
	<br />

	<acme:checkbox code="sponsor.checkTerms" path="checkTerms" />
	<br />
  </fieldset>

	<button type=submit name="save">
		<spring:message code="sponsor.save" />
	</button>

	<acme:cancel code="sponsor.cancel" url="/" />

</form:form>

