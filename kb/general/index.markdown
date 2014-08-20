---
title: XebiaLabs knowledge base
---

{% for cat in site.data.xld.categories %}
## {{ cat | capitalize }}
{% for page in site.pages %}
  {% if page.path contains "kb/general/" %}
    {% unless page.path contains "index" %}
      {% for pc in page.categories %}
        {% if pc == cat %}
1. <a href="{{ page.url}}">{{ page.title }}</a>
        {% endif %}
      {% endfor %}
    {% endunless %}
  {% endif %}
{% endfor %}

{% endfor %}

