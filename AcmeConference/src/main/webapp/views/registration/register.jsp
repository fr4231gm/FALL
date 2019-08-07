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

<form:form action="registration/register.do"  modelAttribute="registration" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="author" />
	<form:hidden path="conference" />
	
	<div class="centrado">
		<i class="fa fa-credit-card"></i>   
		<spring:message code="creditCard" />
		<br><br>
	</div>
	<acme:input code="creditCard.holder" path="creditCard.holder"/>
	<div class="form-group">
		<form:label path="creditCard.make"> <spring:message code="creditCard.make" /> </form:label>
	   	<form:select path="creditCard.make" >
	       	<form:options items="${makes}" />
	   	</form:select>
		<form:errors path="creditCard.make" cssClass="error" />
	</div> 
	<br>
	
	<acme:input code="creditCard.number" path="creditCard.number"/>
	<acme:input code="creditCard.expirationMonth" path="creditCard.expirationMonth"/>
	<acme:input code="creditCard.expirationYear" path="creditCard.expirationYear"/>
	<acme:input code="creditCard.CVV" path="creditCard.CVV"/>
	
	<acme:submit name="save" code="actor.save"/>
	<acme:cancel code="actor.cancel" url="/"/>
	
</form:form>	