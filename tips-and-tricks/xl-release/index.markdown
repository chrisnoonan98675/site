---
title: XL Release
---

<ul>
{% for post in site.categories.xl-release and site.categories.tips-and-tricks %}
		<li><a href="{{ post.url }}">{{ post.title }}</a></li>
	{% endfor %}
</ul>