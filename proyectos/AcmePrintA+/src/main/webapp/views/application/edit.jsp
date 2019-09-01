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

<form:form	modelAttribute="application" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="order" />
	<form:hidden path="company" />
	<form:hidden path="status" />
	

	<acme:input code="application.offeredPrice" path="offeredPrice" placeholder="95.99"/>
	<acme:input code="application.companyComments" path="companyComments" placeholder="Write some comments about your application..."/>
		
	<button type="submit" name="save">
		<spring:message code="application.save" />
	</button>
	
	<acme:back code="application.goback"/>
		
</form:form>	