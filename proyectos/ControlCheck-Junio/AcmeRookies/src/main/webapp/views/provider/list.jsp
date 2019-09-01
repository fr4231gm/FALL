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

<display:table name="providers" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag">
	
	<display:column property="make" titleKey="provider.make" sortable="true"/>
		
	<display:column property="name" titleKey="provider.name" sortable="true"/>

	<display:column property="surname" titleKey="provider.surname" sortable="true"/>
	
	<display:column property="vatNumber" titleKey="provider.vatNumber" sortable="true"/>
	
	<display:column property="photo" titleKey="provider.photo" sortable="true"/>
	
	<display:column property="email" titleKey="provider.email" sortable="true"/>
	
	<display:column property="phoneNumber" titleKey="provider.phoneNumber" sortable="true"/>
	
	<display:column property="address" titleKey="provider.address" sortable="true"/>

	<display:column>
		<acme:link link="item/list.do?providerId=${row.id}"
		code = "provider.items"/>
	</display:column>
</display:table>

<acme:back code="provider.goback" />