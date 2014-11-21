---
title: XL Deploy how-to
---

<ul>
{% for page in site.pages %}
	{% if page.path contains 'xl-deploy' and page.path contains 'how-to' %}
		{% unless page.path contains "index" %}
			<li><a href="{{ page.url }}">{{ page.title }}</a></li>
		{% endunless %}
	{% endif %}
{% endfor %}
</ul>