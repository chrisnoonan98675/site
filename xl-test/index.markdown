---
layout: list-in-sidebar
title: XL Test
product-id: xl-test
weight: 4
---

## Concepts

<ul>
{% for page in site.pages %}
	{% if page.path contains 'xl-test' and page.path contains 'concepts' %}
		{% unless page.path contains "index" %}
			<li><a href="{{ page.url }}">{{ page.title }}</a></li>
		{% endunless %}
	{% endif %}
{% endfor %}
</ul>

## How-tos

<ul>
{% for page in site.pages %}
	{% if page.path contains 'xl-test' and page.path contains 'how-tos' %}
		{% unless page.path contains "index" %}
			<li><a href="{{ page.url }}">{{ page.title }}</a></li>
		{% endunless %}
	{% endif %}
{% endfor %}
</ul>

## Plugins
