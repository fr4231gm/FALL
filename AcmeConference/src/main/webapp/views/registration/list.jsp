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

<display:table name="registrations" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<display:column property="conference.title" titleKey="registration.conference"
		sortable="true"/>
		
		<display:column property="creditCard.holder" titleKey="registration.creditCard" 
		sortable="true"/>
		
	<display:column>
	
		<acme:link link="registration/author/display.do?registrationId=${row.id}"
				code="registration.display" />
				
		
	</display:column>
	
	<display:column>
	
		<acme:link link="conference/display.do?conferenceId=${row.conference.id}"
				code="registration.conference.display" />
	
	</display:column>

</display:table>