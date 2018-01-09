FROM ruby:latest
MAINTAINER jvanerp@xebialabs.com

#Copy over the gemfile to a temporary directory and run the install command.
WORKDIR /tmp
ADD Gemfile Gemfile
ADD Gemfile.lock Gemfile.lock
RUN bundle install

#Switch into the working directory and run the server.
VOLUME /src
WORKDIR /src
ENV JEKYLL_ENV development
ENV JEKYLL_DEST /site
CMD ["bundle exec jekyll serve --port 4000 --host 0.0.0.0 --incremental --destination $JEKYLL_DEST"]
