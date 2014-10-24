---
title: XL Deploy
---

<ol>
{% for post in site.categories.xl-deploy and site.categories.knowledge-base %}
	<li><a href="{{ post.url }}">{{ post.title }}</a></li>
{% endfor %}
</ol>