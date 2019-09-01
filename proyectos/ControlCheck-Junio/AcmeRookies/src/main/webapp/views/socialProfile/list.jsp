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

<display:table name="social" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<div>
	<acme:column titleKey="socialprofile.nick" property="nick"/>
		<display:column>
			<acme:link code="socialprofile.show" link="social-profile/show.do?socialProfileId=${row.id}"/>
		</display:column>
		<display:column>
			<acme:link code="socialprofile.edit" link="social-profile/edit.do?socialProfileId=${row.id}"/>
		</display:column>

		<display:column>
			<acme:link code="socialprofile.delete" link="social-profile/remove.do?id=${row.id}"/>
		</display:column>


	</div>
</display:table>

<acme:link code="socialprofile.create" link="social-profile/create.do?actorId=${id}"/>


