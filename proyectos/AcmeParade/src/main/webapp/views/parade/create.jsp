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
action = "parade/brotherhood/create.do"
modelAttribute = "paradeForm">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="ticker"/>
	
	<acme:input code="parade.rows" path="rows" placeholder="3" />
	<acme:input code="parade.title" path="title" placeholder="title" />
	<acme:date code="parade.moment" path="moment" />
	<acme:input code="parade.description" path="description" placeholder="description of the parade" />
	<acme:checkbox code="parade.isDraft" path="isDraft"/>
	
	<acme:select items="${floats}" itemLabel="title" code="parade.floats" path="floats"/>
		
	<acme:submit name="save" code="parade.save"/>
	<acme:cancel url="parade/brotherhood/list.do" code="parade.cancel"/>
</form:form>	
