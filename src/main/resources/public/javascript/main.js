var theLocalhostUrl = 'http://localhost:8080/ladders';
var theHostedSiteUrl = 'https://immense-headland-50105.herokuapp.com/ladders';
var selectedLeague = "";

$(document).ready(function() {
	console.log("index.html loaded.")	
	var table = $('#leagueInfoTable').DataTable({});	
	new $.fn.dataTable.FixedHeader( table );  

	toastr.success("<b>Please note: </b> <i>www.poe-ladder.com</i> is currently in pre-Alpha testing.<br>  Not all intended features have been implemented to this version.", 
			null, {"iconClass": 'customer-info',
		  "closeButton": false,
		  "debug": false,
		  "newestOnTop": true,
		  "progressBar": false,
		  "positionClass": "toast-bottom-center",
		  "preventDuplicates": false,
		  "onclick": null,
		  "showDuration": "1000",
		  "hideDuration": "1000",
		  "timeOut": "8000",
		  "extendedTimeOut": "1000",
		  "showEasing": "swing",
		  "hideEasing": "linear",
		  "showMethod": "fadeIn",
		  "hideMethod": "fadeOut",	
	})
});

$("ul[id*=dropdownList] li").click(function () {	
	console.log($(this).text()); // gets text contents of clicked li
	selectedLeague = $(this).text();
	getleagueTable(selectedLeague);
});

var getleagueTable = function(selectedleague){	
    loadingTableAnimation();
    getLeagueDataTable(selectedleague);
}

$("#showStatsButton").click(function() {
	console.log("loading drawLevelChart() for : " + selectedLeague);
    console.log("Selected League : " + selectedLeague); 
    toastr.remove();
    if(selectedLeague == "") {    	
        	toastr.success("Select a league or race...", 
        			null, {"iconClass": 'customer-info',
        		  "closeButton": false,
        		  "debug": false,
        		  "newestOnTop": true,
        		  "positionClass": "toast-top-center",
        		  "preventDuplicates": false,
        		  "onclick": null,
        		  "showDuration": "300",
        		  "hideDuration": "1000",
        		  "timeOut": "4000",
        		  "extendedTimeOut": "1000",
        		  "showEasing": "swing",
        		  "hideEasing": "linear",
        		  "showMethod": "fadeIn",
        		  "hideMethod": "fadeOut",  
        		  "tapToDismiss": false,        	
        	})  
    	return false;
    }
    $('#exampleModalLongTitle').text(selectedLeague + " League Stats");
    loadingModalAnimation(selectedLeague);
	drawLevelChart(selectedLeague);
});

var getLeagueDataTable = function(selectedLeague) {    
    $.ajax({
        url: theHostedSiteUrl,
        type: 'GET',
        dataType: "json",
        data : {
        	league : selectedLeague
        },
        success: function(results) {
        	console.log(results)
        	loadingTableAnimation();
            populateLeagueTable(results);
        },
        error: function(error) {
            console.log("getLeagueData error : " + error.responseJSON.message, "error");
        }
    });
};

var populateLeagueTable = function(results) {
    $('#leagueInfoTable').dataTable().fnDestroy();
    $("#leagueInfoTable tbody").empty();
    $("#leagueInfoTableContainer").css({
        "display": "block"
    });
    
    results.forEach(function(data) {
    	var character = data.character;
    	if(data.dead == "true") {
    		character += " <i id='deadStatus'>(dead)</i>";
    	}    	
    	var account = "";
    	if(data.online == "true") {
    		account = "<img class='img-valign' src='/images/green-icon.png' title='online' />   " + data.account;
    	} else {
    		account = "<img class='img-valign' src='/images/red-icon.png' title='offline' />   " + data.account;
    	}
    	
	    $('#leagueInfoTable tbody').append(
            '<tr>' +
	    		'<td>' + data.rank + '</td>' +
	    		'<td>' + account + '</td>' +
	    		'<td>' + character + '</td>' +
	    		'<td>' + data.level + '</td>' +
	    		'<td>' + data.theClass + '</td>' +
	    		'<td>' + data.challenges + '</td>' +
	    		'<td>' + data.experience + '</td>' +
    		'</tr>'
	     );
    });  
    		
	var table = $('#leagueInfoTable').DataTable( {
	    "iDisplayLength" : 50,
		responsive : true
	});	
	new $.fn.dataTable.FixedHeader( table );
};

var drawLevelChart = function(selectedLeague) {
    $.ajax({
        url: theHostedSiteUrl +'/charts',
        type: 'GET',
        dataType: "json",
        data : {
        	league : selectedLeague 
        },
        success: function(results) {
        	console.log("inside getLevelChartData() success : ");
        	loadingModalAnimation();
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
		theme: "light2",
		exportFileName: "Doughnut Chart",
		exportEnabled: false,
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

function loadingTableAnimation() {
	console.log("loadingTableAnimation");
    var x = document.getElementById("tableLoadingAnimation");
    var y = document.getElementById("leagueInfoTableContainer");      
    
    if (x.style.display === "none") {
        x.style.display = "block";
        y.style.display = "none";
    } else {
        x.style.display = "none";
        y.style.display = "block";
    }
}

function loadingModalAnimation() {
    var x = document.getElementById("modalLoadingAnimation");
    var y = document.getElementById("chartContainer");    
    
    if (x.style.display === "none") {
        x.style.display = "block";
        y.style.display = "none";        
    } else {
        x.style.display = "none";
        y.style.display = "block";
    }
}

/*Dropdown Menu*/
$('.dropdown').click(function () {
        $(this).attr('tabindex', 1).focus();
        $(this).toggleClass('active');
        $(this).find('.dropdown-menu').slideToggle(300);
});

$('.dropdown').focusout(function() {
	$(this).removeClass('active');
	$(this).find('.dropdown-menu').slideUp(300);
});

$('.dropdown .dropdown-menu li').click(
		function() {
			$(this).parents('.dropdown').find('span').text($(this).text());
			$(this).parents('.dropdown').find('input').attr('value',
			$(this).attr('id'));
});
