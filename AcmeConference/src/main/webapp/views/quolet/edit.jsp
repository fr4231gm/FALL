<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@taglib prefix="display" uri="http://displaytag.sf.net"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<form:form action="${actionURI}" modelAttribute="quolet">	<form:hidden path="id" />	<form:hidden path="version" />	<form:hidden path="administrator" /> 	<form:hidden path="conference" /> 	<acme:input 		code="quolet.ticker"		path="ticker"		placeholder="uwu"		readonly="true" 	/> 	 	<acme:input 		code="quolet.title"		path="title"		placeholder="Title" 	/> 	
	<acme:input 		code="quolet.body"		path="body"		placeholder="This is a body" 	/>
	<acme:input 		code="quolet.atributo1"		path="atributo1"		placeholder="uwu" 	/>
	<acme:checkbox 		code="quolet.isDraft"		path="isDraft" 	/>
	<acme:back 		code="master.go.back" 	/>
	<acme:submit 		name="save" 		code="master.save" 	 />
</form:form>