

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
	<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- Listing grid -->

	<display:table name="notes" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag" keepStatus="true">

		<!-- Attributes -->

		<spring:message code="note.moment" var="momentHeader" />
		<display:column property="moment" title="${momentHeader}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

		<spring:message code="note.refereeComments" var="refereeCommentsHeader" />
		<display:column property="refereeComments" title="${refereeCommentsHeader}" sortable="false" />

		<spring:message code="note.handyworkerComments" var="handyworkerCommentsHeader" />
		<display:column property="handyworkerCommecustomerCommentsnts" title="${handyworkerCommentsHeader}" sortable="false" />

		<spring:message code="note.customerComments" var="customerCommentsHeader" />
		<display:column property="customerComments" title="${customerCommentsHeader}" sortable="false" />

		<display:column>

				<a href="note/edit.do?noteId=${row.id}">
					<spring:message code="note.edit" />
				</a>

		</display:column>

	</display:table>