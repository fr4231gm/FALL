<%-- elreyrata did that --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="note/edit.do" modelAttribute="note">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="report" />
	<form:hidden path="moment"/>

	<security:authorize access="hasRole('CUSTOMER')">

		<form:label path="customerComments">
			<spring:message code="note.customerComments" />:
		</form:label>
		<form:input path="customeomments" />
		<form:errors cssClass="error" path="customerComments" />
		<br>

	</security:authorize>

	<security:authorize access="hasRole('REFEREE')">

		<form:label path="refereeComments">
			<spring:message code="note.refereeComments" />:
		</form:label>
		<form:input path="refereeComments" />
		<form:errors cssClass="error" path="refereeComments" />
		<br>
		
	</security:authorize>

	<security:authorize access="hasRole('HANDYWORKER')">

		<form:label path="handyworkerComments">
			<spring:message code="note.handyworkerComments" />:
		</form:label>
		<form:input path="handyworkerComments" />
		<form:errors cssClass="error" path="handyworkerComments" />
		<br>
		
	</security:authorize>

	<input type="submit" name="save"
		value="<spring:message code="note.save" />" />&nbsp; 

	<input type="button" name="cancel"
		value="<spring:message code="note.cancel" />"
		onclick="javascript: relativeRedir('note/customer/list.do');" />
	<br>

</form:form>