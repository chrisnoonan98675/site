---
title: XebiaLabs knowledge base
---
{% for page in site.pages %}
  {% if page.path contains "kb/" %}
<a href="{{ page.url}}">{{ page.title }}</a>
  {% endif %}
{% endfor %}
