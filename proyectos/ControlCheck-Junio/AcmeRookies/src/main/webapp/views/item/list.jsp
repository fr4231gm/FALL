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

<display:table name="items" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag">
		
	<display:column property="name" titleKey="item.name" sortable="true"/>

	<display:column property="description" titleKey="item.description" sortable="true"/>
	
	<display:column property="link" titleKey="item.link" sortable="true"/>

	<display:column>
		<acme:link link="provider/display.do?providerId=${row.provider.id}" code = "item.provider"/>
	</display:column>
	
	<display:column>
		<acme:link link="item/display.do?itemId=${row.id}" code = "item.display"/>
	</display:column>
	
	<jstl:if test="${permiso}">
		<display:column>
			<acme:link link="item/provider/edit.do?itemId=${row.id}" code = "item.edit"/>
		</display:column>
		
		<display:column>
			<acme:link link="item/provider/delete.do?itemId=${row.id}" code = "item.delete"/>
		</display:column>
	</jstl:if>
	
</display:table>

<security:authorize access="hasRole('PROVIDER')">
	<acme:link code="item.create" link="item/provider/create.do"/>
</security:authorize>

<acme:cancel url="/provider/list.do" code="item.goback"/>