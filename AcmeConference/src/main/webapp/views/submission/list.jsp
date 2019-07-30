<<<<<<< HEAD
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@taglib prefix="display" uri="http://displaytag.sf.net"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<jstl:choose>	<jstl:when test="${langCode eq 'en'}">		<jstl:set			var = "format" 			value = "{0, date, MM-dd-yy HH:mm}"		/>	</jstl:when>	<jstl:otherwise>		<jstl:set			var = "format" 			value = "{0, date, dd-MM-yy HH:mm}"		/>	</jstl:otherwise></jslt:choose>
<display:table 	name 		= "submissions"  	id 			= "row" 	requestURI 	= "${requestURI}" 	pagesize	= "5" 	class		= "displaytag">
	<display:column 		property	= "ticker" 		titleKey	= "submission.ticker" 		sortable	= "true" 	/>
	<display:column 		property	= "moment" 		titleKey	= "submission.moment" 		format		= "${format}" 		sortable	= "true" 	/>
	<display:column 		property	= "status" 		titleKey	= "submission.status" 		sortable	= "true" 	/>
	<display:column titleKey="submission.Conference"> 		<acme:link 			code="submission.Conference" 			link="Conference/display.do?ConferenceId=${submissionConference.id}" 		/> 	</display:column>
</display:table>
<acme:back 		code="master.go.back" 	/>
<input 	type="button" 	name="create" 	value="<spring:message code="submission.create"/>" 	onclick="redirect: location.href = 'submission/create.do';" />"
=======
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


<display:table name="submissions" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<display:column property="ticker" titleKey="submission.ticker"
		sortable="true"/>
		
		<display:column property="moment" titleKey="submission.moment"
		sortable="true"	format="{0, date, dd/MM/yyyy HH:mm}"/>
		
		
		
			<display:column titleKey="submission.status" sortable="true">
		<jstl:if test="${row.status eq 'UNDER-REVIEW'}">
			<spring:message code="submission.underreview"/>
		</jstl:if>
		<jstl:if test="${row.status eq 'ACCEPTED'}">
			<spring:message code="submission.accepted"/>
		</jstl:if>
		<jstl:if test="${row.status eq 'REJECTED'}">
			<spring:message code="submission.rejected"/>
		</jstl:if>
	</display:column>
	
	<display:column>
		<acme:link link="submission/author/display.do?submissionId=${row.id}" code = "submission.display"/>
	</display:column>
	
	
	

</display:table>

<br>

<acme:back code="conference.goback" />
>>>>>>> developer
