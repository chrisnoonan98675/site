---
title: XL Deploy how-tos
---

<ul>
{% for page in site.pages %}
	{% if page.path contains 'xl-deploy' and page.path contains 'how-tos' %}
		{% unless page.path contains "index" %}
			<li><a href="{{ page.url }}">{{ page.title }}</a></li>
		{% endunless %}
	{% endif %}
{% endfor %}
</ul>