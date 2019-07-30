<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@taglib prefix="display" uri="http://displaytag.sf.net"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<form:form action="${actionURI}" modelAttribute="report">	<form:hidden path="id" />	<form:hidden path="version" />	<form:hidden path="comments" /> 	<form:hidden path="notified" /> 	<form:hidden path="reviewer" /> 	<form:hidden path="conference" /> 	<acme:input 		code="report.decision"		path="decision"		placeholder="uwu" 	/>
	<acme:input 		code="report.originalityScore"		path="originalityScore"	placeholder="5.00" 	/>
	<acme:input 		code="report.qualityScore"		path="qualityScore"	placeholder="5.00" 	/>
	<acme:input 		code="report.readabilityScore"		path="readabilityScore"	placeholder="5.00" 	/>
	<acme:back 		code="master.go.back" 	/>
	<acme:submit 		name="save" 		code="master.save" 	 />
</form:form>