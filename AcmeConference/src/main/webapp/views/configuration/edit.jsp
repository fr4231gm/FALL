
<%-- edit.jsp configuration --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
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

<form:form modelAttribute="configuration">

	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="language" />
	<form:hidden path="buzzWords"/>
	


	<acme:input code="configuration.systemName" path="systemName" placeholder="Acme-De-Comer"/>
	<acme:input code="configuration.banner" path="banner" placeholder="https://www.google.es"/>
	<acme:input code="configuration.welcomeMessage" path="welcomeMessage"  placeholder="Hola Amigos de la ciencia"/>
	<acme:textarea code="configuration.topics" path="topics" placeholder="topics, separated, by, coma,"/>
	<acme:input code="configuration.countryCode" path="countryCode" placeholder="00"/>

	<acme:textarea code="configuration.make" path="make" placeholder="VISA, MAESTRO, MASTERCARD"/>
	<acme:textarea code="configuration.voidWords" path="voidWords" placeholder="void1, void2, void3"/>
	
	<acme:submit name="save" code="configuration.save" />
	<acme:cancel url="${cancelURI}" code="configuration.cancel" />

</form:form>
