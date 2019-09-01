<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form modelAttribute = "paradeForm">
<fmt:formatDate value="${paradeForm.moment}" pattern="dd/MM/yyyy"
	var="parsedMoment" />
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="rows" />
	<form:hidden path="title"/>
	<form:hidden path="description"/>
	<form:hidden path="moment" value="${parsedMoment}" />
	<form:hidden path="ticker"/>
	<form:hidden path="status"/>
	<form:hidden path="isDraft"/>

	<acme:textarea code="parade.rejectReason" path="rejectReason"/><FONT COLOR="red"><jstl:if test="${error=='rejecto'}"><spring:message code="reject.blank"></spring:message></jstl:if></FONT>
	<br/>
	
	
	
		
	
		<acme:submit name="save" code="parade.save"/>
	<acme:cancel url="chapter/manageParades.do" code="parade.cancel"/> 
	
	

</form:form>