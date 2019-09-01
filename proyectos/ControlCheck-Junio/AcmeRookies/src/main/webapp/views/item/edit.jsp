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

<form:form	modelAttribute="item" action="item/provider/edit.do" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="provider" />
	
	<acme:input code="item.name" path="name"  placeholder="Name of item"/>
	<acme:input code="item.description" path="description"  placeholder="Description of the item"/>
	<acme:input code="item.link" path="link"  placeholder="https://www.link.com"/>
	<acme:textarea code="item.pictures" path="pictures"  placeholder="https://www.link1.com, https://www.link2.com, etc."/>
	
	<acme:submit name="save" code="item.save"/>
	<acme:cancel code="item.cancel" url="/item/provider/list.do"/>
	
</form:form>	