var feedUrl = "http://blog.xebialabs.com/feed/";
var feedContainer=document.getElementById("feedContainer");

$(document).ready(function() {
	var feed = new google.feeds.Feed(feedUrl);
	feed.setNumEntries(10);
	feed.load( function(result) {
		list = "<ul>";
		if (!result.error){
			var posts=result.feed.entries;
			for (var i = 0; i < posts.length; i++) { 
				list+="<li><a href='" + posts[i].link + "'target='_blank' title='Posted " + posts[i].publishedDate + "'>" + posts[i].title + "</a></li>";
			}
			list+="</ul>";
			feedContainer.innerHTML = list;
		}
		else {
			feedContainer.innerHTML = "Unable to fetch feed from " + feedUrl;
		}                   
	});
});
