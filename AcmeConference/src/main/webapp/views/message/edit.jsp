
<%-- edit.jsp fix-up task --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="m">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:input code="message.subject" path="subject" placeholder="Hello moto" />
	<acme:textarea code="message.body" path="body"  placeholder="the reason of this message is..." />
	<acme:select itemLabel="name" items="${recipients}" code="message.recipients" path="recipients"  multiple="true" id="recipients" />
	
	<div class="form-group">
		<form:label path="topic"> <spring:message code="message.topic" /> </form:label>
    	<form:select path="topic" >
        	<form:options items="${topics}" />
    	</form:select>
		<form:errors path="topic" cssClass="error" />
	</div>
	<br>

	<acme:cancel code="message.cancel" url="/message/list.do"/>
	<acme:submit name="save" code="message.save" />

</form:form>















