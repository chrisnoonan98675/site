---
title: XL Test
---

<ul>
{% for post in site.categories.xl-test and site.categories.knowledge-base %}
		<li><a href="{{ post.url }}">{{ post.title }}</a></li>
	{% endfor %}
</ul>