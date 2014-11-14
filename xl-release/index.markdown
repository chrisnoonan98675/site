---
layout: list-in-sidebar
title: XL Release
product-id: xl-release
weight: 2
---

## Concepts

<ul>
{% for page in site.pages %}
	{% if page.path contains 'xl-release' and page.path contains 'concepts' %}
		{% unless page.path contains "index" %}
			<li><a href="{{ page.url }}">{{ page.title }}</a></li>
		{% endunless %}
	{% endif %}
{% endfor %}
</ul>

## How-tos

<ul>
{% for page in site.pages %}
	{% if page.path contains 'xl-release' and page.path contains 'how-tos' %}
		{% unless page.path contains "index" %}
			<li><a href="{{ page.url }}">{{ page.title }}</a></li>
		{% endunless %}
	{% endif %}
{% endfor %}
</ul>

## Plugins
