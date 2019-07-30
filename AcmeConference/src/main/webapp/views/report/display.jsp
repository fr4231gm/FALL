<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@taglib prefix="display" uri="http://displaytag.sf.net"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<acme:input 	code="report.decision"	path="report.decision"	readonly="true" />
<acme:input 	code="report.originalityScore"	path="report.originalityScore"	readonly="true" />
<acme:input 	code="report.qualityScore"	path="report.qualityScore"	readonly="true" />
<acme:input 	code="report.readabilityScore"	path="report.readabilityScore"	readonly="true" />
<acme:input 	code="report.comments"	path="report.comments"	readonly="true" />
<acme:link 	code="report.reviewer" 	link="Conference/display.do?ConferenceId=${reportreviewer.id}" />
<acme:link 	code="report.conference" 	link="Cnference/display.do?CnferenceId=${reportconference.id}" />
<acme:back 	code="master.go.back" />