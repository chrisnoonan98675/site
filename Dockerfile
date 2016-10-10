FROM jekyll/jekyll

RUN gem install jekyll-seo-tag

COPY . /tmp

EXPOSE 4000

WORKDIR /tmp
RUN JEKYLL_ENV=production jekyll build

WORKDIR /tmp
CMD JEKYLL_ENV=production jekyll serve --skip-initial-build
