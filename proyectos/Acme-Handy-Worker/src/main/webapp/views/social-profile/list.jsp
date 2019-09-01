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


<display:table name="social" id="row" requestURI="${requestURI}"
	pagesize="6" class="displaytag">
	<div>
		<display:column property="nick" titleKey="socialprofile.nick"></display:column>

		<display:column>

			<a href="socialprofile/show.do?socialProfileId=${row.id}"> <spring:message
					code="socialprofile.show"></spring:message>
			</a>

		</display:column>
		<display:column>

			<a href="socialprofile/edit.do?socialProfileId=${row.id}"> <spring:message
					code="socialprofile.edit"></spring:message>
			</a>

		</display:column>

		<display:column>

			<a href="socialprofile/remove.do?id=${row.id}"> <spring:message
					code="socialprofile.delete"></spring:message>
			</a>

		</display:column>


	</div>
</display:table>


<a href="socialprofile/create.do?actorId=${id}"><spring:message
		code="socialprofile.create"></spring:message></a>

<script>
$(document).ready( function () {	
	
	
    $('#row').dataTable( {
    	"language": {
        	"url": '${tableLang}'
    	},
		"lengthMenu": [ 5, 10, 25, 50, 100 ]
    } );
} );
</script>

