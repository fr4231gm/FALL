<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form modelAttribute="area">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:input code="area.name" path="name" placeholder="Nuketown" />
	<acme:input code="area.pictures" path="pictures"
		placeholder="https://www.google.es" />


	<acme:submit name="save" code="area.save" />
	<acme:cancel code="area.cancel" url="/area/administrator/list.do" />
	<jstl:if test="${area.id != 0}">
		<acme:cancel url="area/administrator/delete.do?areaId=${area.id}"
			code="area.delete" />
	</jstl:if>


</form:form>
