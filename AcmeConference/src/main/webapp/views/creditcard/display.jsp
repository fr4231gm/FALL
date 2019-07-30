<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <%@taglib prefix="display" uri="http://displaytag.sf.net"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<acme:input 	code="creditcard.holder"	path="creditcard.holder"	readonly="true" />
<acme:input 	code="creditcard.number"	path="creditcard.number"	readonly="true" />
<acme:input 	code="creditcard.expirationMonth"	path="creditcard.expirationMonth"	readonly="true" />
<acme:input 	code="creditcard.expirationYear"	path="creditcard.expirationYear"	readonly="true" />
<acme:input 	code="creditcard.make"	path="creditcard.make"	readonly="true" />
<acme:input 	code="creditcard.CVV"	path="creditcard.CVV"	readonly="true" />
<acme:back 	code="master.go.back" />