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
	
<form:form modelAttribute="wolem">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker"/>
	<form:hidden path="publicationMoment"/>
	<form:hidden path="audit"/>
	
	<acme:textarea code="wolem.body" path="body" placeholder="Body"/>
	<br />
	
	<acme:input code="wolem.picture" path="picture" placeholder="https://www.facebook.com/corteingles"/>
	<br />
	
	<acme:checkbox code="wolem.isDraft" path="isDraft"/>
	<br />
	
	<acme:cancel url="wolem/company/list.do" code="wolem.cancel"/>
	<acme:submit name="save" code="wolem.save"/>

</form:form>
	