<%-- edit.jsp phase --%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="${actionURI}" modelAttribute="creditCard">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<spring:message var="holdernamePH" code="creditcard.holdername.placeholder"></spring:message>
	<spring:message var="numberPH" code='creditcard.number.placeholder'></spring:message>
	
	
	<b><form:label path="holderName">
		<spring:message code="creditcard.holdername"></spring:message></form:label>:&nbsp;</b>
	<form:input path="holderName"
		placeholder="${holdernamePH}" />
	<form:errors path="holderName" cssClass="error" />
	<br />

	<b><form:label path="brandName">
		<spring:message code="creditcard.brandname"></spring:message>:&nbsp;</form:label></b>
	<form:select path="brandName" disabled="false">
	<!--  	<form:option label="----" value="0" /> -->
		<form:options items="${brandname}" />
	</form:select>
	<form:errors path="brandName" cssClass="error" />
	<br />

	<b><form:label path="number"> <spring:message
			code="creditcard.number"></spring:message>:&nbsp;</form:label></b>
	<form:input path="number"
		placeholder="${numberPH}" />
	<form:errors path="number" cssClass="error" />
	<br />

	<b><form:label path="expirationMonth">
		<spring:message code="creditcard.month"></spring:message>:&nbsp;</form:label></b>
	<form:input path="expirationMonth" placeholder="MM" />
	<form:errors path="expirationMonth" cssClass="error" />
	<br />

	<b><form:label path="expirationYear">
		<spring:message code="creditcard.year"></spring:message>:&nbsp;</form:label></b>
	<form:input path="expirationYear" placeholder="yy" />
	<form:errors path="expirationYear" cssClass="error" />
	<br />

	<b><form:label path="CVV">CVV:&nbsp;</form:label></b>
	<form:input path="CVV" placeholder="123" />
	<form:errors path="CVV" cssClass="error" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code='creditcard.save'></spring:message>"
		onclick="javascript: relativeRedir('${actionURI}');" />
	<input type="button" name="cancel"
		value="<spring:message code='phase.cancel'></spring:message>"
		onclick="javascript: window.location.href=document.referrer" />

</form:form>
