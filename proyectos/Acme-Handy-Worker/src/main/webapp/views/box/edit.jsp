
<%-- edit.jsp fix-up task --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="box">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="messages" />
	<form:hidden path="actor" />
	<form:hidden path="isSystem" />

	<div class="fh5co-cover" data-stellar-background-ratio="0.5">
		<div class="desc">
			<div class="col-sm-8 mt">
				<div class="tabulation animate-box">
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab"><spring:message
									code="box.create" /> </a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="flights">
							<div class="row">
								<div class="col-xxs-12 col-xs-6 mt alternate">
									<div class="input-field">
										<form:label path="name">
											<spring:message code="box.name" />:&nbsp;
										</form:label>
										<form:input path="name" value="${box.name}" placeholder="Name" />
										<form:errors cssClass="error" path="name" />
									</div>
								</div>
								<div class="col-xxs-12 col-xs-6 mt">
									<section>
										<form:label path="parentBox">
											<spring:message code="box.parent" />:&nbsp;
		</form:label>
										<form:select id="boxes" path="parentBox">
											<form:option itemLabel="root"
												value="" class="cs-select cs-skin-border" />
											<form:options items="${boxes}" itemLabel="name"
												itemValue="id" class="cs-select cs-skin-border" />
										</form:select>
									</section>
								</div>
								<div class="col-xs-12">
									<input type="submit" name="save"
										value="<spring:message code="box.save"/>" /> <input
										type="button" name="cancel"
										value="<spring:message code='fixUpTask.cancel'></spring:message>"
										onclick="javascript: relativeRedir('${cancelURI}');" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</form:form>















