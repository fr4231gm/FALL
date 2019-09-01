

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="complaint/customer/edit.do" modelAttribute="complaint">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="ticker" />
	<form:hidden path="fixUpTask" />
	<form:hidden path="referee" />
	<form:hidden path="report" />
	<form:hidden path="customer" />
	<form:hidden path="moment" />

	<form:label path="description">
		<spring:message code="complaint.description" />:
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />
	<br>

	<form:label path="attachments">
		<spring:message code="complaint.attachments" />:
	</form:label>
	<form:input path="attachments" />
	<form:errors cssClass="error" path="attachments" />
	<br>

	<input type="submit" name="save"
		value="<spring:message code="complaint.save" />" 
		onclick="return confirm('<spring:message code="complaint.confirm.save" />')" />&nbsp; 
	<jstl:if test="${complaint.id != 0} and ${complaint.isDraft == true}" >
		<input type="submit" name="delete"
			value="<spring:message code="complaint.delete" />"
			onclick="return confirm('<spring:message code="complaint.confirm.delete" />')" />&nbsp;
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="complaint.cancel" />"
		onclick="javascript: relativeRedir('complaint/list.do');" />
	<br>

</form:form>