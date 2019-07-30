<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@taglib prefix="display" uri="http://displaytag.sf.net"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<jstl:choose>	<jstl:when test="${langCode eq 'en'}">		<jstl:set			var = "format" 			value = "{0, date, MM-dd-yy HH:mm}"		/>	</jstl:when>	<jstl:otherwise>		<jstl:set			var = "format" 			value = "{0, date, dd-MM-yy HH:mm}"		/>	</jstl:otherwise></jslt:choose>
<display:table 	name 		= "reports"  	id 			= "row" 	requestURI 	= "${requestURI}" 	pagesize	= "5" 	class		= "displaytag">
	<display:column 		property	= "decision" 		titleKey	= "report.decision" 		sortable	= "true" 	/>
	<display:column 		property	= "originalityScore" 		titleKey	= "report.originalityScore" 		sortable	= "true" 	/>
	<display:column 		property	= "qualityScore" 		titleKey	= "report.qualityScore" 		sortable	= "true" 	/>
	<display:column 		property	= "readabilityScore" 		titleKey	= "report.readabilityScore" 		sortable	= "true" 	/>
	<display:column titleKey="report.conference"> 		<acme:link 			code="report.conference" 			link="Cnference/display.do?CnferenceId=${reportconference.id}" 		/> 	</display:column>
</display:table>
<acme:back 		code="master.go.back" 	/>
<input 	type="button" 	name="create" 	value="<spring:message code="report.create"/>" 	onclick="redirect: location.href = 'report/create.do';" />"
