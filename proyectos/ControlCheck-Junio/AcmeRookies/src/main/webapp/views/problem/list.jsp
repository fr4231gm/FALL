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

<display:table name="problems" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag">
	
	<display:column property="title" titleKey="problem.title" sortable="true"/>
		
	
	<display:column titleKey="problem.isDraft">
			<jstl:if test="${row.isDraft == 'true'}">
			<spring:message code="problem.yes"></spring:message>
			</jstl:if>
			<jstl:if test="${row.isDraft == 'false'}">
			<spring:message code="problem.no"></spring:message>
			</jstl:if>
</display:column> 

	<display:column titleKey ="problem.display">
		<acme:link link="problem/company/display.do?problemId=${row.id}"
		code = "problem.display"/>
	</display:column>
	
	<security:authorize access="hasRole('COMPANY')">
	
	<display:column titleKey="problem.edit">
	<jstl:if test="${row.isDraft == true}">
		<acme:link link="problem/company/edit.do?problemId=${row.id}" 	code ="problem.edit"/>
	</jstl:if>
	
	</display:column>
	
	<display:column titleKey="problem.delete">
	<acme:link link="problem/company/delete.do?problemId=${row.id}" code ="problem.delete"/>
	</display:column>
	
	</security:authorize>
</display:table>
<jstl:if test="${error eq 'true'}">
<p style="color:red;"><spring:message code="${messageError}"></spring:message></p>

	</jstl:if>
	<security:authorize access="hasRole('COMPANY')">
	<acme:link code ="problem.create" link="problem/company/create.do"/>
	</security:authorize>

<acme:back code="problem.goback" />