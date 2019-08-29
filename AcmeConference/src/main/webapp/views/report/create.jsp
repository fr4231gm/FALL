<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="report">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="reviewer" /> 
	<form:hidden path="submission" /> 

	<acme:input
 		code="report.originalityScore"
		path="originalityScore"
		placeholder="4.90"
 	/>

	<acme:input
 		code="report.qualityScore"
		path="qualityScore"
		placeholder="7.50"
 	/>

	<acme:input
 		code="report.readabilityScore"
		path="readabilityScore"
		placeholder="8.75"
 	/>
 	
	<acme:textarea
 		code="report.comments"
		path="comments"
		placeholder="you got this score because..."
 	/>

 	
 	<div class="form-group">
		<form:label path="decision">
			<spring:message code="report.decision" />
		</form:label>	
		<form:select path="decision" multiple="false">	
			<form:option value="ACCEPTED"><spring:message code="report.accepted"/></form:option>
			<form:option value="BORDER-LINE"><spring:message code="report.border.line"/></form:option>
			<form:option value="REJECTED"><spring:message code="report.rejected"/></form:option>
		</form:select>
		<form:errors path="decision" cssClass="error" />
	</div>

	<acme:back code="master.go.back" />
	<acme:submit name="save" code="master.save"  />

</form:form>