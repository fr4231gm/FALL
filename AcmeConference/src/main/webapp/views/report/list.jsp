<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:choose>
	<jstl:when test="${langCode eq 'en'}">
		<jstl:set
			var = "format"
 			value = "{0, date, MM-dd-yy}"
		/>
	</jstl:when>
	<jstl:otherwise>
		<jstl:set
			var = "format"
 			value = "{0, date, dd-MM-yy}"
		/>
	</jstl:otherwise>
</jstl:choose>

<display:table
 	name 		= "reports" 
 	id 			= "row"
 	requestURI 	= "${requestURI}"
 	pagesize	= "5"
 	class		= "displaytag"
>
 	
	<display:column titleKey="report.decision" sortable="true">
		<jstl:if test="${row.decision eq 'BORDER-LINE'}">
			<spring:message code="report.border.line"/>
		</jstl:if>
		<jstl:if test="${row.decision eq 'ACCEPTED'}">
			<spring:message code="report.accepted"/>
		</jstl:if>
		<jstl:if test="${row.decision eq 'REJECTED'}">
			<spring:message code="report.rejected"/>
		</jstl:if>
	</display:column>
	
	<security:authorize access="hasRole('AUTHOR')">
		<display:column titleKey="report.submission">
	 		<a href ="submission/author/display.do?submissionId=${row.submission.id}">
	 			<jstl:out value="${row.submission.ticker}"/>
	 		</a>
	 	</display:column>
	<display:column titleKey="report.display">
	 		<a href ="report/author/display.do?reportId=${row.id}">
	 			<spring:message code="report.display"></spring:message>
	 		</a>
	 	</display:column>
	 	<display:column
	 		property	= "reviewer.name"
	 		titleKey	= "report.reviewer"
	 	/>
	</security:authorize>
	
		<security:authorize access="hasRole('ADMINISTRATOR')">
	
		<display:column titleKey="report.submission">
	 		<a href ="submission/administrator/display.do?submissionId=${row.submission.id}">
	 			<jstl:out value="${row.submission.ticker}"/>
	 		</a>
	 	</display:column>
	 	
		<display:column titleKey="report.display">
	 		<a href ="report/administrator/display.do?reportId=${row.id}">
	 			<spring:message code="report.display"></spring:message>
	 		</a>
	 	</display:column>
	 	
	 	<display:column
	 		property	= "submission.author.name"
	 		titleKey	= "report.author"
	 	/>
	 	
	 	<display:column
	 		property	= "reviewer.name"
	 		titleKey	= "report.reviewer"
	 	/>
	 	
	 	
	</security:authorize>
	
	<security:authorize access="hasRole('REVIEWER')">
	
		<display:column titleKey="report.submission">
	 		<a href ="submission/reviewer/display.do?submissionId=${row.submission.id}">
	 			<jstl:out value="${row.submission.ticker}"/>
	 		</a>
	 	</display:column>
	 	
		<display:column titleKey="report.display">
	 		<a href ="report/reviewer/display.do?reportId=${row.id}">
	 			<spring:message code="report.display"></spring:message>
	 		</a>
	 	</display:column>
	 	
	 	<display:column
	 		property	= "submission.author.name"
	 		titleKey	= "report.author"
	 	/>
	</security:authorize>

</display:table>

<acme:back code="master.go.back" />

