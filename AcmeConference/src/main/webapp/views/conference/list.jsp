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

<display:table name="conferences" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<display:column property="title" titleKey="conference.title"
		sortable="true"/>
		
		<display:column property="acronym" titleKey="conference.acronym"
		sortable="true"/>
		
		<display:column property="startDate" titleKey="conference.startDate"
		sortable="true" format="{0, date, dd/MM/yyyy HH:mm}"/>
		
			<display:column property="endDate" titleKey="conference.endDate"
		sortable="true" format="{0, date, dd/MM/yyyy HH:mm}"/>
		
	<display:column>
	
		<acme:link link="conference/display.do?conferenceId=${row.id}"
				code="conference.display" />

	</display:column>
	
	
	

	

</display:table>

<br>

<acme:back code="conference.goback" />