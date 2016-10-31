FROM jekyll/jekyll:3.2

RUN [ "gem", "install", "--no-document", "jekyll-seo-tag"]

ARG JEKYLL_ENV=production

COPY . /srv/jekyll

RUN ["jekyll", "build"]
