<%@page language="java" contentType="text/html; charset=ISO-8859-1"
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
<spring:message code="member.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="member.phone.confirmation.suffix"/> 
</jstl:set>

<fmt:formatDate value="${brotherhoodForm.establishmentDate}" pattern="dd/MM/yyyy"
	var="parsedEstablishmentDate" />
	
<form:form 
	action="brotherhood/register.do" 
	modelAttribute="brotherhoodForm" 
	onsubmit="return phoneValidation('${phoneConfirmationPrefix}', '${phoneConfirmationSuffix}')">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:input code="brotherhood.name" path="name" />
	<br />

	<acme:input code="brotherhood.middle.name" path="middleName" />
	<br />

	<acme:input code="brotherhood.surname" path="surname" />
	<br />

	<acme:input code="brotherhood.photo" path="photo" />
	<br />

	<acme:input code="brotherhood.email" path="email" />
	<br />

	<form:label path="phoneNumber">
		<spring:message code="brotherhood.phone.number" />:&nbsp;
	</form:label>
	<form:input path="phoneNumber" id="phoneId"/>
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	<br />

	<acme:input code="brotherhood.address" path="address" />
	<br />
	
	<acme:input code="brotherhood.title" path="title" />
	<br />
	
	<acme:textarea code="brotherhood.pictures" path="pictures" />
	<br />
	
	<form:label path="establishmentDate">
	<spring:message code="brotherhood.establishmentDate" />
	</form:label>
	<form:input path="establishmentDate" value="${parsedEstablishmentDate}"/>
	<form:errors path="establishmentDate" cssClass="error" />
	<br />
	<br />
		
	<acme:select items="${areas}" itemLabel="name" code="brotherhood.area" path="area"/>
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

	<acme:back code="member.cancel" />

</form:form>

