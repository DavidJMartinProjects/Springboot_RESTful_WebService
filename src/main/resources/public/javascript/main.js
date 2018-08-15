var theLocalhostUrl = 'http://localhost:8080/ladders';
var theHostedSiteUrl = 'https://immense-headland-50105.herokuapp.com/ladders';
var url = theLocalhostUrl;

var selectedLeague = "";
var timeStamp = "";
var flag = true;
var exp = "";
var xphDifference = "";
var isDead = "false";

$(document)
		.ready(
				function() {
					console.log("index.html loaded.")

					var table = $('#leagueInfoTable').dataTable({
						"columnDefs": [ {
							"targets": [0], // column or columns numbers
							"orderable": false,  // set orderable for selected columns
							}],
					});
					new $.fn.dataTable.FixedHeader(table);

					toastr
							.success(
									"<b>Please note: </b> <i>www.poe-ladder.com</i> is currently in Alpha testing.<br>  Not all intended features have been implemented to this version.",
									null,
									{
										"iconClass" : 'customer-info',
										"closeButton" : false,
										"debug" : false,
										"newestOnTop" : true,
										"progressBar" : false,
										"positionClass" : "toast-bottom-center",
										"preventDuplicates" : false,
										"onclick" : null,
										"showDuration" : "1000",
										"hideDuration" : "1000",
										"timeOut" : "8000",
										"extendedTimeOut" : "1000",
										"showEasing" : "swing",
										"hideEasing" : "linear",
										"showMethod" : "fadeIn",
										"hideMethod" : "fadeOut",
									})
				});

$("ul[id*=dropdownList] li").click(function() {
	console.log($(this).text()); // gets text contents of clicked li
	selectedLeague = $(this).text();
	$("#footer").css('visibility', 'hidden');
//	$("#tableLoadingAnimation").css('visibility', 'visible');
	console.log("selectedLeague" + selectedLeague);
	getleagueTable(selectedLeague);
	document.getElementById("footer").style.position = "static";
});

var showStatsBtn = function() {
	document.getElementById("showStatsButton").className = "btn btn-secondary";
	// document.getElementById('showStatsButton').disabled=false;
	// $("#tableLoadingAnimation").css('visibility', 'hidden');
}

var getleagueTable = function(selectedleague) {
	loadingTableAnimation();
	getLeagueDataTable(selectedleague);
	$.fn.dataTable.ext.classes.sPageButton = 'button primary_button';
}

$("#showStatsButton").click(function() {
	console.log("loading drawLevelChart() for : " + selectedLeague);
	console.log("Selected League : " + selectedLeague);
	toastr.remove();
	if (selectedLeague == "") {
		toastr.success("Select a league or race...", null, {
			"iconClass" : 'customer-info',
			"closeButton" : false,
			"debug" : false,
			"newestOnTop" : true,
			"positionClass" : "toast-top-center",
			"preventDuplicates" : false,
			"onclick" : null,
			"showDuration" : "300",
			"hideDuration" : "1000",
			"timeOut" : "4000",
			"extendedTimeOut" : "1000",
			"showEasing" : "swing",
			"hideEasing" : "linear",
			"showMethod" : "fadeIn",
			"hideMethod" : "fadeOut",
			"tapToDismiss" : false,
		})
		return false;
	}
	$('#exampleModalLongTitle').text(selectedLeague + " League Stats");
	loadingModalAnimation(selectedLeague);
	drawLevelChart(selectedLeague);
});

var getLeagueDataTable = function(selectedLeague) {
	$.ajax({
		url : url,
		type : 'GET',
		dataType : "json",
		data : {
			league : selectedLeague
		},
		success : function(results) {
			console.log(results)
			populateLeagueTable(results);
			// showStatsBtn();
			$("#tableLoadingAnimation").css('visibility', 'hidden');
			$("#footer").css('visibility', 'visible');
		},
		error : function(error) {
			console.log("getLeagueData error : " + error.responseJSON.message,
					"error");
		}
	});
};

var populateLeagueTable = function(results) {
	$('#leagueInfoTable').dataTable().fnDestroy();
	$("#leagueInfoTable tbody").empty();
	$("#leagueInfoTableContainer").css({
		"display" : "block"
	});
	flag = true;

	results
			.forEach(function(data) {
				var character = data.character;
				isDead = data.dead;

				if (data.dead == "true") {
					character += " <i id='deadStatus'>(dead)</i>";
				}
				
				var theRankDifference= "";
				var rankDifference = data.rankDifference;
								
				if (rankDifference < 0) {
					theRankDifference = "<span class='arrow-down'></span>&ensp;("+data.rankDifference+ ")";
				} else if (rankDifference >= 0 && rankDifference < 1) {
					theRankDifference = "";
				} else if (rankDifference >= 1){
					theRankDifference = "<span class='arrow-up'></span>&ensp;(+"+data.rankDifference+ ")";
				}
				 
				var account = "";
				if (data.online == "true") {
					account = "<img class='img-valign' src='/images/green-icon.png' title='online' />   "
							+ data.account;
				} else {
					account = "<img class='img-valign' src='/images/red-icon.png' title='offline' />   "
							+ data.account;
				}

				var challenge_icon = getChallengeIcon(data.challenges);

				var twitchLink;
				var twitchUrl = "https://www.twitch.tv/";
				twitchUrl += data.twitch;
				if (data.twitch != "") {
					twitchLink = "<a href='"
							+ twitchUrl
							+ "' target='_blank'><img src='/images/twitch-logo.png' class='twitch-logo' title='"
							+ data.twitch
							+ "' style='width:18px;height:18px;border:0;'></a>";
				} else {
					twitchLink = "";
				}

				xphDifference = "";
				if (data.xphDifference == 0 || data.xphDifference == null
						|| data.xphDifference == "null") {
					xphDifference = "-";
				} else {
					xphDifference = data.xphDifference;
					xphDifference = formatXphDifference(data.xphDifference);
					if (!xphDifference.startsWith("-")) {
						// xphDifference = "+" + xphDifference;
					}					
				}

				exp = "";
				if (data.xph == 0 || data.xph == null || data.xph == "null") {
					exp = "-";
				} else {
					exp = data.xph;
				}

				var xphColor = getXphColor(exp);
				var classColor = getColor(data.theClass);
				var accountLink = getPoeAccount(data.account)
				var ascendancyIcon = getAscendancyIcon(data.theClass);
				
				if (data.dead == "true") {
					$('#leagueInfoTable tbody').append(
							'<tr class = "deadChar"><td>' + theRankDifference + '</td>' 
							+'<td>' + data.rank +'</td>'
							+ '<td><a href=' + accountLink + ' target="_blank">' + account
									+ '</a></td>' + '<td>' + challenge_icon + "  " + character + '</td>' 
									+ '<td>' + data.level + '</td>'
									+ '<td><font color="' + classColor + '">' + ascendancyIcon + "  " + data.theClass + '</font></td>' + '<td class="' + xphColor
									+ '">' + exp 
									+ '</td>'
									+ '<td>' 
									
									+'<div class="progress">'
									+  '<div class="progress-bar bg-dark" style="width:'+data.levelProgressBar+'%">'
							/*		+	 +data.levelProgressBar+'%'*/
									+ '</div>'
									+'</div>'
									
									+ '</td>'
									+ '<td>' + data.experience + '</td>'
									+ '<td>' + twitchLink + '</td>' + '</tr>');
				} else {
					$('#leagueInfoTable tbody').append(
							'<tr><td>' + theRankDifference + '</td>' 
							+'<td>' + data.rank +'</td>'									
							+ '<td><a href=' + accountLink + ' target="_blank">' + account
							+ '</a></td>' + '<td>' + challenge_icon + "  " + character + '</td>' 
							+ '<td>' + data.level + '</td>'
							+ '<td><font color="' + classColor + '">' + ascendancyIcon + "  " + data.theClass + '</font></td>' + '<td class="' + xphColor
							+ '">' + exp + '</td>'
							+ '<td>' 
							
							+'<div class="progress">'
							+  '<div class="progress-bar bg-dark" style="width:'+data.levelProgressBar+'%">'
				/*			+	 +data.levelProgressBar+'%'*/
							+ '</div>'
							+'</div>'
							
							+ '</td>'
							+ '<td>' + data.experience + '</td>'
							+ '<td>' + twitchLink + '</td>' + '</tr>');

				}

				if (flag) {
					timeStamp = data.timeStamp;
					flag = false;
				}

			});

	$('#lastUpdatedMsg').text("updated : " + timeStamp + "");
	console.log("timestamp : " + timeStamp);
	var table = $('#leagueInfoTable').dataTable({
		"iDisplayLength" : 100,
		responsive : true,
		"pagingType" : "full_numbers",
		stateSave : true,
		"columnDefs": [ {
			"targets": [0], // column or columns numbers
			"orderable": false,  // set orderable for selected columns
			}],
	});

	setTimeout(function() {
		getleagueTable(selectedLeague);
		showUpdatedMessage();
	}, 5 * 60 * 1000);
	// }, 1 * 10 * 1000);

	isDead = "false";
	new $.fn.dataTable.FixedHeader(table);

};

var drawLevelChart = function(selectedLeague) {
	$.ajax({
		url : url + '/charts',
		type : 'GET',
		dataType : "json",
		data : {
			league : selectedLeague
		},
		success : function(results) {
			console.log("inside getLevelChartData() success : ");

			loadingModalAnimation();
			populateLevelChart(results);
		},
		error : function(error) {
			console.log("getLeagueData error : " + error.responseJSON.message,
					"error");
		}
	});
}

var populateLevelChart = function(results) {
	console.log("inside populateLevelChart()");

	var theDataPoints = [];
	var addData = function(data) {
		for (var i = 0; i < data.length; i++) {
			console.log("level : " + data[i].frequency);
			console.log("frequency : " + data[i].level);
			theDataPoints.push({
				x : "Level " + data[i].frequency,
				y : parseInt(data[i].level),
				exploded : true
			});
		}
	}
	addData(results);

	var chart = new CanvasJS.Chart(
			"chartContainer",
			{
				theme : "light2",
				exportFileName : "Doughnut Chart",
				exportEnabled : false,
				animationEnabled : true,
				title : {
					text : "Top 200 - Level Breakdown"
				},
				legend : {
					cursor : "pointer",
					itemclick : explodePie
				},
				data : [ {
					type : "doughnut",
					toolTipContent : "<b>Level </b>: {y} <br> <b>Percentage {level}</b>: {level} - #percent%",
					indexLabel : "Level {y} " + "{level} - #percent%",
					dataPoints : theDataPoints
				} ]
			});
	chart.render();

	function explodePie(e) {
		if (typeof (e.dataSeries.dataPoints[e.dataPointIndex].exploded) === "undefined"
				|| !e.dataSeries.dataPoints[e.dataPointIndex].exploded) {
			e.dataSeries.dataPoints[e.dataPointIndex].exploded = true;
		} else {
			e.dataSeries.dataPoints[e.dataPointIndex].exploded = false;
		}
		e.chart.render();
	}
}

function loadingTableAnimation() {
//	console.log("loadingTableAnimation");
//	var x = document.getElementById("tableLoadingAnimation");
//	var y = document.getElementById("leagueInfoTableContainer");
//
//	if (x.style.display === "none") {
//		$(".tableLoadingAnimation").css('visibility', 'visible');
//	} else {
//		$(".tableLoadingAnimation").css('visibility', 'hidden');
//	}
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

/* Dropdown Menu */
$('.dropdown').click(function() {
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
	switch (character) {
	case "Slayer": {
		return "#ffd79e";
	}
	case "Gladiator": {
		return "#ff6978";
	}
	case "Champion": {
		return "#ffb1aa";
	}
	case "Assassin": {
		return "white";
	}
	case "Saboteur": {
		return "#7090bd";
	}
	case "Trickster": {
		return "#9bd19f";
	}
	case "Juggernaut": {
		return "#ffb1aa";
	}
	case "Berserker": {
		return "#ffb1aa";
	}
	case "Chieftain": {
		return "#4d9078";
	}
	case "Necromancer": {
		return "#b4436c";
	}
	case "Elementalist": {
		return "#deb4e1";
	}
	case "Occultist": {
		return "#B49286";
	}
	case "Deadeye": {
		return "#9bd19f ";
	}
	case "Raider": {
		return "#9bd19f";
	}
	case "Pathfinder": {
		return "#9bd19f";
	}
	case "Inquisitor": {
		return "#4B88A2";
	}
	case "Hierophant": {
		return "white";
	}
	case "Guardian": {
		return "#ffb1aa ";
	}
	case "Ascendant": {
		return "#b06831";
	}
	}
}

var getXphColor = function(xph) {
	var xpPerHour = xph;
	if (xpPerHour < "0" && xpPerHour != "-") {
		formatXph(xpPerHour);
		return "xp-per-hour-red";
	} else if (xpPerHour >= "0") {
		formatXph(xpPerHour);
		return "xp-per-hour-green";
	} else {
		return "";
	}
}

var formatXph = function(theNumber) {
	exp = parseFloat(Math.round(theNumber) / 1000000).toFixed(2);
}

var formatXphDifference = function(theNumber) {
	console.log(theNumber);
	xphDifference = (parseInt(Math.round(theNumber) / 1000000).toFixed(2))
			+ "M";
	console.log(xphDifference);
	return xphDifference;
}

var getChallengeIcon = function(numberOfChallenges) {
	return "<img class='icon' src='/challenge_images/" + numberOfChallenges
			+ ".png' title='" + numberOfChallenges
			+ " challenges completed' />";
}

var getAscendancyIcon = function(ascendancy) {
	return "<img class='ascendancy-icon' src='/ascendancy_images/" + ascendancy
			+ ".jpg' title='" + ascendancy + "' />";
}

var getPoeAccount = function(accountName) {
	var address = "https://www.pathofexile.com/account/view-profile/";
	address = address.concat(accountName);
	return address;
}

var showUpdatedMessage = function() {
	toastr.success("ranks have been updated.", null, {
		"iconClass" : 'customer-info',
		"closeButton" : false,
		"debug" : false,
		"newestOnTop" : true,
		"positionClass" : "toast-top-center",
		"preventDuplicates" : false,
		"onclick" : null,
		"showDuration" : "300",
		"hideDuration" : "1000",
		"timeOut" : "4000",
		"extendedTimeOut" : "1000",
		"showEasing" : "swing",
		"hideEasing" : "linear",
		"showMethod" : "fadeIn",
		"hideMethod" : "fadeOut",
		"tapToDismiss" : false,
	})
}