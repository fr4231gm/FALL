
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
	<fmt:formatDate value="${m.moment}" pattern="MM/dd/yyyy"
		var="parsedMoment" />
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" value="${parsedMoment}" />
	<form:hidden path="isSpam" />
	<form:hidden path="boxes" />
	<form:hidden path="sender" />



	<spring:message code="message.subject" />:&nbsp;
	<form:input path="subject" value="${m.subject}" placeholder="Title"/>
	<form:errors cssClass="error" path="subject" />


	<b> <spring:message code="message.body" />:&nbsp;
	</b>
	<form:textarea path="body" value="${m.body}" rows="5" placeholder="the reason of this message is..." />
	<form:errors cssClass="error" path="body" />

	<b> <spring:message code="message.recipients" />:&nbsp;
	</b>
	<form:select multiple="true" id="recipients" path="recipients">
		<form:options items="${recipients}" itemLabel="name" itemValue="id" />
	</form:select>

	<b> <spring:message code="message.tags" />:&nbsp;
	</b>
	<form:input path="tags" value="${m.tags}" placeholder="Information" />
	<form:errors cssClass="error" path="tags" />

	<b><spring:message code="message.priority" />:&nbsp;</b>
	
	<form:select path="priority" disabled="false">
	<jstl:forEach items="${ priorities}" var="row">
		<form:option value="${row }">
			${row}
		</form:option>
		</jstl:forEach>
	</form:select>
	
	<form:errors cssClass="error" path="priority" />

	<acme:cancel code="member.cancel" url="/box/list.do"/>
	<acme:submit name="save" code="box.save" />



</form:form>















