module Jekyll
 
        ##
        # Monkey patch Jekyll's Page class
        # Source: http://biosphere.cc/software-engineering/jekyll-breadcrumbs-navigation-plugin/
        class Page
                @@url_to_page = {}
 
                ##
                # We add a custom method to the page variable, that returns an ordered list of it's
                # parent pages ready for iteration.
                def ancestors
                        a = []
                        # Lazily create a cache of url to page mappings. This enhances the lookup speed.
                        if @@url_to_page.empty?
                                site.pages.map do |page|
                                        @@url_to_page[page.url] = page
                                end
                        end

                        url = self.url
                        while url != "/index.html"
                                pt = url.split("/")
                                if pt[-1] != "index.html"
                                        # to to directory index
                                        pt[-1] = "index.html"
                                        url = pt.join("/")
                                else
                                        # one level up
                                        url = pt[0..-3].join("/") + "/index.html"
                                end
                                a << @@url_to_page[url]
                        end
 
                        return a.reverse
                end
 
                ##
                # Make ancestors available in liquid
                alias orig_to_liquid to_liquid
                def to_liquid
                        h = orig_to_liquid
                        h['ancestors'] = self.ancestors
                        return h
                end
        end
end
