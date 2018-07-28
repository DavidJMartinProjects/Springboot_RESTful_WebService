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
	$("#tableLoadingAnimation").css('visibility', 'visible');
	getleagueTable(selectedLeague);
});

var showStatsBtn = function() {
	document.getElementById("showStatsButton").className = "btn btn-secondary";
	document.getElementById('showStatsButton').disabled=false;
	 $("#tableLoadingAnimation").css('visibility', 'hidden');
}

var getleagueTable = function(selectedleague){	
    loadingTableAnimation();
    getLeagueDataTable(selectedleague);
    $.fn.dataTable.ext.classes.sPageButton = 'button primary_button';    
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
            populateLeagueTable(results);           
            showStatsBtn();
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
    	
    	var twitchLink;
    	var twitchUrl = "https://www.twitch.tv/";
    	twitchUrl += data.twitch;
    	if(data.twitch != "") {
    		twitchLink = "<a href='"+twitchUrl+"' target='_blank'><img src='/images/twitch-logo.png' class='twitch-logo' title='"+data.twitch+"' style='width:18px;height:18px;border:0;'></a>";
    	} else {
    		twitchLink = "";
    	} 
    	    	
    	var classColor = getColor(data.theClass);
    	var accountLink = getPoeAccount(data.account)
	    $('#leagueInfoTable tbody').append(
            '<tr>' +
	    		'<td>' + data.rank + '</td>' +
	    		'<td><a href='+accountLink+' target="_blank">' + account + '</a></td>' +
	    		'<td>' + character + '</td>' +
	    		'<td>' + data.level + '</td>' +
	    		'<td><font color="'+classColor+'">' + data.theClass + '</font></td>' +
	    		'<td>' + data.challenges + '</td>' +
	    		'<td>' + data.xph + '</td>' +
	    		'<td>' + data.xphDifference + '</td>' +
	    		'<td>' + data.timeStamp + '</td>' +
	    		'<td>' + data.experience + '</td>' +
	    		'<td>' + twitchLink + '</td>' +
    		'</tr>'
	     );
    });  
    		
	var table = $('#leagueInfoTable').DataTable( {
	    "iDisplayLength" : 100,
		responsive : true,
		 "pagingType": "full_numbers"
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
				y: parseInt(data[i].level),
				exploded: true
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
     	$(".tableLoadingAnimation").css('visibility', 'visible');
    } else {
     	$(".tableLoadingAnimation").css('visibility', 'hidden');
    }
}

function loadingModalAnimation() {
    var x = document.getElementById("modalLoadingAnimation");
    var y = document.getElementById("chartContainer");    
    
    if (x.style.display === "none") {
     	$(".modalLoadingAnimation").css('visibility', 'hidden');

        x.style.display = "block";
        y.style.display = "none";        
    } else {
    	$(".modalLoadingAnimation").css('visibility', 'hidden');

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

var getColor = function(character) {
	switch(character) {
		case "Slayer": {
			return "#340068";
		}
		case "Gladiator": {
			return "#ff6978";
		}
		case "Champion": {
			return "#5c6f68";			
		}
		case "Assassin": {
			return "#b1ede8";		
		}
		case "Saboteur": {
			return "#235789";		
		}
		case "Trickster": {
			return "#5fad56";		
		}
		case "Juggernaut": {
			return "#ED1C24";		
		}
		case "Berserker": {
			return "#f78154";		
		}
		case "Chieftain": {
			return "#4d9078";		
		}
		case "Necromancer": {
			return "#b4436c";		
		}
		case "Elementalist": {
			return "#7D2E68";		
		}
		case "Occultist": {
			return "#B49286";		
		}
		case "Deadeye": {
			return "#744253 ";		
		}
		case "Raider": {
			return "#371E30";		
		}
		case "Pathfinder": {
			return "#A5B5BF";		
		}
		case "Inquisitor": {
			return "#4B88A2";		
		}
		case "Hierophant": {
			return "#FF8C42";		
		}
		case "Guardian": {
			return "#FCAF58 ";		
		}
		case "Ascendant": {
			return "#5D737E";		 
		}
	}
} 

var getPoeAccount = function(accountName) {
	var address = "https://www.pathofexile.com/account/view-profile/";
	address = address.concat(accountName);
	return address;
}