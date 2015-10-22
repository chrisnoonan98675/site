---
layout: list-in-sidebar
title: Videos
weight: 35
---

Click a link below to watch a video in a pop-up window, or visit the [XebiaLabs YouTube channel](https://www.youtube.com/user/xebialabs) to browse all of our videos.

{% for product in site.data.videos %}
<h2>{{ product.name }}</h2>
<ul>
{% for video in product.videos %}
<li><a href="https://www.youtube.com/watch?v={{ video.id }}" class="magnific-youtube">{{ video.title }}</a></li>
{% endfor %}
</ul>
{% endfor %}
