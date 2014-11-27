---
title: XL Satellite how-to
---

<ul>
{% for page in site.pages %}
	{% if page.path contains 'xl-satellite' and page.path contains 'how-to' %}
		{% unless page.path contains "index" %}
			<li><a href="{{ page.url }}">{{ page.title }}</a></li>
		{% endunless %}
	{% endif %}
{% endfor %}
</ul>