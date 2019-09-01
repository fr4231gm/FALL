
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


	<acme:input code="configuration.systemName" path="systemName" placeholder="Acme-De-Comer"/>
	<acme:input code="configuration.banner" path="banner" placeholder="https://www.google.es"/>
	<acme:input code="configuration.welcomeMessage" path="welcomeMessage"  placeholder="Hola Amigos de la ciencia"/>
	<acme:textarea code="configuration.spamWords" path="spamWords" placeholder="spam, words, separated, by, coma,"/>
	<acme:textarea code="configuration.positiveWords" path="positiveWords" placeholder="possitive, words, separated, by, coma,"/>
	<acme:textarea code="configuration.negativeWords" path="negativeWords" placeholder="negative, words, separated, by, coma,"/>
	
	<acme:input code="configuration.countryCode" path="countryCode" placeholder="00"/>
	<acme:input code="configuration.finderLifeSpan" path="finderLifeSpan" placeholder="12"/>
	<acme:input code="configuration.maxFinder" path="maxFinder" placeholder="16"/>
	<acme:textarea code="configuration.priorities" path="priorities" placeholder="low, neutral, high"/>
	<acme:textarea code="configuration.makes" path="makes" placeholder="VISA, MASTERCARD, MAESTRO"/>
	<acme:input code="configuration.vat" path="vat" placeholder="0.1"/>
	<acme:input code="configuration.fare" path="fare" placeholder="5"/>


	<acme:submit name="save" code="configuration.save" />
	<acme:cancel url="${cancelURI}" code="configuration.cancel" />

</form:form>
