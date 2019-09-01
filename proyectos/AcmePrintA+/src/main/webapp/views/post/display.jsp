<%--
 * display.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<body>
	<script type="text/javascript" src="scripts/three.js"></script>
	<script type="text/javascript" src="scripts/Detector.js"></script>
	<script type="text/javascript" src="scripts/STLLoader.js"></script>
	<script type="text/javascript" src="scripts/OrbitControls.js"></script>

	<!--
	<script
		src="https://embed.github.com/view/3d/pedroswe/3Dprint/master/8Star_DragonBall_V2.stl"></script>
-->

	<div>
		<a href="#"><img src="${bannerSponsorship}"
			alt="${targetURLbanner}" height=200px width=80% /></a>
	</div>

	<fieldset>
		<legend>
			<spring:message code="post.display" />
		</legend>


		<acme:input code="post.ticker" path="postForm.ticker" readonly="true" />
		<br />

		<acme:input code="post.moment" path="postForm.moment" readonly="true" />
		<br />

		<acme:input code="post.title" path="postForm.title" readonly="true" />
		<br />

		<acme:input code="post.description" path="postForm.description"
			readonly="true" />
		<br />

		<acme:input code="post.score" path="postForm.score" readonly="true" />
		<br />

		<jstl:if test="${postForm.isDraft == 'true' }">
			<form:label path="postForm.isDraft">
				<spring:message code="post.isDraft" />
			</form:label>
			<input type="text" value="<spring:message code="post.isDraft.yes" />"
				readonly />
		</jstl:if>
		<jstl:if test="${postForm.isDraft == 'false' }">
			<form:label path="postForm.isDraft">
				<spring:message code="post.isDraft" />
			</form:label>
			<input type="text" value="<spring:message code="post.isDraft.no" />"
				readonly />
		</jstl:if>

		<acme:input code="post.pictures" path="postForm.pictures"
			readonly="true" />
		<br />

		<acme:input code="post.category" path="postForm.category"
			readonly="true" />
		<br />

		<acme:input code="post.stl" path="postForm.stl" readonly="true" />
		<br />
	</fieldset>
	<fieldset>
		<legend>
			<spring:message code="guide.display" />
		</legend>


		<acme:input code="post.guide.extruder" path="postForm.extruderTemp"
			readonly="true" />
		<br />
		<acme:input code="post.guide.hotbed" path="postForm.hotbedTemp"
			readonly="true" />
		<br />

		<acme:input code="post.guide.layer" path="postForm.layerHeight"
			readonly="true" />
		<br />

		<acme:input code="post.guide.speed" path="postForm.printSpeed"
			readonly="true" />
		<br />

		<acme:input code="post.guide.retraction"
			path="postForm.retractionSpeed" readonly="true" />
		<br />

		<acme:textarea code="post.guide.advices" path="postForm.advices"
			readonly="true" />
		<br />
	</fieldset>
	<h3>
		<spring:message code="post.comments"></spring:message>
	</h3>

	<display:table name="comments" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">

		<display:column property="title" titleKey="comment.title"
			sortable="true" />

		<display:column property="description" titleKey="comment.title"
			sortable="true" />

		<display:column property="type" titleKey="comment.type"
			sortable="true" />

		<display:column property="pictures" titleKey="comment.pictures"
			sortable="true" />

		<display:column property="score" titleKey="comment.score"
			sortable="true" />

		<display:column>
			<acme:link link="comment/display.do?commentId=${row.id}"
				code="comment.display" />
		</display:column>
	</display:table>


	<security:authorize access="isAuthenticated()">
		<acme:link code="post.create.comment"
			link="comment/create.do?postId=${postForm.id}" />
	</security:authorize>
	<br />

	<script>
		if (!Detector.webgl)
			Detector.addGetWebGLMessage();

		var camera, scene, renderer;

		init();

		function init() {
			//obtengo la url al fichero STL
			var stl = "${postForm.stl}";

			scene = new THREE.Scene();
			scene.add(new THREE.AmbientLight(0x999999));

			camera = new THREE.PerspectiveCamera(35, window.innerWidth
					/ window.innerHeight, 1, 200);

			// Z is up for objects intended to be 3D printed.

			camera.up.set(0, 0, 1);
			camera.position.set(0, -15, 6);

			camera.add(new THREE.PointLight(0xffffff, 0.8));

			scene.add(camera);

			var grid = new THREE.GridHelper(25, 50, 0xffffff, 0x555555);
			grid.rotateOnAxis(new THREE.Vector3(1, 0, 0), 90 * (Math.PI / 180));
			scene.add(grid);

			renderer = new THREE.WebGLRenderer({
				antialias : true
			});
			//set renderer color to white
			renderer.setClearColor(0xffffff);//999999
			renderer.setPixelRatio(window.devicePixelRatio);
			//set display size here in renderer.setSize
			renderer.setSize(window.innerWidth / 2, window.innerHeight / 2);
			document.body.appendChild(renderer.domElement);

			var loader = new THREE.STLLoader();

			// Binary files

			var material = new THREE.MeshPhongMaterial({
				color : 0x76a6f2,
				specular : 0x111111,
				shininess : 200
			});
			loader.load(stl, function(geometry) {
				var mesh = new THREE.Mesh(geometry, material);

				mesh.position.set(0, 0, 0);
				mesh.rotation.set(0, 0, 0);
				mesh.scale.set(.05, .05, .05);//.02

				mesh.castShadow = true;
				mesh.receiveShadow = true;

				scene.add(mesh);
				render();
			});

			var controls = new THREE.OrbitControls(camera, renderer.domElement);
			controls.addEventListener('change', render);
			controls.target.set(0, 1.2, 2);
			controls.update();
			window.addEventListener('resize', onWindowResize, false);

		}

		function onWindowResize() {

			camera.aspect = window.innerWidth / window.innerHeight;
			camera.updateProjectionMatrix();

			renderer.setSize(window.innerWidth, window.innerHeight);

			render();

		}

		function render() {

			renderer.render(scene, camera);

		}
	</script>

	<br />
	<acme:back code="post.goback" />

</body>