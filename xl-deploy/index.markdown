---
layout: list-in-sidebar
title: XL Deploy
product-id: xl-deploy
weight: 1
---

## Legacy documentation

{% include product_doc_list.html %}

## Concepts

<ul>
{% for page in site.pages %}
	{% if page.path contains 'xl-deploy' and page.path contains 'concepts' %}
		{% unless page.path contains "index" %}
			<li><a href="{{ page.url }}">{{ page.title }}</a></li>
		{% endunless %}
	{% endif %}
{% endfor %}
</ul>

## How-tos

<ul>
{% for page in site.pages %}
	{% if page.path contains 'xl-deploy' and page.path contains 'how-tos' %}
		{% unless page.path contains "index" %}
			<li><a href="{{ page.url }}">{{ page.title }}</a></li>
		{% endunless %}
	{% endif %}
{% endfor %}
</ul>

## Reference

<ul>
{% for page in site.pages %}
	{% if page.path contains 'xl-deploy' and page.path contains 'reference' %}
		{% unless page.path contains "index" %}
			<li><a href="{{ page.url }}">{{ page.title }}</a></li>
		{% endunless %}
	{% endif %}
{% endfor %}
</ul>

## Plugins

