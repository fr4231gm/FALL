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

<form:form action="registration/author/register.do"  modelAttribute="registration" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="author" />
	<form:hidden path="conference" />
	
	<div class="centrado">
		<i class="fa fa-credit-card"></i>   
		<spring:message code="creditCard" />
		<br><br>
	</div>
	<acme:input code="creditCard.holder" path="creditCard.holder" placeholder="Juan Pérez Prado"/>
	<div class="form-group">
		<form:label path="creditCard.make"> <spring:message code="creditCard.make" /> </form:label>
	   	<form:select path="creditCard.make" >
	       	<form:options items="${makes}" />
	   	</form:select>
		<form:errors path="creditCard.make" cssClass="error" />
	</div> 
	<br>
	
	<acme:input code="creditCard.number" path="creditCard.number" placeholder="5332563560352168"/>
	<acme:input code="creditCard.expirationMonth" path="creditCard.expirationMonth" type="number" placeholder="07"/>
	<acme:input code="creditCard.expirationYear" path="creditCard.expirationYear" type="number" placeholder="24"/>
	<acme:input code="creditCard.CVV" path="creditCard.CVV" type="number" placeholder="324"/>
	
	<acme:submit name="save" code="actor.save"/>
	<acme:cancel code="actor.cancel" url="/"/>
	
</form:form>	