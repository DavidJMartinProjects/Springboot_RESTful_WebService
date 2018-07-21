var theLocalhostUrl = 'http://localhost:8080/ladders';
var theHostedSiteUrl = 'https://immense-headland-50105.herokuapp.com/ladders';

$(document).ready(function() {
	console.log("donut_chart.html loaded.")
	drawLevelChart("SSF Incursion HC");
});

var drawLevelChart = function(selectedLeague) {
    $.ajax({
        url: theLocalhostUrl +'/charts',
        type: 'GET',
        dataType: "json",
        data : {
        	league : selectedLeague 
        },
        success: function(results) {
        	console.log("inside getLevelChartData() success : ");
        	populateLevelChart(results);
        },
        error: function(error) {
            console.log("getLeagueData error : " + error.responseJSON.message, "error");
        }
    });
}

var populateLevelChart = function(results) { 
	console.log("inside populateLevelChart()");	
	
	var theDataPoints = [];
	var addData = function(data) {
		for (var i = 0; i < data.length; i++) {
			console.log("level : " +data[i].frequency);
			console.log("frequency : " +data[i].level);
			theDataPoints.push({
				x: "Level " + data[i].frequency,
				y: parseInt(data[i].level)
			});
		}
	}
	addData(results);	
	
	var chart = new CanvasJS.Chart("chartContainer", {
		theme: "dark2",
		exportFileName: "Doughnut Chart",
		exportEnabled: true,
		animationEnabled: true,
		title:{
			text: "Top 200 - Level Breakdown"
		},
		legend:{
			cursor: "pointer",
			itemclick: explodePie
		},
		data: [{
			type: "doughnut",
			toolTipContent: "<b>Level </b>: {y} <br> <b>Percentage {level}</b>: {level} - #percent%",
			indexLabel: "Level {y} " + "{level} - #percent%",
			dataPoints: theDataPoints
		}]
	});
	chart.render();

	function explodePie (e) {
		if(typeof (e.dataSeries.dataPoints[e.dataPointIndex].exploded) === "undefined" || !e.dataSeries.dataPoints[e.dataPointIndex].exploded) {
			e.dataSeries.dataPoints[e.dataPointIndex].exploded = true;
		} else {
			e.dataSeries.dataPoints[e.dataPointIndex].exploded = false;
		}
		e.chart.render();
	}
	

}