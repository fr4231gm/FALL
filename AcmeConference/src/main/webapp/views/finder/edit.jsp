<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@taglib prefix="display" uri="http://displaytag.sf.net"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<form:form action="${actionURI}" modelAttribute="finder">	<form:hidden path="id" />	<form:hidden path="version" />	<form:hidden path="conferences" /> 	<acme:input 		code="finder.keyWord"		path="keyWord"		placeholder="uwu" 	/>
	<acme:input 		code="finder.acronym"		path="acronym"		placeholder="uwu" 	/>
	<acme:input 		code="finder.venue"		path="venue"		placeholder="uwu" 	/>
	<acme:input 		code="finder.summary"		path="summary"		placeholder="uwu" 	/>
	<acme:date 		code="finder.startDate"		path="startDate" 	/>
	<acme:date 		code="finder.endDate"		path="endDate" 	/>
	<acme:input 		code="finder.fee"		path="fee"	placeholder="5.00" 	/>
	<acme:back 		code="master.go.back" 	/>
	<acme:submit 		name="save" 		code="master.save" 	 />
</form:form>