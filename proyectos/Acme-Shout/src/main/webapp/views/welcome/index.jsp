<%--
 * index.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="welcome.greeting.welcome" />
</p>
<security:authorize access="isAuthenticated()">
	<p>
		<spring:message code="welcome.greeting.prefix" />
		<security:authentication property="principal.username"/><spring:message code="welcome.greeting.suffix" />
	</p>
</security:authorize>

<table
	style="margin: 0 auto; width: 244px; background: transparent;"
	cellspacing="0" cellpadding="0">
	<tr>
		<td
			style="font-family: verdana; font-size: 11px; color: #000"><object
				classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
				codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0"
				width="640" height="480">
				<param name="movie"
					value="http://www.games68.com/games/58591331d428e.swf">
				<param name="quality" value="high"></param>
				<param name="menu" value="false"></param>
				<embed src="http://www.games68.com/games/58591331d428e.swf"
					width="640" height="480" quality="high"
					pluginspage="http://www.macromedia.com/go/getflashplayer"
					type="application/x-shockwave-flash" menu="false" wmode="direct"></embed>
			</object></td>
	</tr>
	<tr>

	</tr>
</table>





