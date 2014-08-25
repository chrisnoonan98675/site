---
layout: list-in-sidebar
title: XL Deploy documentation
---

## Product documentation

* [XL Deploy 4.0](/products/xl-deploy/4.0)
* [XL Deploy 4.5](/products/xl-deploy/4.5)

## Knowledge base articles

<ul>
{% for post in site.categories.xl-deploy and site.categories.knowledge-base%}
		<li><a href="{{ post.url }}">{{ post.title }}</a></li>
	{% endfor %}
</ul>

