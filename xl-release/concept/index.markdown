---
title: XL Release concepts
---

<ul>
{% for page in site.pages %}
	{% if page.categories contains 'xl-release' and page.path contains 'concept' %}
		{% unless page.path contains "index" %}
			<li><a href="{{ page.url }}">{{ page.title }}</a></li>
		{% endunless %}
	{% endif %}
{% endfor %}
</ul>