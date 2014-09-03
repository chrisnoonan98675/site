---
title: XL Release
---

<ul>
{% for post in site.categories.xl-release and site.categories.knowledge-base %}
		<li><a href="{{ post.url }}">{{ post.title }}</a></li>
	{% endfor %}
</ul>