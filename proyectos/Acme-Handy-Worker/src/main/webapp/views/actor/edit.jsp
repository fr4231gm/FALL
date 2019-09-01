
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${formURL}" modelAttribute="actorForm">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<div class="fh5co-cover" data-stellar-background-ratio="0.5">
		<div class="desc">
			<div class="col-sm-8 mt">
				<div class="tabulation animate-box">
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab"><spring:message code="actor.register"/>
						</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="flights">
							<div class="row">
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="name">
											<spring:message code="actor.name" />:&nbsp;
										</form:label>
										<form:input path="name" value="${actorForm.name}"
											placeholder="Name" />
										<form:errors cssClass="error" path="name" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="middleName">
											<spring:message code="actor.middleName" />:&nbsp;
										</form:label>
										<form:input path="middleName" value="${actorForm.middleName}"
											placeholder="MiddleName" />
										<form:errors cssClass="error" path="middleName" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="surname">
											<spring:message code="actor.surname" />:&nbsp;
										</form:label>
										<form:input path="surname" value="${actorForm.surname}"
											placeholder="Surname" />
										<form:errors cssClass="error" path="surname" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="email">
											<spring:message code="actor.email" />:&nbsp;
										</form:label>
										<form:input path="email" value="${actorForm.email}"
											placeholder="email@mail.es" />
										<form:errors cssClass="error" path="email" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="address">
											<spring:message code="actor.address" />:&nbsp;
										</form:label>
										<form:input path="address" value="${actorForm.address}"
											placeholder="Address" />
										<form:errors cssClass="error" path="address" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="photo">
											<spring:message code="actor.photo" />:&nbsp;
										</form:label>
										<form:input path="photo" value="${actorForm.photo}"
											placeholder="https://www.photo.es" />
										<form:errors cssClass="error" path="photo" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="phoneNumber">
											<spring:message code="actor.phoneNumber" />:&nbsp;
										</form:label>
										<form:input path="phoneNumber" id="phoneId"
											value="${actorForm.phoneNumber}" placeholder="+34662130564" />
										<form:errors cssClass="error" path="phoneNumber" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="username">
											<spring:message code="actor.username" />:&nbsp;
										</form:label>
										<form:input path="username" value="${actorForm.username}"
											placeholder="username" />
										<form:errors cssClass="error" path="username" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="password">
											<spring:message code="actor.password" />:&nbsp;
										</form:label>
										<form:password path="password" value="${actorForm.password}"
											placeholder="password" />
										<form:errors cssClass="error" path="password" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt">
									<section>
										<form:label path="role">
											<spring:message code="actor.role" />:&nbsp;
		</form:label>
										<form:select id="role" path="role">
											<form:option value="Customer">
												<spring:message code="actor.customer" />
											</form:option>
											<form:option value="Handyworker">
												<spring:message code="actor.handyworker" />
											</form:option>
											<form:option value="Sponsor">
												<spring:message code="actor.sponsor" />
											</form:option>
											<security:authorize access="hasRole('ADMIN')">
												<form:option value="Referee">
													<spring:message code="actor.referee" />
												</form:option>
												<form:option value="Admin">
													<spring:message code="actor.admin" />
												</form:option>
											</security:authorize>

										</form:select>
									</section>
								</div>
								<div class="col-xxs-12 col-xs-6 mt">
									<section></section>
								</div>
								<div class="col-xs-12">
									<input type="submit" name="save"
										value="<spring:message code="actor.save"/>" /> <input
										type="button" name="cancel"
										value="<spring:message code='fixUpTask.cancel'></spring:message>"
										onclick="javascript: relativeRedir('');" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form:form>
<script type="text/javascript" src="scripts/customMessages.js"></script>
