<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@taglib prefix="display" uri="http://displaytag.sf.net"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<form:form action="${actionURI}" modelAttribute="creditcard">	<form:hidden path="id" />	<form:hidden path="version" />	<acme:input 		code="creditcard.holder"		path="holder"		placeholder="uwu" 	/>
	<acme:input 		code="creditcard.number"		path="number"		placeholder="uwu" 	/>
	<acme:input 		code="creditcard.expirationMonth"		path="expirationMonth"		placeholder="5" 	/>
	<acme:input 		code="creditcard.expirationYear"		path="expirationYear"		placeholder="5" 	/>
	<acme:input 		code="creditcard.make"		path="make"		placeholder="uwu" 	/>
	<acme:input 		code="creditcard.CVV"		path="CVV"		placeholder="5" 	/>
	<acme:back 		code="master.go.back" 	/>
	<acme:submit 		name="save" 		code="master.save" 	 />
</form:form>