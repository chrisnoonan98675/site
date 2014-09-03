---
title: XL Deploy 4.0
---

{% for page in site.pages %}
  {% if page.path contains "xl-deploy/4.0/" %}
    {% unless page.path contains "index" %}
<a href="{{ page.url}}">{{ page.title }}</a>
    {% endunless %}
  {% endif %}
{% endfor %}
