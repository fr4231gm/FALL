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

<form:form	modelAttribute="problemForm" action="problem/edit.do" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<acme:input code="problem.title" path="title" />
	<acme:input code="problem.statement" path="statement" />
	<acme:input code="problem.hint" path="hint" />
	<acme:input code="problem.attachments" path="attachments" />
	<acme:checkbox code="problem.isDraft" path="isDraft"/>
	
	<acme:submit name="save" code="problem.save"/>
	<acme:cancel url="/problem/list.do" code="problem.cancel"/>
	
</form:form>	