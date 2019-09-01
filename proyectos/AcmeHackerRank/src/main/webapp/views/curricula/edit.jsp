<%--
 * 
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:set var="phoneConfirmationPrefix">
<spring:message code="hacker.phone.confirmation.prefix"/> 
</jstl:set>

<jstl:set var="phoneConfirmationSuffix">
<spring:message code="hacker.phone.confirmation.suffix"/> 
</jstl:set>

<form:form action="curricula/hacker/edit.do" modelAttribute="personalData" id="personalData"
onsubmit='return phoneValidation("${phoneConfirmationPrefix}", "${phoneConfirmationSuffix}")'>>

	<form:hidden path="id" />
	
	<form:hidden path="version" />

	<fieldset>
	<legend><spring:message code="curricula.personalData" />: </legend>
	
	<form:label path="fullName">
		<spring:message code="curricula.personalData.fullName" />:
	</form:label>
	<form:input path="fullName" placeholder="Jose Juan Pérez" />
	<form:errors cssClass="error" path="fullName" />
	<br />
	<br />
	
	<form:label path="statement">
		<spring:message code="curricula.personalData.statement" />:
	</form:label>
	<form:input path="statement" placeholder="Al oeste Filadelfia crecía y vivia" />
	<form:errors cssClass="error" path="statement" />
	<br />
	<br />
	
	<form:label path="phoneNumber">
		<spring:message code="curricula.personalData.phoneNumber" />:
	</form:label>
	<form:input path="phoneNumber" id="phoneId" placeholder="66213056" />
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	<br />
	
	<form:label path="gitHubLink">
		<spring:message code="curricula.personalData.GitHubLink" />:
	</form:label>
	<form:input path="gitHubLink" placeholder="https://www.yahoo.es" />
	<form:errors cssClass="error" path="gitHubLink" />
	<br />
	<br />
	
	<form:label path="linkedInLink">
		<spring:message code="curricula.personalData.LinkedInLink" />:
	</form:label>
	<form:input path="linkedInLink" placeholder="https://www.yahoo.es" />
	<form:errors cssClass="error" path="linkedInLink" />
	<br />
	<br />
	
	</fieldset>
	<br>
	<br>

<input type="submit" name="save" id="save" value="<spring:message code="curricula.save" />" />&nbsp; 
	<jstl:if test="${personalData.id != 0}">
		<input type="submit" name="delete" value="<spring:message code="curricula.delete" />"
			onclick="return confirm('<spring:message code="curricula.confirm.delete" />')" />&nbsp;
	</jstl:if>

	<input type="button" name="cancel" value="<spring:message code="curricula.cancel" />"
		onclick="javascript: relativeRedir('/curricula/hacker/list.do');" />
	<br />

</form:form>










