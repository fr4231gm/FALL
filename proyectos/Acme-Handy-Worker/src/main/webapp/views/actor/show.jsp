
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${formURL}" modelAttribute="actor">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<div class="fh5co-cover" data-stellar-background-ratio="0.5">
		<div class="desc">
			<div class="col-sm-8 mt">
				<div class="tabulation animate-box">
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab"> 
							<jstl:out value="${actor.userAccount.username}"/>
						</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="flights">
							<div class="row">
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<b><spring:message code="message.sender.email" />:&nbsp;</b>
										<jstl:out value="${actor.email}" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<b><spring:message code="actor.name" />:&nbsp;</b>
										<jstl:out value="${actor.name}" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<b><spring:message code="actor.middleName" />:&nbsp;</b>
										<jstl:out value="${actor.middleName}" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<b><spring:message code="actor.surname" />:&nbsp;</b>
										<jstl:out value="${actor.surname}" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<b> <spring:message code="actor.address" />:&nbsp;
										</b>
										<jstl:out value="${actor.address}" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<b> <spring:message code="actor.phoneNumber" />:&nbsp;
										</b>
										<jstl:out value="${actor.phoneNumber}" />
									</div>
								</div>
								<div class="input-field">
									<img src="${actor.photo}" alt="Picture Not Found"
										width="27%" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</form:form>
