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

<display:table name="audits" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<display:column property="moment" titleKey="audit.moment"
		sortable="true" format="{0, date, dd/MM/yyyy HH:mm}"/>
		
	<display:column property="position.title" titleKey="audit.status"
		sortable="true" />
	
	<display:column property="auditor.name" titleKey="audit.auditor"
		sortable="true" />
	
	<display:column property="score" titleKey="audit.score"
		sortable="true" />

	<security:authorize access="hasRole('AUDITOR')">
	<display:column titleKey="audit.isDraft" sortable="true">
			<jstl:if test="${row.isDraft == 'true'}">
				<spring:message code="actor.yes"></spring:message>
			</jstl:if>
			<jstl:if test="${row.isDraft == 'false'}">
				<spring:message code="actor.no"></spring:message>
			</jstl:if>
		</display:column>
		
	</security:authorize>
	
	<display:column 
	titleKey="message.show">
		<acme:link link="audit/display.do?auditId=${row.id}" code="message.show" />
	</display:column >
	
	
	<display:column 
	titleKey="audit.edit">
			<security:authorize access="hasRole('AUDITOR')">
		<jstl:if test="${!audit.isDraft}">
	<a href="audit/auditor/edit.do?auditId=${row.id}"><spring:message code="audit.edit" /></a>
	</jstl:if>
		</security:authorize>
	</display:column >
		
		
	
</display:table>
<br>
		<security:authorize access="hasRole('AUDITOR')">
	<li><a href="audit/auditor/create.do"><spring:message code="master.page.auditor.audit.create" /></a></li>
		</security:authorize>
<br>

<acme:back code="application.goback" />