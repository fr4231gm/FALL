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

<display:table name="reviewers" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<display:column property="name" titleKey="reviewer.name"
		sortable="true"/>
	
	<display:column property="keywords" titleKey="reviewer.keywords"
		sortable="true"/>
		
	<display:column>
		<acme:link link="reviewer/display.do?reviewerId=${row.id}" code = "reviewer.display"/>
	</display:column>	
		
</display:table>

<br>

<acme:back code="reviewer.goback" />