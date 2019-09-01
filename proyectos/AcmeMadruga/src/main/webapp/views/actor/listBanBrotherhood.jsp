<%-- workplan --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="actor" id="row" requestURI="${requestURI}"
	pagesize="6" class="displaytag">
	<div>
	
	<acme:column titleKey="actor.name" property="name"/>
	<acme:column titleKey="actor.middleName" property="middleName"/>
	<acme:column titleKey="actor.surname" property="surname"/>
	<acme:column titleKey="actor.email" property="email"/>
	<acme:column titleKey="actor.polarity" property="polarity"/>

	<display:column>
			<acme:link code="actor.ban" link="actor/administrator/banBrotherhood.do?brotherhoodId=${row.id}"/>
		</display:column>
		
	

	</div>
</display:table>

<acme:cancel url="/" code="actor.cancel"/>

