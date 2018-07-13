FROM ruby:alpine
MAINTAINER jvanerp@xebialabs.com

# Install Ruby development tools
RUN apk add --update ruby-dev libffi-dev build-base

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
CMD bundler exec jekyll serve --baseurl /latest --port 4000 --host 0.0.0.0 --incremental --destination $JEKYLL_DEST
