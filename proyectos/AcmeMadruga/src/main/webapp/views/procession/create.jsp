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



	
<form:form 
action = "procession/brotherhood/create.do"
modelAttribute = "processionForm">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="ticker"/>
	
	<acme:input code="procession.rows" path="rows"/>
	<acme:input code="procession.title" path="title"/>
	<acme:date code="procession.moment" path="moment"/>
	<acme:input code="procession.description" path="description"/>
	<acme:checkbox code="procession.isDraft" path="isDraft"/>
	
	<acme:select items="${floats}" itemLabel="title" code="procession.floats" path="floats"/>
		
	<acme:submit name="save" code="procession.save"/>
	<acme:cancel url="procession/brotherhood/list.do" code="procession.cancel"/>
</form:form>	
