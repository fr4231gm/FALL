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



<form:form	modelAttribute="applicationForm" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="creationMoment" />
	<form:hidden path="submittedMoment" />
	<form:hidden path="position" />
	
	<h3><spring:message code="application.problem"></spring:message></h3>
	<fieldset>
		<p><strong><spring:message code="problem.company"></spring:message>:</strong> ${problem.company.commercialName}</p>
		<p><strong><spring:message code="problem.title"></spring:message>:</strong> ${problem.title}</p>
		<p><strong><spring:message code="problem.statement"></spring:message>:</strong> ${problem.statement}</p>
		<p><strong><spring:message code="problem.hint"></spring:message>:</strong> ${problem.hint}</p>
		<p><strong><spring:message code="problem.attachments"></spring:message>:</strong> ${problem.attachments}</p>
	</fieldset>
	<jstl:if test="${guardar eq 'false' }"><h4><spring:message code="${crearCurricula}"></spring:message></h4></jstl:if>
	
	<p><em><spring:message code="application.submit.message"></spring:message></em></p>

	<acme:input code="application.answer" path="answer"/>
	<acme:input code="application.linkCode" path="linkCode" placeholder="https://www.urlToCode.com"/>
	
	<acme:show code="application.status" path="status"/>
	
	<acme:select items="${curriculas}" itemLabel="personalData.statement" code="application.curricula" path="curricula"/>
	
	<br>
	<jstl:if test="${guardar eq 'true'}"><button type="submit" name="save">
		<spring:message code="application.save" />
	</button></jstl:if>
	
	
	<acme:cancel code="application.goback" url="/position/list.do"/>
	
	
	
	
</form:form>	