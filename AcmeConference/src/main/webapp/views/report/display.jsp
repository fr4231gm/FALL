<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

 <div style="width: 33%; display:inline-block; text-align: center;">
	<h3><spring:message code='report.readabilityScore' /></h3>
	<canvas id="ReadabilityChart" >	</canvas>
</div>
 <div style="width: 33%;  display:inline-block; text-align: center;">
 	<h3><spring:message code='report.qualityScore' /></h3>
	<canvas id="QualityChart" >	</canvas>
</div>
 <div style="width: 33%;  display:inline-block; text-align: center;">
 	<h3><spring:message code='report.originalityScore' /></h3>
	<canvas id="OriginalityChart" >	</canvas>
</div>

<br><br>		

		<jstl:if test="${report.decision eq 'BORDER-LINE'}">
		
			<div style="text-align: center; color:blue">
			<h1 style="color:black; display:inline-block;"><spring:message code="report.decision"/>:&nbsp;</h1><h1 style="display:inline-block;"><strong><spring:message code="report.border.line"/></strong></h1>
			</div>
		</jstl:if>
		<jstl:if test="${report.decision eq 'ACCEPTED'}">
		
			 <div style="text-align: center; color:green">
				<h1 style="color:black; display:inline-block;"><spring:message code="report.decision"/>:&nbsp;</h1><h1 style="display:inline-block;"><strong><spring:message code="report.accepted"/></strong></h1>
				</div>
		</jstl:if>
		<jstl:if test="${report.decision eq 'REJECTED'}">
			 <div style="text-align: center; color: red">
				<h1 style="color:black; display:inline-block;"><spring:message code="report.decision"/>:&nbsp;</h1><h1 style="display:inline-block;"><strong><spring:message code="report.rejected"/></strong></h1>
			</div>
		</jstl:if>

 <br>
 <div style="text-align: center;">
	<form:textarea style="width: 60%; height: 150px;" path="report.comments" readonly="true"/>
</div>

<br>
 <div style="width:100%; text-align: center;">
	<canvas id="radarChart" >	</canvas>
</div>
<br>

<acme:back code="master.go.back" />
 
  <script>
    let readabilityScoreChart = document.getElementById('ReadabilityChart').getContext('2d');

    // Global Options
    Chart.defaults.global.defaultFontFamily = 'Comic Sans MS';
    Chart.defaults.global.defaultFontColor = '#000';
    Chart.defaults.global.defaultFontSize = 22;
	
    let massPopChart1 = new Chart(readabilityScoreChart, {
      type:'pie', // bar, horizontalBar, pie, line, doughnut, radar, polarArea
      data:{
    	  labels:["<spring:message code='report.left'/>", "<spring:message code='report.readabilityScore'/>"],
        datasets:[{
          label:'ReadabilityScore',
          data:["${10 - report.readabilityScore}", "${report.readabilityScore}"],
          backgroundColor:[
			'rgba(200, 200, 200, 0.0)',
            'rgba(255, 0, 000, 0.7)'
            
          ],
          borderWidth:1,
          borderColor:'#333',
        }]
      },
      options:{
        legend:{
          display:false,
          position:'right',
          labels:{
            fontColor:'#000'
          }
        },
       
      }
    });

    let qualityScoreChart = document.getElementById('QualityChart').getContext('2d');
	
    let massPopChart2 = new Chart(qualityScoreChart, {
      type:'pie', // bar, horizontalBar, pie, line, doughnut, radar, polarArea
      data:{
    	  labels:["<spring:message code='report.left'/>", "<spring:message code='report.qualityScore'/>"],
        datasets:[{
          label:'QualityScore',
          data:["${10 - report.qualityScore}", "${report.qualityScore}"],
          backgroundColor:[
			'rgba(200, 200, 200, 0.0)',
            'rgba(0, 255, 000, 0.7)'
            
          ],
          borderWidth:1,
          borderColor:'#333',
        }]
      },
      options:{
        legend:{
          display:false,
          position:'right',
          labels:{
            fontColor:'#000'
          }
        },
       
      }
    });

    let originalityScoreChart = document.getElementById('OriginalityChart').getContext('2d');
	
    let massPopChart3 = new Chart(originalityScoreChart, {
      type:'pie', // bar, horizontalBar, pie, line, doughnut, radar, polarArea
      data:{
        labels:["<spring:message code='report.left'/>", "<spring:message code='report.originalityScore'/>"],
        datasets:[{
          label:'OriginalityScore',
          data:["${10 - report.originalityScore}", "${report.originalityScore}"	 ],
          backgroundColor:[
            'rgba(200, 200, 200, 0)',
            'rgba(0, 0, 255, 0.7)'	
          ],
          borderWidth:1,
          borderColor:'#333',

        }]
      },
      options:{
        legend:{
          display:false,
          position:'right'
        },
       
      }
    });
    
    let radarChart = document.getElementById('radarChart').getContext('2d');
	
    let massPopChart4 = new Chart(radarChart, {
      type:'radar', // bar, horizontalBar, pie, line, doughnut, radar, polarArea
      data:{
        labels:["<spring:message code='report.readability'/>", "<spring:message code='report.quality'/>", "<spring:message code='report.originality'/>", ],
        datasets:[{
       
          data:["${report.readabilityScore}", "${report.originalityScore}",  "${report.qualityScore}"],
          backgroundColor:[
                           'rgba(255, 000, 255, 0.5)',	
                         ],
          	borderWidth:2,
          	borderColor:'#333',
      		hoverBorderWidth:4,
    		hoverBorderColor:'#000'
        }]
      },
      options:{
          legend:{
            display:false,
            position:'right',
          },
          scale: {
        	  pointLabels: {
        	      fontSize: 20
        	    },

          ticks: {
        	  maxTicksLimit: 5,
              beginAtZero: true,
              max: 10,
              min: 0,
              display: false,
              stepSize: 2
           }
        	}
         
        }
   
    });
  </script>