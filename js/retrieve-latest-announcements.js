var announceFeedUrl = "https://support.xebialabs.com/forums/324570/posts.rss";
var announceContainer=document.getElementById("announceContainer");
var entryCounter = 0;

$(document).ready(function() {
	var announceFeed = new google.feeds.Feed(announceFeedUrl);
	announceFeed.setNumEntries(25);
	announceFeed.load( function(result) {
		list = "<ul class='list-unstyled blogpostlist'>";
		if (!result.error){
			var announcements=result.feed.entries;
			for (var i = 0; i < announcements.length; i++) {
				if (announcements[i].title.indexOf("comment added by") >= 0) {
					// Do nothing
				}
				else { 
					entryCounter++;
					if (entryCounter <=5) {
						list+="<li><a href='" + announcements[i].link + "'target='_blank' title='Posted " + announcements[i].publishedDate + "'>" + announcements[i].title + "</a><br /><small>" + announcements[i].contentSnippet + "</small></li>";
					}
					else {
						// Do nothing
					}
				}
			}
			list+="</ul>";
			announceContainer.innerHTML = list;
		}
		else {
			announceContainer.innerHTML = "Unable to fetch feed from " + announceFeedUrl;
		}                   
	});
});
