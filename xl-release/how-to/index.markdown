---
title: XL Release how-to
---

<ul>
{% for page in site.pages %}
	{% if page.categories contains 'xl-release' and page.path contains 'how-to' %}
		{% unless page.path contains "index" %}
			<li><a href="{{ page.url }}">{{ page.title }}</a></li>
		{% endunless %}
	{% endif %}
{% endfor %}
</ul>