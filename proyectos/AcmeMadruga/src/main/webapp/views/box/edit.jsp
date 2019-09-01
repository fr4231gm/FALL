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

	
<form:form modelAttribute="box">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="messages" />
	<form:hidden path="actor" />
	<form:hidden path="isSystem" />


<acme:input code="box.name" path="name"/>
	<section>
		<form:label path="parentBox">
			<spring:message code="box.parent" />:&nbsp;
		</form:label>
		<form:select id="boxes" path="parentBox">
			<form:option itemLabel="root" value="" />
			<form:options items="${boxes}" itemLabel="name" itemValue="id" />
		</form:select>
	</section>


	<acme:back code="member.cancel" />
	<acme:submit name="save" code="box.save" />

</form:form>















