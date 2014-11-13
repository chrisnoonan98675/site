---
title: XL Deploy
---

<ul>
{% for post in site.categories.xl-deploy and site.categories.tips-and-tricks %}
	<li><a href="{{ post.url }}">{{ post.title }}</a></li>
{% endfor %}
</ul>