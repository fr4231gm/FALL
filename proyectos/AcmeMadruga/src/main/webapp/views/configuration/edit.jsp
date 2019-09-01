
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


	<acme:input code="configuration.systemName" path="systemName" />
	<acme:input code="configuration.banner" path="banner" />
	<acme:input code="configuration.welcomeMessage" path="welcomeMessage" />
	<acme:textarea code="configuration.spamWords" path="spamWords" />
	<acme:textarea code="configuration.positiveWords" path="positiveWords" />
	<acme:textarea code="configuration.negativeWords" path="negativeWords" />
	
	<acme:input code="configuration.countryCode" path="countryCode" />
	<acme:input code="configuration.finderLifeSpan" path="finderLifeSpan" />
	<acme:input code="configuration.maxFinder" path="maxFinder" />
	<acme:textarea code="configuration.priorities" path="priorities" />


	<acme:submit name="save" code="configuration.save" />
	<acme:cancel url="${cancelURI}" code="configuration.cancel" />

</form:form>
