<%-- application listing for handyworker --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table name="actors" id="row" requestURI="${requestURI}" pagesize="6" class="displaytag">
	<div>
		<display:column
			property="name"
			titleKey="actor.name"></display:column>
	
		<display:column
			property="surname"
			titleKey="actor.surname"></display:column>
			
	 	<display:column
			property="userAccount.username"
			titleKey="actor.username"></display:column>
		<display:column> 
				<a href="actor/show.do?id=${row.id}"><spring:message
						code="actor.profile"></spring:message></a>
		</display:column>
	</div>	
</display:table>


